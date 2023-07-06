package com.phone.contacts.service;


import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

@Service
public class EmailService {

    public boolean sendEmail(String subject, String message, List<String> to){
        boolean flag = false;

        String form ="vlyashchenko01@gmail.com";
        String host="smtp.gmail.com";

        Properties properties=System.getProperties();
        System.out.println("PROPERTIES"+properties);

        properties.put("mail.smtp.host",host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        //getting session object
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("vlyashchenko01@gmail.com", "dfgnjdfk");
            }
        });

        session.setDebug(true);

        // compare the message [text, multi media]

        try {
            MimeMessage m = new MimeMessage(session);
            m.setFrom(form);
            m.setSubject(subject);
            m.setText(message);

            for(String recipient : to){
                m.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            }

            // send the message using Tramsport class
            Transport.send(m);
            System.out.println("Sent success........");
            flag=true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

}

