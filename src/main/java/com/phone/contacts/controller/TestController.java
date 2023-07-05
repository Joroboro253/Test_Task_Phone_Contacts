package com.phone.contacts.controller;

import com.phone.contacts.dao.UserRepository;
import com.phone.contacts.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/test")
    @ResponseBody
    public String test()
    {
        User user = new User();
        user.setEmail("vluash@Mail.com");
        user.setId(1);
        user.setName("Vluash");
        user.setPassword("Vluash");
        user.setRole("ROLE_NORMAL");
        userRepository.save(user);

        return "This is just for testing";
    }
}
