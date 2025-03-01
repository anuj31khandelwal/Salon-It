package org.example.dto;

import java.time.LocalDateTime;

public class AppointmentDetails {
    private Long appointmentId;
    private Long serviceId;
    private String serviceName;
    private double price;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String barberName;
    private String salonName;

    public AppointmentDetails(Long appointmentId, Long serviceId, String serviceName, double price, LocalDateTime startTime, LocalDateTime endTime, String barberName, String salonName) {
        this.appointmentId = appointmentId;
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.price = price;
        this.startTime = startTime;
        this.endTime = endTime;
        this.barberName = barberName;
        this.salonName = salonName;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getPrice() {
        return price;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getBarberName() {
        return barberName;
    }

    public String getSalonName() {
        return salonName;
    }
}
