package edu.austral.dissis.notifications;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserPreferenceTest {
  private NotificationService notificationService;
  private EmailNotificationChannel emailChannel;
  private SmsNotificationChannel smsChannel;
  private SimpleCollector<String> collector;

  @BeforeEach
  public void setUp() {
    InotificationConverter emailConverter = new EmailNotificationConverter();
    InotificationConverter smsConverter = new SmsNotificationConverter();

    emailChannel = new EmailNotificationChannel(emailConverter);
    smsChannel = new SmsNotificationChannel(smsConverter);

    Set<InotificationChannel> channels = new HashSet<>();
    channels.add(emailChannel);
    channels.add(smsChannel);
    notificationService = new NotificationService(channels);

    collector = new SimpleCollector<>();
    System.setOut(
        new java.io.PrintStream(System.out) {
          @Override
          public void println(String x) {
            collector.addElement(x);
            super.println(x);
          }
        });
  }

  @Test
  public void userPreferenceTypeFiltersNotifications() {
    Set<InotificationChannel> userChannels = new HashSet<>();
    userChannels.add(emailChannel);

    Set<NotificationType> allowedTypes = new HashSet<>();
    allowedTypes.add(NotificationType.ALERT);

    UserPreference preferences =
        new UserPreference(userChannels, allowedTypes, NotificationPriority.LOW);
    User user = new User("1", "Test User", preferences);

    AlertNotification alert =
        new AlertNotification(
            "Test Alert", NotificationPriority.MEDIUM, "Alert Title", "Test Source");

    // Movido más cerca del primer uso
    ReminderNotification reminder =
        new ReminderNotification(
            "Test Reminder", NotificationPriority.MEDIUM, "Reminder Title", "2025-04-02");

    collector.getCollectedElements().clear();

    notificationService.sendNotification(user, alert);
    notificationService.sendNotification(user, reminder);

    List<String> messages = collector.getCollectedElements();
    Assertions.assertEquals(1, messages.size());
    Assertions.assertTrue(messages.getFirst().contains("Alert"));
    Assertions.assertFalse(messages.getFirst().contains("Reminder"));
  }

  @Test
  public void userPreferencePriorityFiltersNotifications() {
    Set<InotificationChannel> userChannels = new HashSet<>();
    userChannels.add(emailChannel);

    Set<NotificationType> allowedTypes = new HashSet<>();
    allowedTypes.add(NotificationType.ALERT);

    UserPreference preferences =
        new UserPreference(userChannels, allowedTypes, NotificationPriority.HIGH);
    User user = new User("1", "Test User", preferences);

    AlertNotification lowPriority =
        new AlertNotification(
            "Low Priority Alert", NotificationPriority.LOW, "Low Alert", "Test Source");

    AlertNotification mediumPriority =
        new AlertNotification(
            "Medium Priority Alert", NotificationPriority.MEDIUM, "Medium Alert", "Test Source");

    // Movidas las variables highPriority y urgentPriority más cerca de su uso
    collector.getCollectedElements().clear();

    notificationService.sendNotification(user, lowPriority);
    notificationService.sendNotification(user, mediumPriority);

    final AlertNotification highPriority =
        new AlertNotification(
            "High Priority Alert", NotificationPriority.HIGH, "High Alert", "Test Source");
    notificationService.sendNotification(user, highPriority);

    final AlertNotification urgentPriority =
        new AlertNotification(
            "Urgent Priority Alert", NotificationPriority.URGENT, "Urgent Alert", "Test Source");
    notificationService.sendNotification(user, urgentPriority);

    List<String> messages = collector.getCollectedElements();
    Assertions.assertEquals(2, messages.size());

    boolean foundHigh = false;
    boolean foundUrgent = false;

    for (String message : messages) {
      if (message.contains("High Priority Alert")) {
        foundHigh = true;
      }
      if (message.contains("Urgent Priority Alert")) {
        foundUrgent = true;
      }
      Assertions.assertFalse(message.contains("Low Priority Alert"));
      Assertions.assertFalse(message.contains("Medium Priority Alert"));
    }

    Assertions.assertTrue(foundHigh);
    Assertions.assertTrue(foundUrgent);
  }

  @Test
  public void userWithMultipleChannelsReceivesOnAll() {
    Set<InotificationChannel> userChannels = new HashSet<>();
    userChannels.add(emailChannel);
    userChannels.add(smsChannel);

    Set<NotificationType> allowedTypes = new HashSet<>();
    allowedTypes.add(NotificationType.ALERT);

    UserPreference preferences =
        new UserPreference(userChannels, allowedTypes, NotificationPriority.LOW);
    User user = new User("1", "Test User", preferences);

    AlertNotification alert =
        new AlertNotification(
            "Test Alert", NotificationPriority.MEDIUM, "Test Title", "Test Source");

    collector.getCollectedElements().clear();

    notificationService.sendNotification(user, alert);

    List<String> messages = collector.getCollectedElements();
    Assertions.assertEquals(2, messages.size());

    boolean foundEmail = false;
    boolean foundSms = false;

    for (String message : messages) {
      if (message.contains("Sending email")) {
        foundEmail = true;
      }
      if (message.contains("Sending SMS")) {
        foundSms = true;
      }
    }

    Assertions.assertTrue(foundEmail);
    Assertions.assertTrue(foundSms);
  }

  private static class ReminderNotification extends Notification {
    private final String title;
    private final String dueDate;

    public ReminderNotification(
        String content, NotificationPriority priority, String title, String dueDate) {
      super(content, NotificationType.REMINDER, priority);
      this.title = title;
      this.dueDate = dueDate;
    }

    public String getTitle() {
      return title;
    }

    public String getDueDate() {
      return dueDate;
    }
  }
}
