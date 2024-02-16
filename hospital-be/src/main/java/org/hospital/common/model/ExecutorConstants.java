package org.hospital.common.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExecutorConstants {
    public static final String NOTIFICATION_BEAN_NAME = "notificationAsyncThreadPoolExecutor";
}
