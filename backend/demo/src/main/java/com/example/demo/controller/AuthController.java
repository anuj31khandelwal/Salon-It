package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authorization")
public class AuthController {

    // Existing GET method for login page
    @GetMapping("/login")
    public String loginPage() {
        return "Please enter your username and password to log in.";
    }

    // New POST method for login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        // Implement your authentication logic here
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        String userType = loginRequest.getUserType();

        // Example: Simple validation (replace with your actual authentication logic)
        if ("validUser".equals(username) && "validPassword".equals(password)) {
            return ResponseEntity.ok("Login successful!");
        } else {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }

    @GetMapping("/home")
    public String homePage() {
        return "Welcome to the home page!";
    }
}

