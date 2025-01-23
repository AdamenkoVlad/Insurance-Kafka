package com.insurance.notificationservice.service;

import com.insurance.notificationservice.model.Notification;
import com.insurance.notificationservice.model.NotificationType;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;

    @Value("${twilio.phone-number}")
    private String twilioPhoneNumber;

    @PostConstruct
    public void init() {
        Twilio.init(accountSid, authToken);
    }


    @Autowired
    private JavaMailSender mailSender;

    public void sendNotification(Notification notification) {

        if (notification.getType() == NotificationType.EMAIL) {
            sendEmail(notification);
        } else if (notification.getType() == NotificationType.SMS) {
            sendSms(notification);
        }

    }

    private void sendSms(Notification notification) {
        Message message = Message.creator(
                new PhoneNumber(notification.getRecipient()),
                new PhoneNumber(twilioPhoneNumber),
                notification.getMessage()
        ).create();
        mailSender.send(message);
        System.out.println("SMS sent to " + notification.getRecipient() + ": " + notification.getMessage());
    }

    private void sendEmail(Notification notification) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(notification.getRecipient());
        message.setSubject("Insurance Notification Service");
        message.setText(notification.getMessage());
        mailSender.send(message);
        System.out.println("Email sent to " + notification.getRecipient() + ": " + notification.getMessage());

    }

}
