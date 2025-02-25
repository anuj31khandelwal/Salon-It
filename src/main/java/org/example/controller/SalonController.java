package org.example.controller;

import org.example.dto.AppointmentDTO;
import org.example.entity.*;
import org.example.enums.AppointmentStatus;
import org.example.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> getSalonDashboard(@PathVariable Long salonId) {
        return ResponseEntity.ok("Salon dashboard for ID: " + salonId);
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
                                                          @RequestBody String status) {
        Optional<Appointments> appointmentOptional = appointmentRepository.findById(appointmentId);

        if (appointmentOptional.isPresent()) {
            Appointments appointment = appointmentOptional.get();

            if (appointment.getSalon().getId() != salonId) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Appointment does not belong to Salon ID: " + salonId);
            }

            appointment.setStatus(AppointmentStatus.valueOf(status));
            appointmentRepository.save(appointment);

            return ResponseEntity.ok("Appointment ID " + appointmentId + " status updated to: " + status);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Appointment not found with ID: " + appointmentId);
        }
    }


}
