package com.example.sport_notification_service.utils;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailUtils {
    private final JavaMailSender emailSender;

    public EmailUtils(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void posaljiMejl(String kome, String naslov, String tekst) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("natasajockovic@gmail.com");
        message.setTo(kome);
        message.setSubject(naslov);
        message.setText(tekst);

        emailSender.send(message);
        System.out.println("Email uspesno poslat na: " + kome);
    }
}
