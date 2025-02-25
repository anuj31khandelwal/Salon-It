package org.example.dto;

import java.util.List;

public class BarberDashboardDTO {
    private List<AppointmentDTO> pendingAppointments;
    private List<AppointmentDTO> upcomingAppointments;
    private List<AppointmentDTO> pastAppointments;

    // Getters and Setters
    public List<AppointmentDTO> getPendingAppointments() {
        return pendingAppointments;
    }

    public void setPendingAppointments(List<AppointmentDTO> pendingAppointments) {
        this.pendingAppointments = pendingAppointments;
    }

    public List<AppointmentDTO> getUpcomingAppointments() {
        return upcomingAppointments;
    }

    public void setUpcomingAppointments(List<AppointmentDTO> upcomingAppointments) {
        this.upcomingAppointments = upcomingAppointments;
    }

    public List<AppointmentDTO> getPastAppointments() {
        return pastAppointments;
    }

    public void setPastAppointments(List<AppointmentDTO> pastAppointments) {
        this.pastAppointments = pastAppointments;
    }
}

