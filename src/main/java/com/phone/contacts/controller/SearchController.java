package com.phone.contacts.controller;

import com.phone.contacts.dao.ContactRepository;
import com.phone.contacts.dao.UserRepository;
import com.phone.contacts.entities.Contact;
import com.phone.contacts.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SearchController {

    private UserRepository userRepository;
    private ContactRepository contactRepository;

    @Autowired
    public SearchController(UserRepository userRepository, ContactRepository contactRepository) {
        this.userRepository = userRepository;
        this.contactRepository = contactRepository;
    }

    @GetMapping("/search/{query}")
    public ResponseEntity<List<Contact>> search(@PathVariable("query") String query, Principal principal) {
        System.out.println(query);
        User user = userRepository.getUserByUserName(principal.getName());
        List<Contact> contacts = contactRepository.findByNameContainingAndUser(query, user);
        return ResponseEntity.ok(contacts);
    }
}
