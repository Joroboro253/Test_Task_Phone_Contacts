package com.phone.contacts.dto;

import java.util.List;

public class UserRequest {
    private List<String> emails;
    private List<String> phones;
    private int id;
    private String name;
    private String password;
    private String role;

    public UserRequest() {
    }

    public UserRequest(List<String> emails, List<String> phones, int id, String name, String password, String role) {
        this.emails = emails;
        this.phones = phones;
        this.id = id;
        this.name = name;
        this.password = password;
        this.role = role;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
