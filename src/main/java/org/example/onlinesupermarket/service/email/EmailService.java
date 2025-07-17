package org.example.onlinesupermarket.service.email;

import org.example.onlinesupermarket.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendOTP(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("olearningwebsite@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }

    public void sendPasswordChangedEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("olearningwebsite@gmail.com");
        message.setTo(user.getEmail());
        message.setSubject("Your password has been changed");
        message.setText("Hello " + user.getFullName() + ",\n\nYour password has been changed successfully. If you did not perform this action, please contact support immediately.\n\nBest regards,\nOnlineSupermarket Team");
        mailSender.send(message);
    }
} 