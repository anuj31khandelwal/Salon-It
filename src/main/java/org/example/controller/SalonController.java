package org.example.controller;

import org.example.dto.AppointmentDTO;
import org.example.dto.EarningsDTO;
import org.example.dto.SalonDashboardDTO;
import org.example.dto.StatusUpdateRequest;
import org.example.entity.*;
import org.example.enums.AppointmentStatus;
import org.example.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/salons")
public class SalonController {

    private final UserRepository userRepository;
    private final SalonRepository salonRepository;
    private final BarberRepository barberRepository;
    private final ServiceRepository serviceRepository;
    private final AppointmentRepository appointmentRepository;

    public SalonController(UserRepository userRepository, SalonRepository salonRepository, BarberRepository barberRepository, ServiceRepository serviceRepository, AppointmentRepository appointmentRepository) {
        this.userRepository = userRepository;
        this.salonRepository = salonRepository;
        this.barberRepository = barberRepository;
        this.serviceRepository = serviceRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerSalon(@RequestBody Salon salonRequest) {
        try {
            // Check if the owner exists
            Optional<SalonUser> ownerOptional = userRepository.findById(salonRequest.getOwner().getId());

            if (ownerOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Owner not found");
            }

            SalonUser owner = ownerOptional.get();
            salonRequest.setOwner(owner);

            // Save the salon
            salonRepository.save(salonRequest);

            return ResponseEntity.ok("Salon registered successfully with ID: " + salonRequest.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to register salon: " + e.getMessage());
        }
    }


    @GetMapping("/{salonId}/dashboard")
    public ResponseEntity<SalonDashboardDTO> getSalonDashboard(@PathVariable Long salonId) {
        Optional<Salon> salonOptional = salonRepository.findById(salonId);

        if (salonOptional.isPresent()) {
            Salon salon = salonOptional.get();
            LocalDateTime now = LocalDateTime.now();

            List<Appointments> allAppointments = appointmentRepository.findBySalon(salon);

            // Split appointments based on status and date
            List<AppointmentDTO> pendingAppointments = allAppointments.stream()
                    .filter(app -> app.getStatus() == AppointmentStatus.PENDING)
                    .map(AppointmentDTO::new)
                    .collect(Collectors.toList());

            List<AppointmentDTO> upcomingAppointments = allAppointments.stream()
                    .filter(app -> app.getStatus() == AppointmentStatus.CONFIRMED && app.getAppointmentTime().isAfter(now))
                    .map(AppointmentDTO::new)
                    .collect(Collectors.toList());

            List<AppointmentDTO> pastAppointments = allAppointments.stream()
                    .filter(app -> app.getAppointmentTime().isBefore(now))
                    .map(AppointmentDTO::new)
                    .collect(Collectors.toList());

            // Calculate earnings for different intervals
            EarningsDTO earnings = new EarningsDTO(
                    calculateEarnings(allAppointments, now.minusDays(7)),
                    calculateEarnings(allAppointments, now.minusDays(30)),
                    calculateEarnings(allAppointments, now.minusYears(1))
            );

            // Build the dashboard response
            SalonDashboardDTO dashboardDTO = new SalonDashboardDTO(
                    pendingAppointments,
                    upcomingAppointments,
                    pastAppointments,
                    earnings
            );

            return ResponseEntity.ok(dashboardDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    private double calculateEarnings(List<Appointments> appointments, LocalDateTime startDate) {
        return appointments.stream()
                .filter(app -> app.getAppointmentTime().isAfter(startDate))
                .filter(app -> app.getStatus() == AppointmentStatus.COMPLETED)
                .mapToDouble(app -> app.getServiceItem().getPrice())
                .sum();
    }


    @PostMapping("/{salonId}/addBarber")
    public ResponseEntity<String> addBarber(@PathVariable Long salonId, @RequestBody Barber barberRequest) {
        try {
            Optional<Salon> salonOptional = salonRepository.findById(salonId);

            if (salonOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Salon not found");
            }

            Salon salon = salonOptional.get();

            // Link barber to the salon
            barberRequest.setSalon(salon);
            barberRepository.save(barberRequest);

            return ResponseEntity.ok("Barber added to salon ID: " + salonId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to add barber: " + e.getMessage());
        }
    }

    @DeleteMapping("/{salonId}/barbers/{barberId}")
    public ResponseEntity<String> removeBarber(@PathVariable Long salonId, @PathVariable Long barberId) {
        try {
            Optional<Barber> barberOptional = barberRepository.findById(barberId);

            if (barberOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Barber not found");
            }

            Barber barber = barberOptional.get();

            if (barber.getSalon().getId() != salonId) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Barber does not belong to the specified salon");
            }

            barberRepository.delete(barber);

            return ResponseEntity.ok("Barber ID " + barberId + " removed from salon ID: " + salonId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to remove barber: " + e.getMessage());
        }
    }


    @PostMapping("/{salonId}/addServices")
    public ResponseEntity<String> addService(@PathVariable Long salonId, @RequestBody List<ServiceItem> serviceItems) {
        Optional<Salon> salonOptional = salonRepository.findById(salonId);

        if (salonOptional.isPresent()) {
            Salon salon = salonOptional.get();

            if (serviceItems.isEmpty()) {
                return ResponseEntity.badRequest().body("Service list is empty. Please provide at least one service.");
            }

            serviceItems.forEach(serviceItem -> serviceItem.setSalon(salon));
            serviceRepository.saveAll(serviceItems);

            return ResponseEntity.ok("Services added to salon ID: " + salonId);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Salon not found with ID: " + salonId);
        }

    }

    @PutMapping("/{salonId}/services/{serviceId}")
    public ResponseEntity<String> updateService(@PathVariable Long salonId, @PathVariable Long serviceId, @RequestBody ServiceItem updatedService) {
        Optional<ServiceItem> serviceOptional = serviceRepository.findById(serviceId);

        if (serviceOptional.isPresent()) {
            ServiceItem existingService = serviceOptional.get();

            // Ensure the service belongs to the correct salon
            if (existingService.getSalon().getId() != salonId) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Service does not belong to salon ID: " + salonId);
            }

            existingService.setName(updatedService.getName());
            existingService.setPrice(updatedService.getPrice());
            existingService.setDuration(updatedService.getDuration());

            serviceRepository.save(existingService);

            return ResponseEntity.ok("Service ID " + serviceId + " updated for salon ID: " + salonId);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Service not found with ID: " + serviceId);
        }
    }

    @DeleteMapping("/{salonId}/services/{serviceId}")
    public ResponseEntity<String> removeService(@PathVariable Long salonId, @PathVariable Long serviceId) {
        Optional<ServiceItem> serviceOptional = serviceRepository.findById(serviceId);

        if (serviceOptional.isPresent()) {
            ServiceItem service = serviceOptional.get();

            // Ensure the service belongs to the correct salon
            if (service.getSalon().getId() != salonId) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Service does not belong to salon ID: " + salonId);
            }

            serviceRepository.deleteById(serviceId);
            return ResponseEntity.ok("Service ID " + serviceId + " removed from salon ID: " + salonId);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Service not found with ID: " + serviceId);
        }
    }

    @GetMapping("/{salonId}/allAppointments")
    public ResponseEntity<List<AppointmentDTO>> getSalonAppointments(@PathVariable Long salonId) {
        List<Appointments> appointments = appointmentRepository.findBySalonId(salonId);

        List<AppointmentDTO> appointmentDTOs = appointments.stream()
                .map(AppointmentDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(appointmentDTOs);
    }

    @PutMapping("/{salonId}/appointments/{appointmentId}/status")
    public ResponseEntity<String> updateAppointmentStatus(@PathVariable Long salonId,
                                                          @PathVariable Long appointmentId,
                                                          @RequestBody StatusUpdateRequest statusUpdateRequest) {
        Optional<Appointments> appointmentOptional = appointmentRepository.findById(appointmentId);

        if (appointmentOptional.isPresent()) {
            Appointments appointment = appointmentOptional.get();

            if (appointment.getSalon().getId() != salonId) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Appointment ID " + appointmentId + " does not belong to Salon ID: " + salonId);
            }

            try {
                AppointmentStatus newStatus = AppointmentStatus.valueOf(statusUpdateRequest.getStatus().toUpperCase());
                appointment.setStatus(newStatus);
                appointmentRepository.save(appointment);

                return ResponseEntity.ok("Appointment ID " + appointmentId + " status updated to: " + newStatus);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Invalid status value: " + statusUpdateRequest.getStatus() +
                                ". Allowed values: PENDING, CONFIRMED, COMPLETED, CANCELLED");
            }

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Appointment not found with ID: " + appointmentId);
        }
    }



}
