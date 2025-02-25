package org.example.controller;

import org.example.dto.AppointmentDTO;
import org.example.dto.BarberDashboardDTO;
import org.example.entity.Appointments;
import org.example.entity.Barber;
import org.example.enums.AppointmentStatus;
import org.example.repository.AppointmentRepository;
import org.example.repository.BarberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
    public ResponseEntity<BarberDashboardDTO> getBarberDashboard(@PathVariable Long barberId) {
        Optional<Barber> barberOptional = barberRepository.findById(barberId);

        if (barberOptional.isPresent()) {
            Barber barber = barberOptional.get();
            LocalDateTime now = LocalDateTime.now();

            List<Appointments> allAppointments = appointmentRepository.findByBarber(barber);

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

            // Build the dashboard DTO
            BarberDashboardDTO dashboardDTO = new BarberDashboardDTO();
            dashboardDTO.setPendingAppointments(pendingAppointments);
            dashboardDTO.setUpcomingAppointments(upcomingAppointments);
            dashboardDTO.setPastAppointments(pastAppointments);

            return ResponseEntity.ok(dashboardDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
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
