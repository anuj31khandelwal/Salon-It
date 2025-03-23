package org.example.controller;

import org.example.dto.LoginRequest;
import org.example.entity.SalonUser;
import org.example.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
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
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        Optional<SalonUser> userOptional;

        // Check if username is email or phone number
        if (Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", username)) {
            userOptional = userRepository.findByEmail(username);
        } else if (Pattern.matches("^\\d{10}$", username)) {
            userOptional = userRepository.findByPhoneNumber(username);
        } else {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid email or phone number format"));
        }

        // Check if user exists
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "User not found"));
        }

        SalonUser user = userOptional.get();

        // Validate password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid password"));
        }

        // Return user details
        Map<String, Object> response = new HashMap<>();
        response.put("message", "User logged in successfully");
        response.put("customerId", user.getId());
        response.put("customerName",user.getName());
        response.put("customerType",user.getRole());

        return ResponseEntity.ok(response);
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
    public ResponseEntity<String> updateUserProfile(@RequestParam Long userId, @RequestBody SalonUser updatedUser) {
        Optional<SalonUser> existingUserOptional = userRepository.findById(userId);

        if (existingUserOptional.isPresent()) {
            SalonUser existingUser = existingUserOptional.get();

            // Update user details
            existingUser.setName(updatedUser.getName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPhoneNumber(updatedUser.getPhoneNumber());

            // Update password if provided
            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                String encodedPassword = passwordEncoder.encode(updatedUser.getPassword());
                existingUser.setPassword(encodedPassword);
            }

            userRepository.save(existingUser);
            return ResponseEntity.ok("User profile updated successfully");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

}