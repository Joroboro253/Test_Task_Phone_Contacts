package com.phone.contacts.controller;

import com.phone.contacts.entities.EmailRequest;
import com.phone.contacts.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @RequestMapping("/welcome")
    public String welcome() {
        return " Welcome! ";
    }

    @RequestMapping(value = "/sendemail", method= RequestMethod.POST)
    public ResponseEntity<?> sendEmail(@RequestBody EmailRequest request) {
        System.out.println(request);
        boolean results = this.emailService.sendEmail(request.getSubject(), request.getMessage(), request.getTo());

        if(results) {
            return ResponseEntity.ok("Email is sent successfully... :: ");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Email not sent");
        }
    }
}
