package org.example.service;

import org.example.dto.SlotDTO;
import org.example.entity.*;
import org.example.enums.AppointmentStatus;
import org.example.enums.PaymentStatus;
import org.example.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SlotService {

    @Autowired
    private SlotRepository slotRepository;

    @Autowired
    private BarberRepository barberRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServiceRepository serviceItemRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    // Get available slots for a barber
    public List<SlotDTO> getAvailableSlotsForBarber(Long barberId, LocalDate date, int duration) {
        LocalDateTime startTime = date.atTime(LocalTime.of(9, 0));
        LocalDateTime endTime = date.atTime(LocalTime.of(21, 0));

        List<Slot> slots = slotRepository.findByBarberIdAndStartTimeBetweenAndIsBookedFalse(barberId, startTime, endTime);

        // Map to DTO
        return slots.stream().map(slot -> new SlotDTO(
                slot.getId(),
                slot.getStartTime(),
                slot.getEndTime(),
                slot.getBarber().getId(),
                slot.getBarber().getName()
        )).collect(Collectors.toList());
    }

    // Get available slots for the whole salon
    public List<SlotDTO> getAvailableSlotsForSalon(Long salonId, LocalDate date, int duration) {
        LocalDateTime startTime = date.atTime(LocalTime.of(9, 0));
        LocalDateTime endTime = date.atTime(LocalTime.of(21, 0));

        List<Slot> slots = slotRepository.findAvailableSlotsForSalon(salonId, startTime, endTime);

        // Map to DTO
        return slots.stream().map(slot -> new SlotDTO(
                slot.getId(),
                slot.getStartTime(),
                slot.getEndTime(),
                slot.getBarber().getId(),
                slot.getBarber().getName()
        )).collect(Collectors.toList());
    }

    // Book a slot and create an appointment
    public void bookSlot(Long slotId, Long customerId, Long serviceId) {
        Slot slot = slotRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Slot not found"));

        if (slot.isBooked()) {
            throw new RuntimeException("Slot is already booked");
        }

        SalonUser customer = userRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        ServiceItem serviceItem = serviceItemRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        // Mark slot as booked
        slot.setBooked(true);
        slotRepository.save(slot);

        // Create appointment
        Appointments appointment = new Appointments();
        appointment.setCustomer(customer);
        appointment.setSalon(slot.getSalon());
        appointment.setBarber(slot.getBarber());
        appointment.setServiceItem(serviceItem);
        appointment.setAppointmentTime(slot.getStartTime());
        appointment.setStatus(AppointmentStatus.PENDING);
        appointment.setPaymentStatus(PaymentStatus.PENDING);

        appointmentRepository.save(appointment);
    }

    // Cancel a booking and free up the slot
    public void cancelSlot(Long slotId) {
        Slot slot = slotRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Slot not found"));

        if (!slot.isBooked()) {
            throw new RuntimeException("Slot is not booked");
        }

        slot.setBooked(false);
        slotRepository.save(slot);

        // Delete the corresponding appointment
        appointmentRepository.deleteByAppointmentTime(slot.getStartTime());
    }
}
