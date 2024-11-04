package com.example.demo.dto;

import java.time.LocalDateTime;

public class PendingAppointmentDTO {
    private Long id;
    private LocalDateTime appointmentTime;
    private String customerName;
    private boolean confirmed;

    public PendingAppointmentDTO(Long id, LocalDateTime appointmentTime, String customerName, boolean confirmed) {
        this.id = id;
        this.appointmentTime = appointmentTime;
        this.customerName = customerName;
        this.confirmed = confirmed;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public LocalDateTime getAppointmentTime() { return appointmentTime; }
    public String getCustomerName() { return customerName; }
    public boolean isConfirmed() { return confirmed; }
}
