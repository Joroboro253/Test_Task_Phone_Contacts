package com.phone.contacts.controller;

import com.phone.contacts.dao.UserRepository;
import com.phone.contacts.entities.User;
import com.phone.contacts.helper.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RegistrationController {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public RegistrationController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<Message> registerUser(@RequestBody User user) {
        try {
            // Check if a user with the same username exists
            User existingUser = userRepository.getUserByUserName(user.getName());

            if (existingUser != null) {
                return ResponseEntity.badRequest().body(new Message("Username is already taken", "alert-danger"));
            }

            // Setting default values and password hashing
            user.setRole("ROLE_USER");
            user.setEnabled(true);
            user.setImageUrl("default.png");
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            // Saving user data in the database
            User savedUser = userRepository.save(user);

            if (savedUser != null) {
                return ResponseEntity.ok(new Message("Successfully registered", "alert-success"));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new Message("Failed to register user", "alert-danger"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Message("Something went wrong: " + e.getMessage(), "alert-danger"));
        }
    }


}
