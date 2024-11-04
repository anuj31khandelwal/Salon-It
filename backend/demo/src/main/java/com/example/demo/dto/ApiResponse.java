package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private int count;

    public ApiResponse(boolean success, String message, T data, int count) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.count = count;
    }

}
