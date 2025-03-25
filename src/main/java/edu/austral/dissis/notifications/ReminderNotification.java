package edu.austral.dissis.notifications;

import java.time.LocalDateTime;

public class ReminderNotification extends Notification {
  private final LocalDateTime reminderDateTime;
  private final String eventLocation;

  public ReminderNotification(
      String content,
      NotificationPriority priority,
      LocalDateTime reminderDateTime,
      String eventLocation) {
    super(content, NotificationType.REMINDER, priority);
    this.reminderDateTime = reminderDateTime;
    this.eventLocation = eventLocation;
  }

  public LocalDateTime getReminderDateTime() {
    return reminderDateTime;
  }

  public String getEventLocation() {
    return eventLocation;
  }
}
