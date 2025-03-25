package edu.austral.dissis.notifications;

public class SmsNotificationChannel implements InotificationChannel {
  private final InotificationConverter converter;

  public SmsNotificationChannel(InotificationConverter converter) {
    this.converter = converter;
  }

  @Override
  public void send(Notification notification) {
    Object convertedNotification = converter.convert(notification);
    System.out.println("Sending SMS: " + convertedNotification);
  }

  @Override
  public boolean supports(NotificationType type) {
    return true;
  }
}
