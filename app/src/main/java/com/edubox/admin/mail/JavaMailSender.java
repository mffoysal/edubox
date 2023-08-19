package com.edubox.admin.mail;

import java.util.Properties;
import javax.activation.DataSource;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

public class JavaMailSender {
    public static void main(String[] args) {
        // Sender's email credentials
        final String senderEmail = "mdfarhadfoysal144@gmail.com";
        final String senderPassword = "nkdfdwljhumdzbva";

        // Recipient's email address
        String recipientEmail = "mff585855075@gmail.com";

        // Email subject and content
        String emailSubject = "Welcome to ~EdUBox~";
        String emailContent = "Hello, this is a test email sent from a EdUBox desktop application using JavaMail!";
        String attFile = "./assets/profilepicicon.jpg";
        String footer = "\nFarhad Foysal\n01585855075";

        // Setup properties for Gmail SMTP server
        Properties props = new Properties();

        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
//      props.put("mail.smtp.proxy.host", "proxy.example.com");
//      props.put("mail.smtp.proxy.port", "8080");
        props.put("mail.smtp.starttls.enable", "false");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.ssl.enable", "true");

        // Create a JavaMail session object
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            // Create a MimeMessage object and set the required properties
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            message.setSubject(emailSubject);
//            message.setText(emailContent);





            // Create a multipart message
            Multipart multipart = new MimeMultipart();

            // Create and add the text content
            MimeBodyPart textPart = new MimeBodyPart();
            String textContent = emailContent;
            textPart.setText(textContent);
            multipart.addBodyPart(textPart);

            if(!attFile.isEmpty()) {
                // Create and add the attachment


                MimeBodyPart attachmentPart = new MimeBodyPart();
                String attachmentFilePath = attFile;
                DataSource source = new FileDataSource(attachmentFilePath);
                attachmentPart.setDataHandler(new DataHandler(source));
                attachmentPart.setFileName(source.getName());
                multipart.addBodyPart(attachmentPart);
            }

            MimeBodyPart footerPart = new MimeBodyPart();
            footerPart.setText(footer);
            multipart.addBodyPart(footerPart);
            // Set the content of the message to the multipart object
            message.setContent(multipart);

            // Send the message using the Transport class
            Transport.send(message);
            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
