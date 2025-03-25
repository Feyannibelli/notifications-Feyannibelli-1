package edu.austral.dissis.notifications;

import java.util.Map;

public class PushNotificationConverter implements InotificationConverter {
  @Override
  public Object convert(Notification notification) {
    return switch (notification.getType()) {
      case ALERT -> convertAlertToPush(notification);
      case REMINDER -> convertReminderToPush(notification);
      case PROMOTION -> convertPromotionToPush(notification);
    };
  }

  private Map<String, Object> convertAlertToPush(Notification notification) {
    return Map.of(
        "type", "ALERT",
        "content", notification.getContent(),
        "priority", notification.getPriority());
  }

  private Map<String, Object> convertReminderToPush(Notification notification) {
    return Map.of(
        "type", "REMINDER",
        "content", notification.getContent(),
        "priority", notification.getPriority());
  }

  private Map<String, Object> convertPromotionToPush(Notification notification) {
    return Map.of(
        "type", "PROMOTION",
        "content", notification.getContent(),
        "priority", notification.getPriority());
  }
}
