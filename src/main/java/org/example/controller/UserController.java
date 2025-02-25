package org.example.controller;

import org.example.entity.Appointments;
import org.example.enums.AppointmentStatus;
import org.example.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.example.entity.SalonUser;
import org.example.repository.AppointmentRepository;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    private final AppointmentRepository appointmentRepository;

    public UserController(UserRepository userRepository, AppointmentRepository appointmentRepository) {
        this.userRepository = userRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @GetMapping("/{userId}/dashboard")
    public ResponseEntity<String> getUserDashboard(@PathVariable Long userId) {
        return ResponseEntity.ok("User dashboard data for ID: " + userId);
    }

    @PostMapping("/book/appointment")
    public ResponseEntity<String> bookAppointment(@RequestBody Appointments appointment) {
        long userId = appointment.getCustomer().getId();
        Optional<SalonUser> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            SalonUser user = userOptional.get();

            // Link the appointment to the user
            appointment.setCustomer(user);
            appointment.setStatus(AppointmentStatus.valueOf("PENDING")); // Default status

            // Save the appointment
            appointmentRepository.save(appointment);

            return ResponseEntity.ok("Appointment booked successfully for user ID: " + userId);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    @GetMapping("/{userId}/appointments")
    public ResponseEntity<List<Appointments>> getUserAppointments(@PathVariable Long userId) {
        Optional<SalonUser> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            List<Appointments> appointments = appointmentRepository.findByCustomerId(userId);

            if (appointments.isEmpty()) {
                return ResponseEntity.ok(Collections.emptyList()); // Return empty list if no appointments
            }
            return ResponseEntity.ok(appointments);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/appointments/{appointmentId}/reschedule")
    public ResponseEntity<String> rescheduleAppointment(@PathVariable Long appointmentId, @RequestBody LocalDateTime newTime) {
        Optional<Appointments> appointmentOptional = appointmentRepository.findById(appointmentId);

        if (appointmentOptional.isPresent()) {
            Appointments appointment = appointmentOptional.get();
            appointment.setAppointmentTime(newTime);
            appointmentRepository.save(appointment);

            return ResponseEntity.ok("Appointment ID " + appointmentId + " rescheduled to: " + newTime);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Appointment not found with ID: " + appointmentId);
        }
    }

    @DeleteMapping("/appointments/{appointmentId}/cancel")
    public ResponseEntity<String> cancelAppointment(@PathVariable Long appointmentId) {
        Optional<Appointments> appointmentOptional = appointmentRepository.findById(appointmentId);

        if (appointmentOptional.isPresent()) {
            Appointments appointment = appointmentOptional.get();
            appointmentRepository.delete(appointment);

            return ResponseEntity.ok("Appointment ID " + appointmentId + " canceled");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Appointment not found with ID: " + appointmentId);
        }
    }

}
