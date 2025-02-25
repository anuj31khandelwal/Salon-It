package org.example.controller;

import org.example.dto.AppointmentDTO;
import org.example.entity.Appointments;
import org.example.entity.Barber;
import org.example.enums.AppointmentStatus;
import org.example.repository.AppointmentRepository;
import org.example.repository.BarberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/barbers")
public class BarberController {

    private final AppointmentRepository appointmentRepository;
    private final BarberRepository barberRepository;

    public BarberController(AppointmentRepository appointmentRepository, BarberRepository barberRepository) {
        this.appointmentRepository = appointmentRepository;
        this.barberRepository = barberRepository;
    }

    @GetMapping("/{barberId}/dashboard")
    public ResponseEntity<String> getBarberDashboard(@PathVariable Long barberId) {
        return ResponseEntity.ok("Barber dashboard data for ID: " + barberId);
    }

    @GetMapping("/{barberId}/allAppointments")
    public ResponseEntity<List<AppointmentDTO>> getBarberAppointments(@PathVariable Long barberId) {
        List<Appointments> appointments = appointmentRepository.findByBarberId(barberId);

        List<AppointmentDTO> appointmentDTOs = appointments.stream()
                .map(AppointmentDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(appointmentDTOs);
    }


    @PutMapping("/{barberId}/availability")
    public ResponseEntity<String> manageAvailability(@PathVariable Long barberId, @RequestBody boolean isAvailable) {
        Optional<Barber> barberOptional = barberRepository.findById(barberId);

        if (barberOptional.isPresent()) {
            Barber barber = barberOptional.get();
            barber.setAvailable(isAvailable);
            barberRepository.save(barber);

            String status = isAvailable ? "available" : "unavailable";
            return ResponseEntity.ok("Barber ID " + barberId + " is now " + status);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Barber not found with ID: " + barberId);
        }
    }

    @PutMapping("/{barberId}/appointments/{appointmentId}/status")
    public ResponseEntity<String> updateAppointmentStatus(@PathVariable Long barberId,
                                                          @PathVariable Long appointmentId,
                                                          @RequestBody String status) {
        Optional<Appointments> appointmentOptional = appointmentRepository.findById(appointmentId);

        if (appointmentOptional.isPresent()) {
            Appointments appointment = appointmentOptional.get();

            if (appointment.getBarber().getId() != barberId) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Appointment does not belong to Barber ID: " + barberId);
            }

            appointment.setStatus(AppointmentStatus.valueOf(status));
            appointmentRepository.save(appointment);

            return ResponseEntity.ok("Appointment ID " + appointmentId + " status updated to: " + status);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Appointment not found with ID: " + appointmentId);
        }
    }

}
