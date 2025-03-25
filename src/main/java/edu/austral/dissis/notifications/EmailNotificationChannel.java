package edu.austral.dissis.notifications;

public class EmailNotificationChannel implements InotificationChannel {
  private final InotificationConverter converter;

  public EmailNotificationChannel(InotificationConverter converter) {
    this.converter = converter;
  }

  @Override
  public void send(Notification notification) {
    Object convertedNotification = converter.convert(notification);
    System.out.println("Sending email: " + convertedNotification);
  }

  @Override
  public boolean supports(NotificationType type) {
    return true;
  }
}
