package com.phone.contacts.controller;

import com.phone.contacts.dao.UserRepository;
import com.phone.contacts.entities.User;
import com.phone.contacts.helper.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class HomeController {

    private BCryptPasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    @Autowired
    public HomeController(BCryptPasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String home(Model m){
        return "Home - Smart Contact Manager";
    }

    @GetMapping("/about")
    public String about(Model m){
        return "About -Smart Contact Manager";
    }

    @PostMapping("/signup")
    public String signup(@RequestBody User user) {
        user.setRole("ROLE_USER");
        user.setEnabled(true);
        user.setImageUrl("default.png");
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User result = userRepository.save(user);

        if(result != null) {
            return "User registered successfully";
        } else {
            return "Failed to register";
        }

    }

    @PostMapping("/do_register")
    public ResponseEntity<Message> registerUser(@Valid @RequestBody User user, BindingResult result) {
        try {
            if(result.hasErrors())
            {
                System.out.println("ERROR " + result.toString());
                return ResponseEntity.badRequest().body(new Message("Validation error", "alert-danger"));
            }

            user.setRole("ROLE_USER");
            user.setEnabled(true);
            user.setImageUrl("default.png");
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            // saving data to database
            User savedUser = userRepository.save(user);
            if(savedUser != null) {
                return ResponseEntity.ok(new Message("Successfully registered", "alert-success"));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Message("Failed to register user", "alert-danger"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Message("Something went wrong: " + e.getMessage(), "alert-danger"));
        }
    }

    @GetMapping("/signin")
    public String customlogin(){
        return "The Login Page";
    }

}
