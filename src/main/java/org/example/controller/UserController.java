package org.example.controller;

import org.example.dto.AppointmentDTO;
import org.example.dto.AppointmentFailureResponse;
import org.example.dto.SlotDTO;
import org.example.dto.UserDashboardDTO;
import org.example.entity.*;
import org.example.enums.AppointmentStatus;
import org.example.repository.BarberRepository;
import org.example.repository.UserRepository;
//import org.example.service.AppointmentService;
import org.example.service.AppointmentService;
import org.example.service.SlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.example.repository.AppointmentRepository;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private final UserRepository userRepository;

    private final AppointmentRepository appointmentRepository;

    private final BarberRepository barberRepository;

    @Autowired
    private final AppointmentService appointmentService;

    public UserController(UserRepository userRepository, AppointmentRepository appointmentRepository, BarberRepository barberRepository,AppointmentService appointmentService) {
        this.userRepository = userRepository;
        this.appointmentRepository = appointmentRepository;
        this.barberRepository = barberRepository;
        this.appointmentService = appointmentService;
    }

    @GetMapping("/{userId}/dashboard")
    public ResponseEntity<UserDashboardDTO> getUserDashboard(@PathVariable Long userId) {
        Optional<SalonUser> customerOptional = userRepository.findById(userId);

        if (customerOptional.isPresent()) {
            SalonUser customer = customerOptional.get();
            LocalDateTime now = LocalDateTime.now();

            List<Appointments> allAppointments = appointmentRepository.findByCustomer(customer);

            // Split appointments into past and upcoming
            List<AppointmentDTO> pastAppointments = allAppointments.stream()
                    .filter(app -> app.getAppointmentTime().isBefore(now))
                    .map(AppointmentDTO::new)
                    .collect(Collectors.toList());

            List<AppointmentDTO> upcomingAppointments = allAppointments.stream()
                    .filter(app -> app.getAppointmentTime().isAfter(now))
                    .map(AppointmentDTO::new)
                    .collect(Collectors.toList());

            // Prepare the dashboard response
            UserDashboardDTO dashboardDTO = new UserDashboardDTO(pastAppointments, upcomingAppointments);

            return ResponseEntity.ok(dashboardDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

//    @PostMapping("/book/appointment")
//    public ResponseEntity<String> bookAppointment(@RequestBody Appointments appointment) {
//        long userId = appointment.getCustomer().getId();
//        Optional<SalonUser> userOptional = userRepository.findById(userId);
//
//        if (userOptional.isPresent()) {
//            SalonUser user = userOptional.get();
//
//            // Link the appointment to the user
//            appointment.setCustomer(user);
//            appointment.setStatus(AppointmentStatus.valueOf("PENDING")); // Default status
//
//            // Save the appointment
//            appointmentRepository.save(appointment);
//
//            return ResponseEntity.ok("Appointment booked successfully for user ID: " + userId);
//        }
//
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
//    }

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

    @Autowired
    private SlotService slotService;

    // Get available slots for a barber
    @GetMapping("/barber/{barberId}/available")
    public ResponseEntity<List<SlotDTO>> getAvailableSlotsForBarber(
            @PathVariable Long barberId,
            @RequestParam LocalDate date,
            @RequestParam int duration) {

        List<SlotDTO> availableSlots = slotService.getAvailableSlotsForBarber(barberId, date, duration);
        return ResponseEntity.ok(availableSlots);
    }

    // Get available slots for a salon
    @GetMapping("/salon/{salonId}/available")
    public ResponseEntity<List<SlotDTO>> getAvailableSlotsForSalon(
            @PathVariable Long salonId,
            @RequestParam LocalDate date,
            @RequestParam int duration) {

        List<SlotDTO> availableSlots = slotService.getAvailableSlotsForSalon(salonId, date, duration);
        return ResponseEntity.ok(availableSlots);
    }

    // Book a slot
    @PostMapping("/{slotId}/book")
    public ResponseEntity<String> bookSlot(
            @PathVariable Long slotId,
            @RequestParam Long customerId,
            @RequestParam Long serviceId) {

        try {
            slotService.bookSlot(slotId, customerId, serviceId);
            return ResponseEntity.ok("Slot booked successfully, appointment created!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Cancel a slot
    @PostMapping("/{slotId}/cancel")
    public ResponseEntity<String> cancelSlot(@PathVariable Long slotId) {
        try {
            slotService.cancelSlot(slotId);
            return ResponseEntity.ok("Slot canceled and freed up!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
//
    @PostMapping("/book/appointment")
    public ResponseEntity<Object> bookAppointment(@RequestBody Appointments appointment) {
        long userId = appointment.getCustomer().getId();
        LocalDateTime startTime = appointment.getAppointmentTime();
        int duration = appointment.getServiceItem().getDuration();

        if (appointment.getBarber() != null) {
            Barber barber = appointment.getBarber();
            boolean isAvailable = appointmentService.isSlotAvailable(barber, startTime, duration);
            if (isAvailable) {
                appointment.setStatus(AppointmentStatus.PENDING);
                appointmentRepository.save(appointment);
                return ResponseEntity.ok("Appointment booked successfully with barber: " + barber.getName());
            }
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Selected slot is not available for this barber");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Barber is required for booking");
    }
}
