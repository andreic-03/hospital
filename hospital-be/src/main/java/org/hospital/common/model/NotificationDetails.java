package org.hospital.common.model;

import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
public class NotificationDetails {
    public static final String TO_KEY = "to";
    public static final String FULL_NAME_KEY = "fullName";
    public static final String REGISTER_LINK_KEY = "registerLink";
    public static final String USERNAME_KEY = "username";
    public static final String TOKEN_PLACEHOLDER_KEY = "{token}";

    private final NotificationType notificationType;
    private Set<KeyValueItem> items;

    public NotificationDetails addKeyValueItem(final String key, final String value) {
        if (Objects.isNull(items)) {
            this.items = new HashSet<>();
        }

        this.items.add(new KeyValueItem(key, value));

        return this;
    }

    @Getter
    @EqualsAndHashCode
    @AllArgsConstructor
    public class KeyValueItem {
        private final String key;
        private final String value;
    }

    public enum NotificationType {
        REGISTER_ACCOUNT
    }
}
