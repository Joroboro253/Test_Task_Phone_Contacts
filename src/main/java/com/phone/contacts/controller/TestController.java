package com.phone.contacts.controller;

import com.phone.contacts.dao.UserRepository;
import com.phone.contacts.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    private final UserRepository userRepository;

    @Autowired
    public TestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/test")
    public ResponseEntity<String> test()
    {
        User user = new User();
        user.setEmail("vluash@Mail.com");
        user.setId(1);
        user.setName("Vluash");
        user.setPassword("Vluash");
        user.setRole("ROLE_NORMAL");
        userRepository.save(user);

        return ResponseEntity.ok("This is just for testing");
    }
}
