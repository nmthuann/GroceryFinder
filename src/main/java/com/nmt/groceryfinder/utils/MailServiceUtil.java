package com.nmt.groceryfinder.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceUtil {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String userName;

    @Autowired
    public MailServiceUtil(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    public void sendHtmlEmail(String to, String subject, String htmlContent) throws MailException, MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

            message.setFrom(new InternetAddress("nmt.m10.2862001@gmail.com"));
            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setContent(htmlContent, "text/html; charset=utf-8");
            mailSender.send(message);

    }
}
