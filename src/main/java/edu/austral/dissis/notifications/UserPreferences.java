package edu.austral.dissis.notifications;

public class UserPreferences {
    private final Set<NotificationChannel> preferredChannels = new HashSet<>();
    private final Set<Class<? extends Notification>> preferredNotifications = new HashSet<>();

    public void addChannel(NotificationChannel channel) {
        preferredChannels.add(channel);
    }

    public void addNotificationType(Class<? extends Notification> notificationType) {
        preferredNotifications.add(notificationType);
    }

    public boolean allowsNotification(Notification notification) {
        return preferredNotifications.contains(notification.getClass());
    }

    public Set<NotificationChannel> getPreferredChannels() {
        return preferredChannels;
    }
}
