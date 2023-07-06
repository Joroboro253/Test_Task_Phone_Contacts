package com.phone.contacts.controller;

import com.phone.contacts.entities.EmailRequest;
import com.phone.contacts.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/welcome")
    public ResponseEntity<String> welcome() {
        return ResponseEntity.ok("Welcome!");
    }

    @PostMapping("/sendemail")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest request) {
        System.out.println(request);
        boolean results = emailService.sendEmail(request.getSubject(), request.getMessage(), request.getTo());

        if (results) {
            return ResponseEntity.ok("Email sent successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email");
        }
    }
}
