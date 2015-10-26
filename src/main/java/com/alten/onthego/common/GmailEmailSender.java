package com.alten.onthego.common;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class GmailEmailSender {

    public static void sendEmail(String host, String port,
            final String userName, final String password, String toAddress,
            String subject, String message, String[] attachments)
            throws AddressException, MessagingException {
        // sets SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.user", userName);
        properties.put("mail.password", password);
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.debug", "true");

        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };
        Session session = Session.getInstance(properties, auth);

        // creates a new e-mail message
        Message sessionmessage = new MimeMessage(session);

        sessionmessage.setFrom(new InternetAddress(userName));
        InternetAddress[] toAddresses = {new InternetAddress(toAddress)};
        sessionmessage.setRecipients(Message.RecipientType.TO, toAddresses);
        sessionmessage.setSubject(subject);
        sessionmessage.setSentDate(new Date());

        // creates message part
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(message, "text/html");

        // creates multi-part
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        // adds attachments
        if (attachments != null && attachments.length > 0) {
            for (String filePath : attachments) {
                MimeBodyPart attachPart = new MimeBodyPart();

                try {
                    attachPart.attachFile(filePath);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                multipart.addBodyPart(attachPart);
            }
        }

        // sets the multi-part as e-mail's content
        sessionmessage.setContent(multipart);

        // sends the e-mail
        Transport.send(sessionmessage);
    }

    public static void main(String[] args) throws MessagingException {
        sendEmail("smtp.alten.se", "25", "noreply-destinationlindholmen", "Lindholmen2015", "khaled.nawasreh@gmail.com", "subject", "here is the messages", null);
        System.out.println("Email sent done!");
    }
}
