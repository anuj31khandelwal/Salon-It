package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EarningsDTO {
    private double last7Days;
    private double last30Days;
    private double last1Year;
}

