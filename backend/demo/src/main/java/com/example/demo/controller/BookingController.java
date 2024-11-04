package com.example.demo.controller;

import com.example.demo.dto.AppointmentRequest;
import com.example.demo.model.Appointment;
import com.example.demo.model.Service;
import com.example.demo.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import com.example.demo.repository.ServiceRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = "http://localhost:3000")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private ServiceRepository serviceRepository;

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("API is working!");
    }

    @PostMapping("/appointments/book")
    public ResponseEntity<Map<String, Boolean>> bookAppointment(@RequestBody AppointmentRequest request) {
        try {
            Appointment appointment = bookingService.createAppointment(request);

            // Here you check if the appointment was confirmed by the salon
            boolean isConfirmed = appointment.isConfirmed(); // Assuming this field is set in your logic

            // Return only the confirmation status
            Map<String, Boolean> response = new HashMap<>();
            response.put("confirmed", isConfirmed);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @GetMapping("/salons/{salonId}/services")
    public ResponseEntity<List<Service>> getSalonServices(@PathVariable Long salonId) {
        List<Service> services = serviceRepository.findBySalonIdAndActiveTrue(salonId);
        return ResponseEntity.ok(services);
    }
}
