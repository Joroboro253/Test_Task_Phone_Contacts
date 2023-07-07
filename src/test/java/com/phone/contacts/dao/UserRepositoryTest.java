package com.phone.contacts.dao;

import com.phone.contacts.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testGetUserByUserName() {
        User user = new User();
        user.setEmails(Collections.singletonList("test@example.com"));
        user.setName("Test User");

        userRepository.save(user);

        User retrievedUser = userRepository.getUserByUserName("test@example.com");

        assertNotNull(retrievedUser);

        assertEquals("Test User", retrievedUser.getName());
    }
}

