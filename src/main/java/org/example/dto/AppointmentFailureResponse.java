package org.example.dto;

import org.example.entity.Barber;

import java.time.LocalTime;
import java.util.List;

public class AppointmentFailureResponse {
    private List<LocalTime[]> availableSlots;
    private List<Barber> availableBarbers;

    public AppointmentFailureResponse(List<LocalTime[]> availableSlots, List<Barber> availableBarbers) {
        this.availableSlots = availableSlots;
        this.availableBarbers = availableBarbers;
    }

    public List<LocalTime[]> getAvailableSlots() {
        return availableSlots;
    }

    public List<Barber> getAvailableBarbers() {
        return availableBarbers;
    }
}
