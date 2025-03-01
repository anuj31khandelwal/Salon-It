package org.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class SlotDTO {
    private List<Long> slotId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long barberId;
    private String barberName;

    // Constructor
    public SlotDTO(List<Long> slotId, LocalDateTime startTime, LocalDateTime endTime, Long barberId, String barberName) {
        this.slotId = slotId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.barberId = barberId;
        this.barberName = barberName;
    }
}
