package org.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookingResponse {
    private List<AppointmentDetails> appointments;
    private double totalBill;
    private String errorMessage;

    public BookingResponse(List<AppointmentDetails> appointments, double totalBill) {
        this.appointments = appointments;
        this.totalBill = totalBill;
    }

    public BookingResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
