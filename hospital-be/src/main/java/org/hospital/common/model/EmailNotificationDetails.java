package org.hospital.common.model;

import lombok.Builder;
import lombok.Getter;
import org.hospital.configuration.porperties.EmailNotificationProperties;

@Getter
@Builder
public class EmailNotificationDetails {
    private String to;
    private String content;
    private EmailNotificationProperties.EmailPropertiesByNotificationType appMailPropertiesByNotificationType;
}
