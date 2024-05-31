package org.hospital.services.notification.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hospital.common.model.EmailNotificationDetails;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class EmailServiceImpl implements EmailService{
    private static final String SUBJECT_PREFIX = "[Hospital]";

    private JavaMailSender emailSender;

    @Override
    public void sendEmail(EmailNotificationDetails emailNotificationDetails) {
        final MimeMessage message = emailSender.createMimeMessage();
        final MimeMessageHelper mimeMessageHelper;
        try {
            mimeMessageHelper = new MimeMessageHelper(message, true, "UTF-8");
            mimeMessageHelper.setFrom("CHANGE_ME");
            mimeMessageHelper.setTo(emailNotificationDetails.getTo());
            message.setSubject(String.join(" ", SUBJECT_PREFIX, emailNotificationDetails.getAppMailPropertiesByNotificationType().getSubject()));
            mimeMessageHelper.setText(emailNotificationDetails.getContent(), true);
            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
}
