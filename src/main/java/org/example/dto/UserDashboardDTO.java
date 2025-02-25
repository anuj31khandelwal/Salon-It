package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UserDashboardDTO {
    private List<AppointmentDTO> pastAppointments;
    private List<AppointmentDTO> upcomingAppointments;
}

