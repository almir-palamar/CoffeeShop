package com.example.coffeeshop.services;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {

    private JavaMailSender mailSender;

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
