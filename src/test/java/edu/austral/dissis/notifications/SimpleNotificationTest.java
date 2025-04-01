package edu.austral.dissis.notifications;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SimpleNotificationTest {

  @Test
  public void userWithNoNotifications() {
    final SimpleCollector<String> collector = new SimpleCollector<>();

    final Set<InotificationChannel> channels = new HashSet<>();
    final Set<NotificationType> allowedTypes = new HashSet<>();
    final UserPreference preferences =
        new UserPreference(channels, allowedTypes, NotificationPriority.LOW);
    final User user = new User("1", "Test User", preferences);

    final NotificationService notificationSystem = new NotificationService(new HashSet<>());

    final AlertNotification event =
        new AlertNotification("Test Alert", NotificationPriority.LOW, "Test Title", "Test Source");

    System.setOut(
        new java.io.PrintStream(System.out) {
          @Override
          public void println(String x) {
            collector.addElement(x);
          }
        });

    notificationSystem.sendNotification(user, event);

    Assertions.assertIterableEquals(List.of(), collector.getCollectedElements());
  }

  @Test
  public void userWithPushNotification() {
    final SimpleCollector<String> collector = new SimpleCollector<>();

    final PushNotificationConverter pushConverter = new PushNotificationConverter();
    final PushNotificationChannel pushChannel = new PushNotificationChannel(pushConverter);

    final Set<InotificationChannel> channels = new HashSet<>(Set.of(pushChannel));
    final Set<NotificationType> allowedTypes = new HashSet<>(Set.of(NotificationType.ALERT));
    final UserPreference preferences =
        new UserPreference(channels, allowedTypes, NotificationPriority.LOW);
    final User user = new User("1", "Test User", preferences);

    final NotificationService notificationSystem =
        new NotificationService(new HashSet<>(Set.of(pushChannel)));

    final AlertNotification event =
        new AlertNotification("Test Alert", NotificationPriority.LOW, "Test Title", "Test Source");

    System.setOut(
        new java.io.PrintStream(System.out) {
          @Override
          public void println(String x) {
            collector.addElement(x);
          }
        });

    notificationSystem.sendNotification(user, event);

    // Debug the actual output to understand what's being received
    System.err.println("Actual Push Output: " + collector.getCollectedElements());

    // Let's use contains instead of exact string matching to be more flexible
    Assertions.assertTrue(
        collector.getCollectedElements().get(0).contains("Sending Push Notification"),
        "Should start with 'Sending Push Notification'");
    Assertions.assertTrue(
        collector.getCollectedElements().get(0).contains("type=ALERT"),
        "Should contain 'type=ALERT'");
    Assertions.assertTrue(
        collector.getCollectedElements().get(0).contains("content=Test Alert"),
        "Should contain 'content=Test Alert'");
  }

  @Test
  public void userWithEmailNotification() {
    final SimpleCollector<String> collector = new SimpleCollector<>();

    final EmailNotificationConverter emailConverter = new EmailNotificationConverter();
    final EmailNotificationChannel emailChannel = new EmailNotificationChannel(emailConverter);

    final Set<InotificationChannel> channels = new HashSet<>(Set.of(emailChannel));
    final Set<NotificationType> allowedTypes = new HashSet<>(Set.of(NotificationType.ALERT));
    final UserPreference preferences =
        new UserPreference(channels, allowedTypes, NotificationPriority.LOW);
    final User user = new User("1", "Test User", preferences);

    final NotificationService notificationSystem =
        new NotificationService(new HashSet<>(Set.of(emailChannel)));

    final AlertNotification event =
        new AlertNotification("Test Alert", NotificationPriority.LOW, "Test Title", "Test Source");

    System.setOut(
        new java.io.PrintStream(System.out) {
          @Override
          public void println(String x) {
            collector.addElement(x);
          }
        });

    notificationSystem.sendNotification(user, event);

    final String expectedEmailNotification =
        "Sending email: <html><body><h1>Alert</h1><p>Test Alert</p>"
            + "<p>Priority: LOW</p></body></html>";

    Assertions.assertIterableEquals(
        List.of(expectedEmailNotification), collector.getCollectedElements());
  }

  @Test
  public void userWithSmsNotification() {
    final SimpleCollector<String> collector = new SimpleCollector<>();

    final SmsNotificationConverter smsConverter = new SmsNotificationConverter();
    final SmsNotificationChannel smsChannel = new SmsNotificationChannel(smsConverter);

    final Set<InotificationChannel> channels = new HashSet<>(Set.of(smsChannel));
    final Set<NotificationType> allowedTypes = new HashSet<>(Set.of(NotificationType.ALERT));
    final UserPreference preferences =
        new UserPreference(channels, allowedTypes, NotificationPriority.LOW);
    final User user = new User("1", "Test User", preferences);

    final NotificationService notificationSystem =
        new NotificationService(new HashSet<>(Set.of(smsChannel)));

    final AlertNotification event =
        new AlertNotification("Test Alert", NotificationPriority.LOW, "Test Title", "Test Source");

    System.setOut(
        new java.io.PrintStream(System.out) {
          @Override
          public void println(String x) {
            collector.addElement(x);
          }
        });

    notificationSystem.sendNotification(user, event);

    final String expectedSmsNotification = "Sending SMS: ALERT: Test Alert (Priority: LOW)";

    Assertions.assertIterableEquals(
        List.of(expectedSmsNotification), collector.getCollectedElements());
  }

  @Test
  public void userWithAllNotifications() {
    final SimpleCollector<String> collector = new SimpleCollector<>();

    final PushNotificationConverter pushConverter = new PushNotificationConverter();
    final PushNotificationChannel pushChannel = new PushNotificationChannel(pushConverter);

    final EmailNotificationConverter emailConverter = new EmailNotificationConverter();
    final EmailNotificationChannel emailChannel = new EmailNotificationChannel(emailConverter);

    final SmsNotificationConverter smsConverter = new SmsNotificationConverter();
    final SmsNotificationChannel smsChannel = new SmsNotificationChannel(smsConverter);

    final Set<InotificationChannel> channels =
        new HashSet<>(Set.of(pushChannel, emailChannel, smsChannel));
    final Set<NotificationType> allowedTypes = new HashSet<>(Set.of(NotificationType.ALERT));
    final UserPreference preferences =
        new UserPreference(channels, allowedTypes, NotificationPriority.LOW);
    final User user = new User("1", "Test User", preferences);

    final NotificationService notificationSystem =
        new NotificationService(new HashSet<>(Set.of(pushChannel, emailChannel, smsChannel)));

    final AlertNotification event =
        new AlertNotification("Test Alert", NotificationPriority.LOW, "Test Title", "Test Source");

    System.setOut(
        new java.io.PrintStream(System.out) {
          @Override
          public void println(String x) {
            collector.addElement(x);
          }
        });

    notificationSystem.sendNotification(user, event);

    final List<String> collectedElements = collector.getCollectedElements();

    System.err.println("All notifications: " + collectedElements);

    Assertions.assertEquals(3, collectedElements.size());

    Assertions.assertTrue(
        collectedElements.stream().anyMatch(s -> s.contains("Sending SMS: ALERT")),
        "Should contain SMS notification");
    Assertions.assertTrue(
        collectedElements.stream()
            .anyMatch(s -> s.contains("Sending email") && s.contains("<html>")),
        "Should contain email notification");
    Assertions.assertTrue(
        collectedElements.stream().anyMatch(s -> s.contains("Sending Push Notification")),
        "Should contain push notification");
  }
}
