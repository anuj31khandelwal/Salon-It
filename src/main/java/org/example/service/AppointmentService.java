package org.example.service;

import org.example.entity.Appointments;
import org.example.entity.AvailableSlot;
import org.example.entity.Barber;
import org.example.entity.Salon;
import org.example.repository.AppointmentRepository;
import org.example.repository.BarberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private BarberRepository barberRepository;

    public List<AvailableSlot> getAvailableBarberSlots(Long barberId, LocalDateTime startTime, int duration) {
        LocalDateTime endTime = startTime.plusMinutes(duration);
        return appointmentRepository.findAvailableSlotsForBarber(barberId, startTime, endTime);
    }

    public List<AvailableSlot> getAvailableSalonSlots(Long salonId, LocalDateTime startTime, int duration) {
        LocalDateTime endTime = startTime.plusMinutes(duration);
        return appointmentRepository.findAvailableSlotsForSalon(salonId, startTime, endTime);
    }

    public boolean isSlotAvailable(Barber barber, LocalDateTime startTime, int duration) {
        LocalDateTime endTime = startTime.plusMinutes(duration);
        List<AvailableSlot> overlappingAppointments = appointmentRepository.findAvailableSlotsForBarber(
                barber.getId(), startTime, endTime
        );
        return !overlappingAppointments.isEmpty();
    }
}
