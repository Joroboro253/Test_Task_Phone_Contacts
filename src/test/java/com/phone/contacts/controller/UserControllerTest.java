package com.phone.contacts.controller;

import com.phone.contacts.dao.ContactRepository;
import com.phone.contacts.dao.UserRepository;
import com.phone.contacts.entities.Contact;
import com.phone.contacts.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserControllerTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddContact() {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("testUser");

        Contact request = new Contact();
        request.setName("John Doe");

        User user = new User();
        user.setId(1);
        user.setName("testUser");

        when(userRepository.getUserByUserName("testUser")).thenReturn(user);

        ResponseEntity<Contact> response = userController.addContact(request, principal);

        verify(userRepository, times(1)).save(user);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("John Doe", response.getBody().getName());
        assertEquals(user, response.getBody().getUser());
    }

    @Test
    void testShowContactDetails() {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("testUser");

        int contactId = 1;

        User user = new User();
        user.setId(1);
        user.setName("testUser");

        Contact contact = new Contact();
        contact.setCid(contactId);
        contact.setName("John Doe");
        contact.setUser(user);

        when(userRepository.getUserByUserName("testUser")).thenReturn(user);
        when(contactRepository.findById(contactId)).thenReturn(Optional.of(contact));

        ResponseEntity<?> response = userController.showContactDetails(contactId, principal);

        verify(userRepository, times(1)).getUserByUserName("testUser");
        verify(contactRepository, times(1)).findById(contactId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(contact, response.getBody());
    }

    @Test
    void testDeleteContact() {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("testUser");

        int contactId = 1;

        User user = new User();
        user.setId(1);
        user.setName("testUser");

        Contact contact = new Contact();
        contact.setCid(contactId);
        contact.setName("John Doe");
        contact.setUser(user);

        when(userRepository.getUserByUserName("testUser")).thenReturn(user);
        when(contactRepository.findById(contactId)).thenReturn(Optional.of(contact));

        ResponseEntity<?> response = userController.deleteContact(contactId, null, principal);

        verify(userRepository, times(1)).getUserByUserName("testUser");
        verify(contactRepository, times(1)).findById(contactId);
        verify(userRepository, times(1)).save(user);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testUpdateForm() {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("testUser");

        int contactId = 1;

        User user = new User();
        user.setId(1);
        user.setName("testUser");

        Contact existingContact = new Contact();
        existingContact.setCid(contactId);
        existingContact.setName("John Doe");
        existingContact.setUser(user);

        Contact updatedContact = new Contact();
        updatedContact.setName("Updated Name");

        when(userRepository.getUserByUserName("testUser")).thenReturn(user);
        when(contactRepository.findById(contactId)).thenReturn(Optional.of(existingContact));
        when(contactRepository.save(any(Contact.class))).thenReturn(updatedContact);

        ResponseEntity<?> response = userController.updateForm(contactId, updatedContact, principal);

        verify(userRepository, times(1)).getUserByUserName("testUser");
        verify(contactRepository, times(1)).findById(contactId);
        verify(contactRepository, times(1)).save(any(Contact.class));
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedContact, response.getBody());
        assertEquals("Updated Name", existingContact.getName());
    }

    @Test
    void testUserProfile() {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("testUser");

        User user = new User();
        user.setId(1);
        user.setName("testUser");

        when(userRepository.getUserByUserName("testUser")).thenReturn(user);

        ResponseEntity<User> response = userController.userProfile(principal);

        verify(userRepository, times(1)).getUserByUserName("testUser");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }
}

