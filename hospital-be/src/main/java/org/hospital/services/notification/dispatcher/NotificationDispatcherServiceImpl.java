package org.hospital.services.notification.dispatcher;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hospital.common.model.EmailNotificationDetails;
import org.hospital.common.model.NotificationDetails;
import org.hospital.common.util.NotificationDetailsUtil;
import org.hospital.configuration.exception.model.HospitalException;
import org.hospital.configuration.porperties.EmailNotificationProperties;
import org.hospital.services.notification.email.EmailService;
import org.hospital.services.notification.template.TemplateResolverService;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.hospital.common.model.ExecutorConstants.NOTIFICATION_BEAN_NAME;

@Service
@Slf4j
@AllArgsConstructor
public class NotificationDispatcherServiceImpl implements NotificationDispatcherService {

    private final EmailNotificationProperties emailNotificationProperties;
    private final TemplateResolverService templateResolverService;
    private final EmailService emailSenderService;

    @Async(NOTIFICATION_BEAN_NAME)
    @Retryable(retryFor = HospitalException.class)
    @Override
    public void dispatchNotification(NotificationDetails notificationDetails) {
        final var notificationType = notificationDetails.getNotificationType();
        final var properties = Optional.ofNullable(
                emailNotificationProperties.get(notificationType)).orElseThrow(() -> {
            log.error("No configurations found for notification type: {}", notificationType);
            return new HospitalException();
        });

        final var templateName = Optional.ofNullable(
                properties.getTemplateName()
        ).orElseThrow(() -> {
            log.error("No templateName defined for notification type: {}", notificationType);
            return new HospitalException();
        });

        final String content = templateResolverService.resolveContent(templateName, notificationDetails);
        emailSenderService.sendEmail(
                EmailNotificationDetails.builder()
                        .to(NotificationDetailsUtil.getProperty(notificationDetails, NotificationDetails.TO_KEY))
                        .content(content)
                        .appMailPropertiesByNotificationType(properties)
                        .build()
        );
    }
}
