package com.edubox.admin.mail;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;

public class MailData {
    public static void sendEmail(String recipient, String subject, String messageText) {
        // Setup properties for Gmail SMTP server
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Create a JavaMail session object
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("your.email@gmail.com", "your.password");
            }
        });

        try {
            // Create a MimeMessage object and set the required properties
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("your.email@gmail.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(subject);
            message.setText(messageText);

            // Send the message using the Transport class
            Transport.send(message);
            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
