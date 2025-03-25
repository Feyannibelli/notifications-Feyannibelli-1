package edu.austral.dissis.notifications;

public class AlertNotification extends Notification {
  private final String title;
  private final String source;

  public AlertNotification(
      String content, NotificationPriority priority, String title, String source) {
    super(content, NotificationType.ALERT, priority);
    this.title = title;
    this.source = source;
  }

  public String getTitle() {
    return title;
  }

  public String getSource() {
    return source;
  }
}
