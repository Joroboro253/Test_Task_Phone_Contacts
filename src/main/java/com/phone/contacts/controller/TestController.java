package com.phone.contacts.controller;

import com.phone.contacts.dao.UserRepository;
import com.phone.contacts.dto.UserRequest;
import com.phone.contacts.entities.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Api(description = "Test controller")
public class TestController {

    private final UserRepository userRepository;

    @Autowired
    public TestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/test")
    @ApiOperation("Testing")
    public ResponseEntity<String> test(@RequestBody UserRequest userRequest)
    {
        User user = new User();
        user.setEmails(userRequest.getEmails());
        user.setPhones(userRequest.getPhones());
        user.setId(userRequest.getId());
        user.setName(userRequest.getName());
        user.setPassword(userRequest.getPassword());
        user.setRole(userRequest.getRole());
        userRepository.save(user);

        return ResponseEntity.ok("User saved successfully");
    }
}

