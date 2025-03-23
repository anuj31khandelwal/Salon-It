package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.entity.Salon;

@Getter
@Setter
public class SalonDetailsDTO {
    private long id;
    private String name;
    private String address;
    private String openingTime;
    private String closingTime;
    private String phoneNumber;

    public SalonDetailsDTO() {}

    public SalonDetailsDTO(Salon salon){
        this.id = salon.getId();
        this.name = salon.getName();
        this.address = salon.getAddress();
        this.openingTime = String.valueOf(salon.getOpeningTime());
        this.closingTime = String.valueOf(salon.getClosingTime());
        this.phoneNumber = salon.getPhoneNumber();;
    }
}