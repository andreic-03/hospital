package org.hospital.services.notification.email;

import org.hospital.common.model.EmailNotificationDetails;

public interface EmailService {
    void sendEmail(EmailNotificationDetails emailNotificationDetails);
}
