package org.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping("/{userId}/dashboard")
    public ResponseEntity<String> getUserDashboard(@PathVariable Long userId) {
        return ResponseEntity.ok("User dashboard data for ID: " + userId);
    }

    @PostMapping("/{userId}/appointments")
    public ResponseEntity<String> bookAppointment(@PathVariable Long userId) {
        return ResponseEntity.ok("Appointment booked for user ID: " + userId);
    }

    @GetMapping("/{userId}/appointments")
    public ResponseEntity<String> getUserAppointments(@PathVariable Long userId) {
        return ResponseEntity.ok("Appointments for user ID: " + userId);
    }

    @PutMapping("/appointments/{appointmentId}/reschedule")
    public ResponseEntity<String> rescheduleAppointment(@PathVariable Long appointmentId) {
        return ResponseEntity.ok("Appointment ID " + appointmentId + " rescheduled");
    }

    @DeleteMapping("/appointments/{appointmentId}/cancel")
    public ResponseEntity<String> cancelAppointment(@PathVariable Long appointmentId) {
        return ResponseEntity.ok("Appointment ID " + appointmentId + " canceled");
    }
}
