package org.hospital.services.notification.template;


import org.hospital.common.model.NotificationDetails;

public interface TemplateResolverService {
    String resolveContent(final String templateName, final NotificationDetails notificationDetails);
}
