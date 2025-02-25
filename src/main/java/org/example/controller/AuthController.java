package org.example.controller;

import org.example.dto.LoginRequest;
import org.example.entity.SalonUser;
import org.example.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody SalonUser salonUser) {
        String username = salonUser.getName();
        String password = salonUser.getPassword();
        String phoneNumber = salonUser.getPhoneNumber();
        String email = salonUser.getEmail();
        String role = String.valueOf(salonUser.getRole());

        // Validation for password length
        if (password == null || password.length() < 8) {
            return ResponseEntity.badRequest().body("Password must be at least 8 characters long");
        }

        // Check if the username is valid (either phone or email)
        boolean isEmail = (email != null && Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", email));
        boolean isPhone = (phoneNumber != null && Pattern.matches("^\\d{10}$", phoneNumber));

        if (!isEmail && !isPhone) {
            return ResponseEntity.badRequest().body("Username must be a valid email or phone number");
        }

        // Check if the email or phone number already exists
        if (email != null && userRepository.findByEmail(email).isPresent()) {
            return ResponseEntity.badRequest().body("Email is already taken");
        }
        if (phoneNumber != null && userRepository.findByPhoneNumber(phoneNumber).isPresent()) {
            return ResponseEntity.badRequest().body("Phone number is already taken");
        }

        // Encode the password
        String encodedPassword = passwordEncoder.encode(password);
        salonUser.setPassword(encodedPassword);

        // Save the user
        userRepository.save(salonUser);

        return ResponseEntity.ok("User registered successfully");
    }


    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        // Check if username is email or phone number
        Optional<SalonUser> userOptional;

        if (Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", username)) {
            userOptional = userRepository.findByEmail(username);
        } else if (Pattern.matches("^\\d{10}$", username)) {
            userOptional = userRepository.findByPhoneNumber(username);
        } else {
            return ResponseEntity.badRequest().body("Invalid email or phone number format");
        }

        // Check if user exists
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }

        SalonUser user = userOptional.get();

        // Validate password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
        }

        // Generate JWT (if using token-based auth) or return success
        return ResponseEntity.ok("User logged in successfully");
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser() {
        return ResponseEntity.ok("User logged out successfully");
    }

    @GetMapping("/profile")
    public Optional<SalonUser> getUserProfile(@RequestParam Long userId) {
        if(userRepository.existsById(userId)) {
            return userRepository.findById(userId);
        }
        return Optional.empty();
    }

    @PutMapping("/profile/update")
    public ResponseEntity<String> updateUserProfile() {
        return ResponseEntity.ok("User profile updated");
    }
}