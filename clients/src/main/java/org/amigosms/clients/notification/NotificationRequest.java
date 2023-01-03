package org.amigosms.clients.notification;

import java.time.LocalDateTime;

public record NotificationRequest(
        Integer toCustomerId,
        String toCustomerName,
        String createDate,
        String message) {
}
