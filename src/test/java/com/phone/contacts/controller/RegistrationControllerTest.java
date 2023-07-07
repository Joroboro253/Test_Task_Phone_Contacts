package com.phone.contacts.controller;

import com.phone.contacts.dao.UserRepository;
import com.phone.contacts.entities.User;
import com.phone.contacts.helper.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class RegistrationControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private RegistrationController registrationController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        registrationController = new RegistrationController(userRepository, passwordEncoder);
    }

    @Test
    public void testRegisterUser() {
        User user = new User();
        user.setName("testUser");
        user.setPassword("password");

        when(userRepository.getUserByUserName(user.getName())).thenReturn(null);
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);

        ResponseEntity<Message> response = registrationController.registerUser(user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully registered", response.getBody().getContent());
    }
}

