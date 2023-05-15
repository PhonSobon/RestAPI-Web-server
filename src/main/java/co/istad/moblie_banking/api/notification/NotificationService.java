package co.istad.moblie_banking.api.notification;

import co.istad.moblie_banking.api.notification.web.CreateNotificationDto;

public interface NotificationService {
    boolean pushNotification(CreateNotificationDto notificationDto);
}
