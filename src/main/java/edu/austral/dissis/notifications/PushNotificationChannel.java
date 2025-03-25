package edu.austral.dissis.notifications;

public class PushNotificationChannel implements InotificationChannel {
  private final InotificationConverter converter;

  public PushNotificationChannel(InotificationConverter converter) {
    this.converter = converter;
  }

  @Override
  public void send(Notification notification) {
    Object convertedNotification = converter.convert(notification);
    System.out.println("Sending Push Notification: " + convertedNotification);
  }

  @Override
  public boolean supports(NotificationType type) {
    return true;
  }
}
