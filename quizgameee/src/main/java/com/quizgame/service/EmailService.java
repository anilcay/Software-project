package com.quizgame.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationCode(String to, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("millionare.noreply@gmail.com");
        message.setTo(to);
        message.setSubject("QuizGame - Doğrulama Kodu");
        message.setText("Doğrulama kodunuz: " + code);

        try {
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Kod gönderilemedi: " + e.getMessage());
        }
    }
}
