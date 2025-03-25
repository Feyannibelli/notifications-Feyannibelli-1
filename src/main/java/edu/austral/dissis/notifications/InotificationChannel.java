package edu.austral.dissis.notifications;

public interface InotificationChannel {
  void send(Notification notification);

  boolean supports(NotificationType type);
}
