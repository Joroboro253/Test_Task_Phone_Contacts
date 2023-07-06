package com.phone.contacts.controller;

import com.phone.contacts.dao.UserRepository;
import com.phone.contacts.dto.UserRequest;
import com.phone.contacts.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TestController {

    private final UserRepository userRepository;

    @Autowired
    public TestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/test")
    public ResponseEntity<String> test(@RequestBody UserRequest userRequest)
    {
        User user = new User();
        user.setEmails(userRequest.getEmails()); // Изменение: emails теперь является списком
        user.setPhones(userRequest.getPhones()); // Изменение: phones теперь является списком
        user.setId(userRequest.getId());
        user.setName(userRequest.getName());
        user.setPassword(userRequest.getPassword());
        user.setRole(userRequest.getRole());
        userRepository.save(user);

        return ResponseEntity.ok("User saved successfully");
    }
}

