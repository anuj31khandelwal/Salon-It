package com.example.demo.controller;

import com.example.demo.model.Salon;
import com.example.demo.service.SuperAdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/superadmin")
public class SuperAdminController {

    @Autowired
    private SuperAdminService superAdminService;

    @GetMapping("/salons")
    public List<Salon> getAllSalons() {
        return superAdminService.getAllSalons();
    }
}
