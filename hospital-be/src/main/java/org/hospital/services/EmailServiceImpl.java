package org.hospital.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class EmailServiceImpl implements EmailService{
    private static final String NOREPLY_ADDRESS = "noreply@hospital.com";

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(String to, String subject, String text) {
        final MimeMessage message = emailSender.createMimeMessage();
        final MimeMessageHelper mimeMessageHelper;
        try {
            mimeMessageHelper = new MimeMessageHelper(message, true, "UTF-8");
            mimeMessageHelper.setFrom(NOREPLY_ADDRESS);
            mimeMessageHelper.setTo(to);
            message.setSubject(subject);
            mimeMessageHelper.setText(text);
            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
}
