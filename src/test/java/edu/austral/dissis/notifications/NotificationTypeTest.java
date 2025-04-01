package edu.austral.dissis.notifications;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NotificationTypeTest {
  private NotificationService notificationService;
  private SimpleCollector<String> collector;
  private User user;

  @BeforeEach
  public void setUp() {
    InotificationConverter emailConverter = new EmailNotificationConverter();
    InotificationConverter smsConverter = new SmsNotificationConverter();
    InotificationConverter pushConverter = new PushNotificationConverter();

    EmailNotificationChannel emailChannel = new EmailNotificationChannel(emailConverter);
    SmsNotificationChannel smsChannel = new SmsNotificationChannel(smsConverter);
    PushNotificationChannel pushChannel = new PushNotificationChannel(pushConverter);

    Set<InotificationChannel> channels = new HashSet<>();
    channels.add(emailChannel);
    channels.add(smsChannel);
    channels.add(pushChannel);
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

    Set<InotificationChannel> userChannels = new HashSet<>();
    userChannels.add(emailChannel);

    Set<NotificationType> allowedTypes = new HashSet<>();
    allowedTypes.add(NotificationType.ALERT);
    allowedTypes.add(NotificationType.REMINDER);
    allowedTypes.add(NotificationType.PROMOTION);

    UserPreference preferences =
        new UserPreference(userChannels, allowedTypes, NotificationPriority.LOW);
    user = new User("1", "Test User", preferences);
  }

  @Test
  public void reminderNotificationIsSentCorrectly() {
    collector.getCollectedElements().clear();

    ReminderNotification reminder =
        new ReminderNotification(
            "Don't forget your meeting",
            NotificationPriority.MEDIUM,
            "Team Meeting",
            "2025-04-02T14:00:00");

    // Send notification
    notificationService.sendNotification(user, reminder);

    List<String> messages = collector.getCollectedElements();
    Assertions.assertEquals(1, messages.size());
    Assertions.assertTrue(messages.getFirst().contains("Sending email"));
    Assertions.assertTrue(messages.getFirst().contains("Reminder"));
    Assertions.assertTrue(messages.getFirst().contains("Don't forget your meeting"));
  }

  @Test
  public void promotionNotificationIsSentCorrectly() {
    collector.getCollectedElements().clear();

    PromotionNotification promotion =
        new PromotionNotification(
            "50% off on all items", NotificationPriority.LOW, "Spring Sale", "2025-04-15");

    notificationService.sendNotification(user, promotion);

    List<String> messages = collector.getCollectedElements();
    Assertions.assertEquals(1, messages.size());
    Assertions.assertTrue(messages.getFirst().contains("Sending email"));
    Assertions.assertTrue(messages.getFirst().contains("Promotion"));
    Assertions.assertTrue(messages.getFirst().contains("50% off on all items"));
  }

  @Test
  public void urgentPriorityNotificationIsSentCorrectly() {
    collector.getCollectedElements().clear();

    AlertNotification urgentAlert =
        new AlertNotification(
            "System is down!", NotificationPriority.URGENT, "Critical Alert", "System Monitor");

    notificationService.sendNotification(user, urgentAlert);

    List<String> messages = collector.getCollectedElements();
    Assertions.assertEquals(1, messages.size());
    Assertions.assertTrue(messages.getFirst().contains("Sending email"));
    Assertions.assertTrue(messages.getFirst().contains("Alert"));
    Assertions.assertTrue(messages.getFirst().contains("System is down!"));
    Assertions.assertTrue(messages.getFirst().contains("URGENT"));
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

  private static class PromotionNotification extends Notification {
    private final String title;
    private final String expirationDate;

    public PromotionNotification(
        String content, NotificationPriority priority, String title, String expirationDate) {
      super(content, NotificationType.PROMOTION, priority);
      this.title = title;
      this.expirationDate = expirationDate;
    }

    public String getTitle() {
      return title;
    }

    public String getExpirationDate() {
      return expirationDate;
    }
  }
}
