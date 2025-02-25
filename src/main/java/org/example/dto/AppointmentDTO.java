package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.entity.Appointments;

import java.time.LocalDateTime;

@Getter
@Setter
public class AppointmentDTO {
    private Long id;
    private String customerName;
    private String serviceName;
    private LocalDateTime appointmentTime;
    private String status;

    public AppointmentDTO(Appointments appointment) {
        this.id = appointment.getId();
        this.customerName = appointment.getCustomer().getName();
        this.serviceName = appointment.getServiceItem().getName();
        this.appointmentTime = appointment.getAppointmentTime();
        this.status = String.valueOf(appointment.getStatus());
    }
}
