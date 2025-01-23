package com.insurance.notificationservice.model;

import lombok.Data;

@Data
public class Notification {

 private String message;
 private String recipient;
 private NotificationType type;

}
