package edu.austral.dissis.notifications;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ChannelManagementTest {
  private NotificationService notificationService;
  private EmailNotificationChannel emailChannel;
  private SmsNotificationChannel smsChannel;
  private PushNotificationChannel pushChannel;
  private SimpleCollector<String> collector;
  private User user;

  @BeforeEach
  public void setUp() {
    InotificationConverter emailConverter = new EmailNotificationConverter();
    InotificationConverter smsConverter = new SmsNotificationConverter();
    InotificationConverter pushConverter = new PushNotificationConverter();

    emailChannel = new EmailNotificationChannel(emailConverter);
    smsChannel = new SmsNotificationChannel(smsConverter);
    pushChannel = new PushNotificationChannel(pushConverter);

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
  public void addingChannelMakesItAvailable() {
    notificationService = new NotificationService(new HashSet<>());

    Set<InotificationChannel> userChannels = new HashSet<>();
    userChannels.add(emailChannel);

    Set<NotificationType> allowedTypes = new HashSet<>();
    allowedTypes.add(NotificationType.ALERT);

    UserPreference preferences =
        new UserPreference(userChannels, allowedTypes, NotificationPriority.LOW);
    user = new User("1", "Test User", preferences);

    AlertNotification alert =
        new AlertNotification(
            "Test Alert", NotificationPriority.MEDIUM, "Test Title", "Test Source");

    collector.getCollectedElements().clear();

    notificationService.sendNotification(user, alert);

    Assertions.assertEquals(0, collector.getCollectedElements().size());

    notificationService.addChannel(emailChannel);

    notificationService.sendNotification(user, alert);

    List<String> messages = collector.getCollectedElements();
    Assertions.assertEquals(1, messages.size());
    Assertions.assertTrue(messages.getFirst().contains("Sending email"));
  }

  @Test
  public void removingChannelMakesItUnavailable() {
    Set<InotificationChannel> serviceChannels = new HashSet<>();
    serviceChannels.add(emailChannel);
    serviceChannels.add(smsChannel);
    notificationService = new NotificationService(serviceChannels);

    Set<InotificationChannel> userChannels = new HashSet<>();
    userChannels.add(emailChannel);
    userChannels.add(smsChannel);

    Set<NotificationType> allowedTypes = new HashSet<>();
    allowedTypes.add(NotificationType.ALERT);

    UserPreference preferences =
        new UserPreference(userChannels, allowedTypes, NotificationPriority.LOW);
    user = new User("1", "Test User", preferences);

    AlertNotification alert =
        new AlertNotification(
            "Test Alert", NotificationPriority.MEDIUM, "Test Title", "Test Source");

    collector.getCollectedElements().clear();

    notificationService.sendNotification(user, alert);

    List<String> messages = collector.getCollectedElements();
    Assertions.assertEquals(2, messages.size());

    collector.getCollectedElements().clear();

    notificationService.removeChannel(emailChannel);

    notificationService.sendNotification(user, alert);

    messages = collector.getCollectedElements();
    Assertions.assertEquals(1, messages.size());
    Assertions.assertTrue(messages.getFirst().contains("Sending SMS"));
  }

  @Test
  public void channelSupportsMethodFiltersNotifications() {
    InotificationConverter emailConverter = new EmailNotificationConverter();
    InotificationChannel selectiveChannel = new SelectiveSupportChannel(emailConverter);

    Set<InotificationChannel> serviceChannels = new HashSet<>();
    serviceChannels.add(selectiveChannel);
    notificationService = new NotificationService(serviceChannels);

    Set<InotificationChannel> userChannels = new HashSet<>();
    userChannels.add(selectiveChannel);

    Set<NotificationType> allowedTypes = new HashSet<>();
    allowedTypes.add(NotificationType.ALERT);
    allowedTypes.add(NotificationType.REMINDER);

    UserPreference preferences =
        new UserPreference(userChannels, allowedTypes, NotificationPriority.LOW);
    user = new User("1", "Test User", preferences);

    AlertNotification alert =
        new AlertNotification("Test Alert", NotificationPriority.LOW, "Test Title", "Test Source");

    collector.getCollectedElements().clear();

    notificationService.sendNotification(user, alert);

    List<String> messages = collector.getCollectedElements();
    Assertions.assertEquals(1, messages.size());

    collector.getCollectedElements().clear();

    // Move the declaration right here, immediately before it's used
    NotificationTypeTest.ReminderNotification reminder =
        new NotificationTypeTest.ReminderNotification(
            "Test Reminder", NotificationPriority.LOW, "Reminder Title", "2025-04-02");
    notificationService.sendNotification(user, reminder);

    messages = collector.getCollectedElements();
    Assertions.assertEquals(0, messages.size());
  }

  private static class SelectiveSupportChannel implements InotificationChannel {
    private final InotificationConverter converter;

    public SelectiveSupportChannel(InotificationConverter converter) {
      this.converter = converter;
    }

    @Override
    public void send(Notification notification) {
      Object convertedNotification = converter.convert(notification);
      System.out.println("Sending selective channel: " + convertedNotification);
    }

    @Override
    public boolean supports(NotificationType type) {
      return type == NotificationType.ALERT;
    }
  }

  private static class NotificationTypeTest {
    public static class ReminderNotification extends Notification {
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
}
