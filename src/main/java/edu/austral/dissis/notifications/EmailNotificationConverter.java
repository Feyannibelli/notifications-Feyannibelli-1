package edu.austral.dissis.notifications;

public class EmailNotificationConverter implements InotificationConverter {
  @Override
  public Object convert(Notification notification) {
    return switch (notification.getType()) {
      case ALERT -> convertAlertToEmail(notification);
      case REMINDER -> convertReminderToEmail(notification);
      case PROMOTION -> convertPromotionToEmail(notification);
    };
  }

  private String convertAlertToEmail(Notification notification) {
    return String.format(
        "<html><body><h1>Alert</h1><p>%s</p><p>Priority: %s</p></body></html>",
        notification.getContent(), notification.getPriority());
  }

  private String convertReminderToEmail(Notification notification) {
    return String.format(
        "<html><body><h1>Reminder</h1><p>%s</p><p>Priority: %s</p></body></html>",
        notification.getContent(), notification.getPriority());
  }

  private String convertPromotionToEmail(Notification notification) {
    return String.format(
        "<html><body><h1>Promotion</h1><p>%s</p><p>Priority: %s</p></body></html>",
        notification.getContent(), notification.getPriority());
  }
}
