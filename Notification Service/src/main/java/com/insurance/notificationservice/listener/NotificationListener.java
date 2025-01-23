package com.insurance.notificationservice.listener;

import com.insurance.notificationservice.model.Notification;
import com.insurance.notificationservice.model.NotificationType;
import com.insurance.notificationservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationListener {

    @Autowired
    private NotificationService notificationService;

    @KafkaListener(topics = "customer-service", groupId = "notification-service-group")
    public void listenToCustomerCreated(String message) {
        Notification notification = new Notification();
        notification.setMessage("New customer created: " + message);
        notification.setRecipient("admin@insurance.com");
        notification.setType(NotificationType.EMAIL);
        notificationService.sendNotification(notification);

    }

    @KafkaListener(topics = "policy-service", groupId = "notification-service-group")
    public void listenToPolicyCreated(String message) {
        Notification notification = new Notification();
        notification.setMessage("New policy created: " + message);
        notification.setRecipient("admin@insurance.com");
        notification.setType(NotificationType.EMAIL);
        notificationService.sendNotification(notification);

    }

    @KafkaListener(topics = "claim-service", groupId = "notification-service-group")
    public void listenToClaimCreated(String message) {
        Notification notification = new Notification();
        notification.setMessage("New claim created: " + message);
        notification.setRecipient("admin@insurance.com");
        notification.setType(NotificationType.EMAIL);
        notificationService.sendNotification(notification);

    }
}
