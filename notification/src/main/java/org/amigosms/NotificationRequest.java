package org.amigosms;

public record NotificationRequest(
        Integer toCustomerId,
        String toCustomerName,
        String message) {
}
