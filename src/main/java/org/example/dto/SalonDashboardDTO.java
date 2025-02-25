package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class SalonDashboardDTO {
    private List<AppointmentDTO> pendingAppointments;
    private List<AppointmentDTO> upcomingAppointments;
    private List<AppointmentDTO> pastAppointments;
    private EarningsDTO earnings;
}

