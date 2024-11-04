package com.example.demo.service;

import com.example.demo.dto.AppointmentRequest;
import com.example.demo.model.Appointment;
import com.example.demo.model.Salon;
import com.example.demo.model.Service;  // Changed to use your custom Service model
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.SalonRepository;
import com.example.demo.repository.ServiceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@org.springframework.stereotype.Service  // Fully qualified to avoid confusion
public class BookingService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private SalonRepository salonRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Transactional
    public Appointment createAppointment(AppointmentRequest request) {
        // Retrieve Salon
        Salon salon = salonRepository.findById(request.getSalonId())
                .orElseThrow(() -> new RuntimeException("Salon not found"));

        // Retrieve Service
        Service service = serviceRepository.findById(request.getServiceId())
                .orElseThrow(() -> new RuntimeException("Service not found"));

        // Parse appointment time from the request
        LocalDateTime appointmentTime = LocalDateTime.parse(request.getAppointmentTime());

        // Create new Appointment object and set properties
        Appointment appointment = new Appointment();
        appointment.setSalon(salon);
        appointment.setService(service);
        appointment.setAppointmentTime(appointmentTime);
        appointment.setCustomerName(request.getCustomerName());
        appointment.setCustomerEmail(request.getCustomerEmail());
        appointment.setConfirmed(false);
        appointment.setCreatedAt(LocalDateTime.now());

        // Save the appointment to the repository
        return appointmentRepository.save(appointment);
    }
}