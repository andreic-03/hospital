package org.hospital.configuration.porperties;

import lombok.Getter;
import lombok.Setter;
import org.hospital.common.model.NotificationDetails;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
@ConfigurationProperties(prefix = "app.mail")
public class EmailNotificationProperties extends HashMap<NotificationDetails.NotificationType, EmailNotificationProperties.EmailPropertiesByNotificationType> {

    @Getter
    @Setter
    public static class EmailPropertiesByNotificationType {
        private String templateName;
        private String subject;
        private AdditionalDetails additionalDetails;

        public static class AdditionalDetails extends HashMap<String, String> {
        }
    }
}
