package org.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/salons")
public class SalonController {

    @PostMapping("/register")
    public ResponseEntity<String> registerSalon() {
        return ResponseEntity.ok("Salon registered successfully");
    }

    @GetMapping("/{salonId}/dashboard")
    public ResponseEntity<String> getSalonDashboard(@PathVariable Long salonId) {
        return ResponseEntity.ok("Salon dashboard for ID: " + salonId);
    }

    @PostMapping("/{salonId}/barbers")
    public ResponseEntity<String> addBarber() {
        return ResponseEntity.ok("Barber added to salon");
    }

    @DeleteMapping("/{salonId}/barbers/{barberId}")
    public ResponseEntity<String> removeBarber(@PathVariable Long barberId) {
        return ResponseEntity.ok("Barber ID " + barberId + " removed");
    }

    @PostMapping("/{salonId}/services")
    public ResponseEntity<String> addService() {
        return ResponseEntity.ok("Service added to salon");
    }

    @PutMapping("/{salonId}/services/{serviceId}")
    public ResponseEntity<String> updateService(@PathVariable Long serviceId) {
        return ResponseEntity.ok("Service ID " + serviceId + " updated");
    }

    @DeleteMapping("/{salonId}/services/{serviceId}")
    public ResponseEntity<String> removeService(@PathVariable Long serviceId) {
        return ResponseEntity.ok("Service ID " + serviceId + " removed");
    }
}
