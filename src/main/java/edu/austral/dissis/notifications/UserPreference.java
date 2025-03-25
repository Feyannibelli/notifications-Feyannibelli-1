package edu.austral.dissis.notifications;

import java.util.HashSet;
import java.util.Set;

public class UserPreference {
  private final Set<InotificationChannel> channels;
  private final Set<NotificationType> allowedTypes;
  private final NotificationPriority minPriority;

  public UserPreference(
      Set<InotificationChannel> channels,
      Set<NotificationType> allowedTypes,
      NotificationPriority minPriority) {
    this.channels = new HashSet<>(channels);
    this.allowedTypes = new HashSet<>(allowedTypes);
    this.minPriority = minPriority;
  }

  public boolean canReceive(Notification notification) {
    return allowedTypes.contains(notification.getType())
        && notification.getPriority().ordinal() >= minPriority.ordinal();
  }

  public Set<InotificationChannel> getChannels() {
    return new HashSet<>(channels);
  }
}
