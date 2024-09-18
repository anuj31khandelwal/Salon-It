package com.example.demo.controller;

import com.example.demo.model.Appointment;
import com.example.demo.model.Service;
import com.example.demo.model.Salon;
import com.example.demo.service.SalonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salon")
public class SalonController {

    @Autowired
    private SalonService salonService;

    @PostMapping("/register")
    public Salon registerSalon(@RequestBody Salon salon) {
        return salonService.registerSalon(salon);
    }

    @PostMapping("/add-service")
    public Service addService(@RequestBody Service service) {
        return salonService.addService(service);
    }

    @GetMapping("/appointments/{id}")
    public List<Appointment> getAppointments(@PathVariable Long id) {
        return salonService.getAppointments(id);
    }

    @GetMapping("/search")
    public List<Salon> searchSalons(@RequestParam String location, @RequestParam String service) {
        return salonService.searchSalons(location, service);
    }
}
