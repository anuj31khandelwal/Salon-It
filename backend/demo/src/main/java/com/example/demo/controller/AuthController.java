package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @GetMapping("/login")
    public String loginPage() {
        return "Please enter your username and password to log in.";
    }

    @GetMapping("/home")
    public String homePage() {
        return "Welcome to the home page!";
    }
}
