package com.example.coffeeshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void sendEmail(String to, String subject, String body) {
        try {
            Thread.sleep(5000L);
            System.out.println("Sending email");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Async
    public void sendWelcomeEmail(String to) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(to);
        email.setFrom("CoffeeShop");
        email.setSubject("Welcome to Coffeeshop");
        email.setText("Welcome to Coffeeshop!");
        this.mailSender.send(email);
    }

}
