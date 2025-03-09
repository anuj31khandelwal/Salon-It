package org.example.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceItemDTO {
    private long id;
    private String name;
    private Double price;
    private Integer duration;
}
