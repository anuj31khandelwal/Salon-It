package org.example.dto;

import java.time.LocalDateTime;

public class SlotDTO {
    private Long slotId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long barberId;
    private String barberName;

    // Constructor
    public SlotDTO(Long slotId, LocalDateTime startTime, LocalDateTime endTime, Long barberId, String barberName) {
        this.slotId = slotId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.barberId = barberId;
        this.barberName = barberName;
    }

    // Getters and Setters
    public Long getSlotId() {
        return slotId;
    }

    public void setSlotId(Long slotId) {
        this.slotId = slotId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Long getBarberId() {
        return barberId;
    }

    public void setBarberId(Long barberId) {
        this.barberId = barberId;
    }

    public String getBarberName() {
        return barberName;
    }

    public void setBarberName(String barberName) {
        this.barberName = barberName;
    }
}
