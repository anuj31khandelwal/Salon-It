package com.example.demo.service;

import com.example.demo.dto.PendingAppointmentDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Appointment;
import com.example.demo.model.Service;
import com.example.demo.model.Salon;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.SalonRepository;
import com.example.demo.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class SalonService {

    @Autowired
    private SalonRepository salonRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    public Salon registerSalon(Salon salon) {
        return salonRepository.save(salon);
    }

    public Service addService(Service service) {
        return serviceRepository.save(service);
    }

    public List<Appointment> getAppointments(Long salonId) {
        return appointmentRepository.findBySalonId(salonId);
    }

    public List<Salon> searchSalons(String location, String service) {
        return salonRepository.findByLocationAndService(location, service);
    }

    public List<PendingAppointmentDTO> getPendingAppointments(Long salonId) {
        List<Appointment> pendingAppointments = appointmentRepository.findBySalonIdAndConfirmedFalse(salonId);
        return pendingAppointments.stream()
                .map(appointment -> new PendingAppointmentDTO(
                        appointment.getId(),
                        appointment.getAppointmentTime(),
                        appointment.getCustomerName(),
                        appointment.isConfirmed()))
                .collect(Collectors.toList());
    }


    // Confirm an appointment
    public Appointment confirmAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found for id: " + appointmentId));
        appointment.setConfirmed(true);  // Update the appointment to be confirmed
        return appointmentRepository.save(appointment);  // Save the updated appointment
    }
    public List<Service> getSalonServices(Long salonId) {
        // First check if salon exists
        Salon salon = salonRepository.findById(salonId)
                .orElseThrow(() -> new ResourceNotFoundException("Salon not found with id: " + salonId));

        // Get all active services for the salon
        return serviceRepository.findBySalonIdAndActiveTrue(salonId);
    }
}
