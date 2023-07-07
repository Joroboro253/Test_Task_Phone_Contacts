package com.phone.contacts.dto;

import java.util.List;

public class EmailRequest {

    private List<String> to;
    private String subject;
    private String message;

    public EmailRequest() {
        super();
    }

    public EmailRequest(List<String> to, String subject, String message) {
        this.to = to;
        this.subject = subject;
        this.message = message;
    }

    public void setTo(List<String> to) {
        this.to = to;
    }

    public List<String> getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "EmailRequest [" +
                "to='" + to +
                ", subject='" + subject +
                ", message='" + message +
                ']';
    }
}
