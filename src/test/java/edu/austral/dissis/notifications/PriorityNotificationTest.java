package edu.austral.dissis.notifications;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PriorityNotificationTest {
  private NotificationService notificationService;
  private InotificationChannel emailChannel;

  @BeforeEach
  public void setUp() {
    final InotificationConverter emailConverter = new EmailNotificationConverter();

    emailChannel = new EmailNotificationChannel(emailConverter);

    final Set<InotificationChannel> channels = new HashSet<>();
    channels.add(emailChannel);

    notificationService = new NotificationService(channels);
  }

  @Test
  public void highPriorityOnlyUserGetsHighPriorityNotification() {
    final SimpleCollector<String> collector = new SimpleCollector<>();

    final Set<InotificationChannel> userChannels = new HashSet<>();
    userChannels.add(emailChannel);

    final Set<NotificationType> allowedTypes = new HashSet<>();
    allowedTypes.add(NotificationType.ALERT);

    final UserPreference preferences =
        new UserPreference(userChannels, allowedTypes, NotificationPriority.HIGH);

    final User user = new User("1", "High Priority User", preferences);

    final AlertNotification highPriorityEvent =
        new AlertNotification(
            "High Priority Alert", NotificationPriority.HIGH, "Critical", "System");

    final AlertNotification mediumPriorityEvent =
        new AlertNotification(
            "Medium Priority Alert", NotificationPriority.MEDIUM, "Warning", "System");

    final AlertNotification lowPriorityEvent =
        new AlertNotification("Low Priority Alert", NotificationPriority.LOW, "Info", "System");

    System.setOut(
        new java.io.PrintStream(System.out) {
          @Override
          public void println(String x) {
            collector.addElement(x);
            super.println(x);
          }
        });

    notificationService.sendNotification(user, highPriorityEvent);
    notificationService.sendNotification(user, mediumPriorityEvent);
    notificationService.sendNotification(user, lowPriorityEvent);

    final String expectedHighPriorityEmail =
        "Sending email: <html><body><h1>Alert</h1><p>High Priority Alert</p>"
            + "<p>Priority: HIGH</p></body></html>";

    assertIterableEquals(List.of(expectedHighPriorityEmail), collector.getCollectedElements());
  }

  @Test
  public void mediumAndHighPriorityUserGetsMediumAndHighNotifications() {
    final SimpleCollector<String> collector = new SimpleCollector<>();

    final Set<InotificationChannel> userChannels = new HashSet<>();
    userChannels.add(emailChannel);

    final Set<NotificationType> allowedTypes = new HashSet<>();
    allowedTypes.add(NotificationType.ALERT);

    final UserPreference preferences =
        new UserPreference(userChannels, allowedTypes, NotificationPriority.MEDIUM);

    final User user = new User("2", "Medium and High Priority User", preferences);

    final AlertNotification highPriorityEvent =
        new AlertNotification(
            "High Priority Alert", NotificationPriority.HIGH, "Critical", "System");

    final AlertNotification mediumPriorityEvent =
        new AlertNotification(
            "Medium Priority Alert", NotificationPriority.MEDIUM, "Warning", "System");

    final AlertNotification lowPriorityEvent =
        new AlertNotification("Low Priority Alert", NotificationPriority.LOW, "Info", "System");

    System.setOut(
        new java.io.PrintStream(System.out) {
          @Override
          public void println(String x) {
            collector.addElement(x);
            super.println(x);
          }
        });

    notificationService.sendNotification(user, highPriorityEvent);
    notificationService.sendNotification(user, mediumPriorityEvent);
    notificationService.sendNotification(user, lowPriorityEvent);

    final String expectedHighPriorityEmail =
        "Sending email: <html><body><h1>Alert</h1><p>High Priority Alert</p>"
            + "<p>Priority: HIGH</p></body></html>";
    final String expectedMediumPriorityEmail =
        "Sending email: <html><body><h1>Alert</h1><p>Medium Priority Alert</p>"
            + "<p>Priority: MEDIUM</p></body></html>";

    assertIterableEquals(
        List.of(expectedHighPriorityEmail, expectedMediumPriorityEmail),
        collector.getCollectedElements());
  }
}
