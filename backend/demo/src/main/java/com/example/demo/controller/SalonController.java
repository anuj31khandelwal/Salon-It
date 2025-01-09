package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.PendingAppointmentDTO;
import com.example.demo.model.Appointment;
import com.example.demo.model.Service;
import com.example.demo.model.Salon;
import com.example.demo.service.SalonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/salon")
public class SalonController {

    @Autowired
    private SalonService salonService;

    @PostMapping("/register")
    public Salon registerSalon(@RequestBody Salon salon) {
        return salonService.registerSalon(salon);
    }

    @PostMapping("/add-service")
    public Service addService(@RequestBody Service service) {
        return salonService.addService(service);
    }

    @GetMapping("/appointments/{id}")
    public List<Appointment> getAppointments(@PathVariable Long id) {
        return salonService.getAppointments(id);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Salon>> searchSalons(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String service,
            @RequestParam(required = false) String salon) {

        List<Salon> salons;

        if (salon != null && !salon.isEmpty()) {
            // Search by salon name
            salons = salonService.searchBySalonName(salon);
        } else if (location != null && !location.isEmpty() && service != null && !service.isEmpty()) {
            // Search by both location and service
            salons = salonService.searchSalonsByLocationAndService(location, service);
        } else if (location != null && !location.isEmpty()) {
            // Search by location only
            salons = salonService.searchByLocation(location);
        } else if (service != null && !service.isEmpty()) {
            // Search by service only
            salons = salonService.searchByService(service);
        } else {
            salons = salonService.getAllSalons();  // Return all salons if no filters are applied
        }

        if (salons.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(salons); // Return 204 if no salons found
        }

        return ResponseEntity.ok(salons); // Return 200 with found salons
    }


    @GetMapping("/{salonId}/pending-appointments")
    public List<PendingAppointmentDTO> getPendingAppointments(@PathVariable Long salonId) {
        return salonService.getPendingAppointments(salonId);
    }


    @PutMapping("/confirm-appointment/{appointmentId}")
    public ResponseEntity<Appointment> confirmAppointment(@PathVariable Long appointmentId) {
        Appointment confirmedAppointment = salonService.confirmAppointment(appointmentId);
        return ResponseEntity.ok(confirmedAppointment);
    }

//    @GetMapping("/{salonId}/services")
//    public ResponseEntity<ApiResponse<List<Service>>> getSalonServices(@PathVariable Long salonId) {
//        List<Service> services = salonService.getSalonServices(salonId);
//        return ResponseEntity.ok(new ApiResponse<>(true,
//                "Services retrieved successfully",
//                services,
//                services.size()));
//    }
    @GetMapping("/{id}")
    public ResponseEntity<Salon> getSalonById(@PathVariable Long id) {
        Salon salon = salonService.getSalonById(id);
        if (salon == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(salon);
    }

    @GetMapping("/{id}/services")
    public ResponseEntity<List<Service>> getServicesBySalonId(@PathVariable Long id) {
        List<Service> services = salonService.getServicesBySalonId(id);
        if (services.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(services);
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

}