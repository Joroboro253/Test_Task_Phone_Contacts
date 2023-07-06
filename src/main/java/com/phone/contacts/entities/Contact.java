package com.phone.contacts.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "CONTACT")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cid;
    private String name;
    private String secondName;
    private String work;

    @ElementCollection
    private List<String> emails;

    @ElementCollection
    private List<String> phones;

    private String image;
    @Column(length = 1000)
    private String description;

    @ManyToOne()
    @JsonIgnore
    private User user;

    public Contact() {
    }

    public Contact(int cid, String name, String secondName, String work, List<String> emails, List<String> phones, String image, String description, User user) {
        this.cid = cid;
        this.name = name;
        this.secondName = secondName;
        this.work = work;
        this.emails = emails;
        this.phones = phones;
        this.image = image;
        this.description = description;
        this.user = user;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int id) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
