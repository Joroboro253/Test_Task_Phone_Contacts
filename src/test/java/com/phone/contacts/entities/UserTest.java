package com.phone.contacts.entities;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testUserCreation() {
        // Create a user
        User user = new User();
        user.setName("John Doe");
        user.setEmails(new ArrayList<>());
        user.setPassword("password");
        user.setRole("ROLE_USER");
        user.setEnabled(true);
        user.setImageUrl("user.png");
        user.setAbout("About me");
        user.setPhones(new ArrayList<>());
        user.setContacts(new ArrayList<>());

        // Check the user properties
        assertEquals("John Doe", user.getName());
        assertNotNull(user.getEmails());
        assertEquals(0, user.getEmails().size());
        assertEquals("password", user.getPassword());
        assertEquals("ROLE_USER", user.getRole());
        assertTrue(user.isEnabled());
        assertEquals("user.png", user.getImageUrl());
        assertEquals("About me", user.getAbout());
        assertNotNull(user.getPhones());
        assertEquals(0, user.getPhones().size());
        assertNotNull(user.getContacts());
        assertEquals(0, user.getContacts().size());
    }

    @Test
    public void testUserId() {
        // Create a user with ID
        User user = new User();
        user.setId(1);
        assertEquals(1, user.getId());
    }
}

