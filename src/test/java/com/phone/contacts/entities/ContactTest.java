package com.phone.contacts.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

public class ContactTest {

    @Test
    public void testContactCreation() {
        // Create a user
        User user = new User();
        user.setId(1);
        user.setName("Test User");

        // Create a contact
        Contact contact = new Contact();
        contact.setName("John");
        contact.setSecondName("Doe");
        contact.setWork("Developer");
        contact.setEmails(new ArrayList<>());
        contact.getPhones().add("1234567890");
        contact.setImage("contact.png");
        contact.setDescription("This is a contact");

        // Set the user
        contact.setUser(user);

        // Check the contact properties
        assertEquals("John", contact.getName());
        assertEquals("Doe", contact.getSecondName());
        assertEquals("Developer", contact.getWork());
        assertNotNull(contact.getEmails());
        assertEquals(0, contact.getEmails().size());
        assertNotNull(contact.getPhones());
        assertEquals(1, contact.getPhones().size());
        assertEquals("1234567890", contact.getPhones().get(0));
        assertEquals("contact.png", contact.getImage());
        assertEquals("This is a contact", contact.getDescription());
        assertEquals(user, contact.getUser());
    }

    @Test
    public void testContactId() {
        // Create a contact with ID
        Contact contact = new Contact();
        contact.setCid(1);
        assertEquals(1, contact.getCid());
    }
}

