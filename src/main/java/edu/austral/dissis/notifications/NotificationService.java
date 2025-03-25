package edu.austral.dissis.notifications;

import java.util.HashSet;
import java.util.Set;

public class NotificationService {
  private final Set<InotificationChannel> availableChannels;

  public NotificationService(Set<InotificationChannel> channels) {
    this.availableChannels = new HashSet<>(channels);
  }

  public void sendNotification(User user, Notification notification) {
    if (!user.getPreferences().canReceive(notification)) {
      return;
    }
    for (InotificationChannel channel : user.getPreferences().getChannels()) {
      if (availableChannels.contains(channel) && channel.supports(notification.getType())) {
        channel.send(notification);
      }
    }
  }

  public void addChannel(InotificationChannel channel) {
    availableChannels.add(channel);
  }

  public void removeChannel(InotificationChannel channel) {
    availableChannels.remove(channel);
  }
}
