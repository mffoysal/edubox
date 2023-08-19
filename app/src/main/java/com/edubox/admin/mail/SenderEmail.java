package com.edubox.admin.mail;

import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SenderEmail {
    private static final String SMTP_HOST = "your-smtp-host";
    private static final int SMTP_PORT = 587; // Replace with the appropriate SMTP port
    private static final String USERNAME = "your-email-username";
    private static final String PASSWORD = "your-email-password";

    public static void sendEmailWithHTMLAndAttachment(String recipientEmail, String subject, String htmlContent, String attachmentFilePath) {
        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);

            // Create the HTML body part
            MimeBodyPart htmlBodyPart = new MimeBodyPart();
            htmlBodyPart.setContent(htmlContent, "text/html");

            // Create the attachment body part
            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(attachmentFilePath);
            attachmentBodyPart.setDataHandler(new DataHandler(source));
            attachmentBodyPart.setFileName(source.getName());

            // Create a multipart message to hold the body parts
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(htmlBodyPart);
            multipart.addBodyPart(attachmentBodyPart);

            // Set the multipart message as the content of the email
            message.setContent(multipart);

            Transport.send(message);
            System.out.println("Email with HTML and attachment sent successfully.");
        } catch (MessagingException e) {
            System.out.println("Failed to send email with HTML and attachment. Error: " + e.getMessage());
        }
    }
    private void sendEmailWithHTMLAndAttachment() {
        String recipientEmail = "recipient@example.com"; // Replace with the recipient email address
        String subject = "Email with HTML and Attachment";

        // HTML content
        String htmlContent = "<html><body><h1>Hello, this is an HTML email.</h1></body></html>";

        String attachmentFilePath = "path/to/attachment/file.pdf"; // Replace with the actual file path

        SenderEmail.sendEmailWithHTMLAndAttachment(recipientEmail, subject, htmlContent, attachmentFilePath);
    }
}
