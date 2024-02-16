package org.hospital.services.notification.dispatcher;

import org.hospital.common.model.NotificationDetails;

public interface NotificationDispatcherService {

    void dispatchNotification(final NotificationDetails notificationDetails);
}
