package org.example.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AvailableSlot {
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public AvailableSlot(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public AvailableSlot() {}


}
