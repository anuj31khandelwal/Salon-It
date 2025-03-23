package org.example.controller;

import org.example.dto.*;
import org.example.entity.*;
import org.example.enums.AppointmentStatus;
import org.example.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;
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
    public ResponseEntity<Map<String, Object>> registerSalon(@RequestBody Salon salonRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Check if the owner exists
            Optional<SalonUser> ownerOptional = userRepository.findById(salonRequest.getOwner().getId());
            if (ownerOptional.isEmpty()) {
                response.put("message", "Owner not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            SalonUser owner = ownerOptional.get();
            salonRequest.setOwner(owner);

            // Save the salon
            Salon savedSalon = salonRepository.save(salonRequest);

            response.put("message", "Salon registered successfully");
            response.put("salonId", savedSalon.getId());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Failed to register salon");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
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
                    salon.getName(),
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

    @PutMapping("/{salonId}/updateBarberStatus/{barberId}")
    public ResponseEntity<String> updateBarberStatus(
            @PathVariable Long salonId,
            @PathVariable Long barberId,
            @RequestBody Map<String, Boolean> requestBody) {
        try {
            Optional<Salon> salonOptional = salonRepository.findById(salonId);
            Optional<Barber> barberOptional = barberRepository.findById(barberId);

            if (salonOptional.isEmpty() || barberOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Salon or Barber not found");
            }

            Barber barber = barberOptional.get();
            Boolean isAvailable = requestBody.get("isAvailable"); // Ensure we're getting a Boolean value

            if (isAvailable == null) {
                return ResponseEntity.badRequest().body("Invalid request: 'isAvailable' field is missing");
            }

            barber.setAvailable(isAvailable); // Update the availability
            barberRepository.save(barber); // Save the updated status

            return ResponseEntity.ok("Barber availability updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update barber status: " + e.getMessage());
        }
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
                AppointmentStatus currentStatus = appointment.getStatus();
                AppointmentStatus newStatus = AppointmentStatus.valueOf(statusUpdateRequest.getStatus().toUpperCase());

                // Check if the current status is CANCELLED (cannot be modified)
                if (currentStatus == AppointmentStatus.CANCELLED) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Cancelled appointments cannot be modified");
                }

                // Enforce status transition rules
                if (newStatus == AppointmentStatus.CANCELLED) {
                    // Allow cancellation from any non-CANCELLED status
                    if (currentStatus == AppointmentStatus.COMPLETED) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body("Completed appointments cannot be cancelled");
                    }
                } else {
                    // For status progression (non-cancellation), enforce proper sequence
                    boolean validTransition = false;

                    switch (currentStatus) {
                        case PENDING:
                            validTransition = (newStatus == AppointmentStatus.CONFIRMED);
                            break;
                        case CONFIRMED:
                            validTransition = (newStatus == AppointmentStatus.COMPLETED);
                            break;
                        case COMPLETED:
                            // Completed appointments cannot change status
                            validTransition = false;
                            break;
                        default:
                            validTransition = false;
                    }

                    if (!validTransition) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body("Invalid status transition: " + currentStatus + " → " + newStatus);
                    }
                }

                // Apply status change
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

    @GetMapping("/searchByLocation")
    public ResponseEntity<List<SalonDetailsDTO>> searchSalonsByLocation(@RequestParam String location) {
        List<Salon> salons = salonRepository.findSalonsByLocation(location);
        List<SalonDetailsDTO> salonDetails = salons.stream().map(this::mapToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(salonDetails);
    }

    private SalonDetailsDTO mapToDTO(Salon salon) {
        SalonDetailsDTO dto = new SalonDetailsDTO();
        dto.setId(salon.getId());
        dto.setName(salon.getName());
        dto.setAddress(salon.getAddress());
        dto.setOpeningTime(String.valueOf(salon.getOpeningTime()));
        dto.setClosingTime(String.valueOf(salon.getClosingTime()));
        dto.setPhoneNumber(salon.getPhoneNumber());
        return dto;
    }

    @GetMapping("/{salonId}")
    public ResponseEntity<SalonDetailsDTO> getSalonById(@PathVariable long salonId) {
        Salon salon = salonRepository.findById(salonId)
                .orElseThrow(() -> new RuntimeException("Salon not found with ID: " + salonId));

        SalonDetailsDTO salonDetails = mapToDTO(salon);
        return ResponseEntity.ok(salonDetails);
    }

    @GetMapping("/{salonId}/services")
    public ResponseEntity<List<ServiceItemDTO>> getServicesBySalonId(@PathVariable long salonId) {
        List<ServiceItem> services = serviceRepository.findBySalonId(salonId);

        List<ServiceItemDTO> serviceDTOs = services.stream()
                .map(this::mapServiceItemToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(serviceDTOs);
    }

    private ServiceItemDTO mapServiceItemToDTO(ServiceItem service) {
        ServiceItemDTO dto = new ServiceItemDTO();
        dto.setId(service.getId());
        dto.setName(service.getName());
        dto.setPrice(service.getPrice());
        dto.setDuration(service.getDuration());
        return dto;
    }

    @GetMapping("/barbers/{salonId}")
    public ResponseEntity<List<Map<String, Object>>> getAllBarbers(@PathVariable long salonId) {
        List<Map<String, Object>> barberList = new ArrayList<>();
        List<Barber> barbers = barberRepository.findBySalonId(salonId);

        for (Barber barber : barbers) {
            Map<String, Object> barberInfo = new HashMap<>();
            barberInfo.put("id", barber.getId());
            barberInfo.put("name", barber.getName());
            barberList.add(barberInfo);
        }

        return ResponseEntity.ok(barberList);
    }

    @PostMapping("/upload-documents/{salonId}")
    public ResponseEntity<?> uploadDocuments(@PathVariable Long salonId,
                                             @RequestParam("cosmetologyLicense") MultipartFile cosmetologyLicense,
                                             @RequestParam("idProof") MultipartFile idProof,
                                             @RequestParam("taxId") MultipartFile taxId,
                                             @RequestParam("bankAccountDetails") MultipartFile bankAccountDetails,
                                             @RequestParam("serviceMenu") MultipartFile serviceMenu,
                                             @RequestParam("bestWorkPhotos") MultipartFile bestWorkPhotos) {

        // Process and save documents (skip actual file saving for now)
        // You can implement actual file storage logic later
        boolean isDocumentsUploaded = saveDocuments(salonId, cosmetologyLicense, idProof, taxId, bankAccountDetails, serviceMenu, bestWorkPhotos);

        if (isDocumentsUploaded) {
            // Generate a reference ID after successful document upload
            String referenceId = String.valueOf(salonId);

            // Save the reference ID to the salon record (you can implement this method in salonService)


            // Return the reference ID in the response
            return ResponseEntity.ok(referenceId);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to upload documents");
        }
    }

    // Temporary method to save the uploaded documents (you can expand this with actual logic)
    private boolean saveDocuments(Long salonId, MultipartFile cosmetologyLicense, MultipartFile idProof,
                                  MultipartFile taxId, MultipartFile bankAccountDetails,
                                  MultipartFile serviceMenu, MultipartFile bestWorkPhotos) {
        // For now, just return true as a placeholder for actual logic
        return true;
    }

    // Temporary method to generate reference ID (you can implement your own ID generation logic)
    private String generateReferenceId() {
        return "REF-" + System.currentTimeMillis();  // Example reference ID
    }

    @GetMapping("/allSalons/{userId}")
    public ResponseEntity<List<SalonDetailsDTO>> getAllSalonsOfOwner(@PathVariable Long userId) {
        List<Salon> salons = salonRepository.findSalonsByOwnerId(userId);

        if (salons == null || salons.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }

        List<SalonDetailsDTO> salonDTOs = salons.stream()
                .map(SalonDetailsDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(salonDTOs);
    }

}
