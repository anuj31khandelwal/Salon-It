package org.example.service;

import org.example.dto.AppointmentDetails;
import org.example.dto.BookingResponse;
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
    // Get or generate slots for a barber
    public List<SlotDTO> getOrGenerateSlotsForBarber(Long salonId, Long barberId, LocalDate date, int duration) {
        LocalDateTime startTime = date.atTime(LocalTime.of(9, 0));
        LocalDateTime endTime = date.atTime(LocalTime.of(21, 0));

        List<Slot> slots = slotRepository.findByBarberIdAndStartTimeBetweenAndIsBookedFalse(barberId, startTime, endTime);

        // If no slots exist, generate them
        if (slots.isEmpty()) {
            slots = generateSlots(salonId, barberId, startTime, endTime, 15); // Generate 15-min slots
        }

        return mergeAvailableSlots(slots, duration);
    }

    // Get or generate slots for the whole salon
    public List<SlotDTO> getOrGenerateSlotsForSalon(Long salonId, LocalDate date, int duration) {
        LocalDateTime startTime = date.atTime(LocalTime.of(9, 0));
        LocalDateTime endTime = date.atTime(LocalTime.of(21, 0));

        List<Slot> slots = slotRepository.findBySalonIdAndStartTimeBetweenAndIsBookedFalse(salonId, startTime, endTime);

        // If no slots exist, generate them for all barbers
        if (slots.isEmpty()) {
            List<Barber> barbers = barberRepository.findBySalonId(salonId);
            for (Barber barber : barbers) {
                slots.addAll(generateSlots(salonId, barber.getId(), startTime, endTime, 15)); // Generate 15-min slots
            }
        }

        return mergeAvailableSlots(slots, duration);
    }

    // Slot generation logic
    public List<Slot> generateSlots(Long salonId, Long barberId, LocalDateTime startTime, LocalDateTime endTime, int duration) {
        Barber barber = barberRepository.findById(barberId)
                .orElseThrow(() -> new RuntimeException("Barber not found"));

        LocalDateTime current = startTime;
        List<Slot> slots = new ArrayList<>();

        while (current.plusMinutes(duration).isBefore(endTime) || current.plusMinutes(duration).equals(endTime)) {
            Slot slot = new Slot();
            slot.setBarber(barber);
            slot.setSalon(barber.getSalon());
            slot.setStartTime(current);
            slot.setEndTime(current.plusMinutes(duration));
            slot.setBooked(false);

            slots.add(slot);
            current = current.plusMinutes(duration);
        }

        return slotRepository.saveAll(slots);
    }

private List<SlotDTO> mergeAvailableSlots(List<Slot> slots, int duration) {
    List<SlotDTO> mergedSlots = new ArrayList<>();

    int requiredSlots = duration / 15; // Assuming base slot size is 15 minutes

    for (int i = 0; i <= slots.size() - requiredSlots; i++) {
        boolean canMerge = true;
        List<Long> mergedSlotIds = new ArrayList<>();
        mergedSlotIds.add(slots.get(i).getId()); // Add first slot ID

        // Check if consecutive slots are available
        for (int j = 1; j < requiredSlots; j++) {
            if (!slots.get(i + j).getStartTime().equals(slots.get(i + j - 1).getEndTime())) {
                canMerge = false;
                break;
            }
            mergedSlotIds.add(slots.get(i + j).getId()); // Collect merged slot IDs
        }

        if (canMerge) {
            Slot firstSlot = slots.get(i);
            Slot lastSlot = slots.get(i + requiredSlots - 1);

            mergedSlots.add(new SlotDTO(
                    mergedSlotIds,
                    firstSlot.getStartTime(),
                    lastSlot.getEndTime(),
                    firstSlot.getBarber().getId(),
                    firstSlot.getBarber().getName()
            ));

            // Skip merged slots to avoid overlaps
            i += requiredSlots - 1;
        }
    }

    return mergedSlots;
}

public BookingResponse bookSlots(List<Long> slotIds, Long customerId, List<Long> serviceIds) {
    List<Slot> slots = slotRepository.findAllById(slotIds);

    List<Long> bookedSlots = new ArrayList<>();
    for (Slot slot : slots) {
        if (slot.isBooked()) {
            bookedSlots.add(slot.getId());
        }
    }
    if (!bookedSlots.isEmpty()) {
        return new BookingResponse("Slots already booked: " + bookedSlots); // Return error response
    }

    // Book all merged slots
    for (Slot slot : slots) {
        slot.setBooked(true);
    }
    slotRepository.saveAll(slots);

    // Create appointments and calculate total bill
    List<AppointmentDetails> appointmentDetailsList = new ArrayList<>();
    double totalBill = 0.0;

    Slot firstSlot = slots.get(0);
    Slot lastSlot = slots.get(slots.size() - 1);

    SalonUser customer = userRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

    for (Long serviceId : serviceIds) {
        ServiceItem service = serviceItemRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found with ID: " + serviceId));

        Appointments appointment = new Appointments();
        appointment.setCustomer(customer);
        appointment.setBarber(firstSlot.getBarber());
        appointment.setSalon(firstSlot.getSalon());
        appointment.setServiceItem(service);
        appointment.setAppointmentTime(firstSlot.getStartTime());
        appointment.setStatus(AppointmentStatus.PENDING);
        appointment.setPaymentStatus(PaymentStatus.PENDING);

        appointmentRepository.save(appointment);

        // Prepare appointment details
        appointmentDetailsList.add(new AppointmentDetails(
                appointment.getId(),
                service.getId(),
                service.getName(),
                service.getPrice(),
                firstSlot.getStartTime(),
                lastSlot.getEndTime(),
                firstSlot.getBarber().getName(),
                firstSlot.getSalon().getName()
        ));

        // Update total bill
        totalBill += service.getPrice();
    }

    // Return the complete booking response
    return new BookingResponse(appointmentDetailsList, totalBill);
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
