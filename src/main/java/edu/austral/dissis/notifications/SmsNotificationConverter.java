package edu.austral.dissis.notifications;

public class SmsNotificationConverter implements InotificationConverter {
  @Override
  public Object convert(Notification notification) {
    return switch (notification.getType()) {
      case ALERT -> convertAlertToSms(notification);
      case REMINDER -> convertReminderToSms(notification);
      case PROMOTION -> convertPromotionToSms(notification);
    };
  }

  private String convertAlertToSms(Notification notification) {
    return String.format(
        "ALERT: %s (Priority: %s)", notification.getContent(), notification.getPriority());
  }

  private String convertReminderToSms(Notification notification) {
    return String.format(
        "REMINDER: %s (Priority: %s)", notification.getContent(), notification.getPriority());
  }

  private String convertPromotionToSms(Notification notification) {
    return String.format(
        "PROMO: %s (Priority: %s)", notification.getContent(), notification.getPriority());
  }
}
