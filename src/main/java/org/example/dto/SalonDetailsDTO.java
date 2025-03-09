package org.example.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalonDetailsDTO {
    private long id;
    private String name;
    private String address;
    private String openingTime;
    private String closingTime;
    private String phoneNumber;
}