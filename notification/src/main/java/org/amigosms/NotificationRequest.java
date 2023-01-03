package org.amigosms;

import java.time.LocalDateTime;

public record NotificationRequest(
        Integer toCustomerId,
        String toCustomerName,
        String createDate,
        String message) {
}
