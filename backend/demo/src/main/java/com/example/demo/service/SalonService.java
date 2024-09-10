package com.example.demo.service;

import com.example.demo.model.Appointment;
import com.example.demo.model.Service;
import com.example.demo.model.Salon;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.SalonRepository;
import com.example.demo.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

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
}
