package com.example.demo.controller;

import com.example.demo.model.SignupRequest;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

@RestController
@RequestMapping("/signup")
public class SignupController {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public SignupController(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequest signupRequest) {
        String username = signupRequest.getUsername();
        String password = signupRequest.getPassword();
        String userType = signupRequest.getUserType();

        // Validation for password length
        if (password.length() < 8) {
            return "Password must be at least 8 characters long";
        }

        // Check if the username is valid (either phone or email)
        boolean isEmail = Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", username);
        boolean isPhone = Pattern.matches("^\\d{10}$", username);

        if (!isEmail && !isPhone) {
            return "Username must be a valid email or phone number";
        }

        // Check if the username already exists
        if (userRepository.findByUsername(username).isPresent()) {
            return "Username is already taken";
        }

        // Encode the password
        String encodedPassword = passwordEncoder.encode(password);

        // Save user to the database
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(encodedPassword);
        newUser.setUserType(userType);
        userRepository.save(newUser);

        return "Account created successfully!";
    }
}
