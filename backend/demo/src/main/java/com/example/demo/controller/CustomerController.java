package com.example.demo.controller;

import com.example.demo.model.Appointment;
import com.example.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/book")
    public Appointment bookAppointment(@RequestBody Appointment appointment) {
        return customerService.bookAppointment(appointment);
    }

    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<?> cancelAppointment(@PathVariable Long id) {
        return customerService.cancelAppointment(id);
    }
}
