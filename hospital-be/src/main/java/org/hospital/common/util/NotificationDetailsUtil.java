package org.hospital.common.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hospital.common.model.NotificationDetails;
import org.hospital.configuration.exception.model.HospitalException;
import org.hospital.configuration.porperties.EmailNotificationProperties;

import java.util.Optional;

@Slf4j
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class NotificationDetailsUtil {
    public static String getAdditionalProperty(
            final EmailNotificationProperties emailNotificationProperties,
            final NotificationDetails.NotificationType notificationType,
            final String property) {

        return Optional.ofNullable(emailNotificationProperties.get(notificationType))
                .map(EmailNotificationProperties.EmailPropertiesByNotificationType::getAdditionalDetails)
                .map(value -> value.get(property))
                .orElseThrow(() -> {
                    log.error("Additional property: {} cannot be found for notificationType: {}", property, notificationType);
                    return new HospitalException();
                });
    }

    public static String getProperty(final NotificationDetails notificationDetails,
                                     final String key) {
        return notificationDetails.getItems()
                .stream()
                .filter(value -> value.getKey().equals(key))
                .map(NotificationDetails.KeyValueItem::getValue)
                .findFirst()
                .orElseThrow(() -> {
                    log.error("Property not found: ", key);
                    return new HospitalException();
                });
    }
}
