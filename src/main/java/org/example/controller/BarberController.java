package org.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/barbers")
public class BarberController {

    @GetMapping("/{barberId}/dashboard")
    public ResponseEntity<String> getBarberDashboard(@PathVariable Long barberId) {
        return ResponseEntity.ok("Barber dashboard data for ID: " + barberId);
    }

    @GetMapping("/{barberId}/appointments")
    public ResponseEntity<String> getBarberAppointments(@PathVariable Long barberId) {
        return ResponseEntity.ok("Appointments for barber ID: " + barberId);
    }

    @PostMapping("/{barberId}/availability")
    public ResponseEntity<String> manageAvailability() {
        return ResponseEntity.ok("Barber availability updated");
    }

    @PutMapping("/{barberId}/appointments/{appointmentId}/status")
    public ResponseEntity<String> updateAppointmentStatus(@PathVariable Long appointmentId) {
        return ResponseEntity.ok("Appointment ID " + appointmentId + " status updated");
    }
}
