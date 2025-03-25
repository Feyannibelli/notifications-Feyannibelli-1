package edu.austral.dissis.notifications;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Notification {
  private final String id;
  private final String content;
  private final NotificationType type;
  private final NotificationPriority priority;
  private final LocalDateTime createdAt;

  protected Notification(String content, NotificationType type, NotificationPriority priority) {
    this.id = UUID.randomUUID().toString();
    this.content = content;
    this.type = type;
    this.priority = priority;
    this.createdAt = LocalDateTime.now();
  }

  public String getId() {
    return id;
  }

  public String getContent() {
    return content;
  }

  public NotificationType getType() {
    return type;
  }

  public NotificationPriority getPriority() {
    return priority;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }
}
