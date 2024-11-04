//package com.example.demo.controller;
//
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/signup")
//public class SignupController {
//
//    private final UserDetailsService userDetailsService;
//    private final PasswordEncoder passwordEncoder;
//
//    public SignupController(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
//        this.userDetailsService = userDetailsService;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @PostMapping("/signup")
//    public String signup(String username, String password, Model model) {
//        // Validate the password (e.g., length, complexity, etc.)
//        if (password.length() < 8) {
//            model.addAttribute("message", "Password doesn't meet the criteria");
//            return "signup";  // Redirect back to signup page with error message
//        }
//
//        // Create the user if password is valid
//        UserDetails newUser = User.withUsername(username)
//                .password(passwordEncoder.encode(password))
//                .roles("USER")
//                .build();
//        ((InMemoryUserDetailsManager) userDetailsService).createUser(newUser);
//
//        model.addAttribute("message", "Account created successfully!");
//        return "login";  // Redirect to login page after successful signup
//    }
//}
//
package com.example.demo.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/signup")
public class SignupController {

    private final PasswordEncoder passwordEncoder;

    public SignupController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequest signupRequest) {
        String username = signupRequest.getUsername();
        String password = signupRequest.getPassword();

        // Validate the password (e.g., length, complexity, etc.)
        if (password.length() < 8) {
            return "Password doesn't meet the criteria"; // Return error message
        }

        // Create user logic can be added here (e.g., saving to the database)

        return "Account created successfully!"; // Return success message
    }
}
