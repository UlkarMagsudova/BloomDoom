package com.ltclab.bloomdoomseller.service.email;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailService {
    private final JavaMailSender javaMailSender;

    public void sendPasswordResetEmail(String email, String token) {

        String resetLink = "http://localhost:8080/api/password/reset?token=" + token;
//        http://localhost:8080/api/password/reset?token=abc123

        // Create the email message
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setFrom("ummagsudova@gmail.com");
        message.setSubject("Password Reset Request");
        message.setText("To reset your password, click the following link: " + resetLink);

        javaMailSender.send(message);

        System.out.println("Password reset email sent to: " + email);
    }
}
