package com.phone.contacts.dao;

import com.phone.contacts.entities.Contact;
import com.phone.contacts.entities.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ContactRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ContactRepository contactRepository;

    @Test
    public void testFindByNameContainingAndUser() {
        User user = new User();
        Contact contact = new Contact();
        contact.setUser(user);
        contact.setName("Test Contact");
        entityManager.persist(user);
        entityManager.persist(contact);
        entityManager.flush();

        List<Contact> foundContacts = contactRepository.findByNameContainingAndUser("Test Contact", user);

        assertThat(foundContacts).isNotEmpty();
        assertThat(foundContacts.get(0).getName()).isEqualTo("Test Contact");
    }
}
