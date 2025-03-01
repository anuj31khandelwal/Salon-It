package org.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class BookingRequest {
    // Getters and Setters
    private List<Long> slotIds;
    private Long customerId;
    private List<Long> serviceIds;

}
