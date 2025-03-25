package edu.austral.dissis.notifications;

public class SmsNotificationConverter implements InotificationConverter {
  @Override
  public Object convert(Notification notification) {
    return switch (notification.getType()) {
      case ALERT -> convertAlertToSMS(notification);
      case REMINDER -> convertReminderToSMS(notification);
      case PROMOTION -> convertPromotionToSMS(notification);
    };
  }

  private String convertAlertToSMS(Notification notification) {
    return String.format(
        "ALERT: %s (Priority: %s)", notification.getContent(), notification.getPriority());
  }

  private String convertReminderToSMS(Notification notification) {
    return String.format(
        "REMINDER: %s (Priority: %s)", notification.getContent(), notification.getPriority());
  }

  private String convertPromotionToSMS(Notification notification) {
    return String.format(
        "PROMO: %s (Priority: %s)", notification.getContent(), notification.getPriority());
  }
}
