package edu.austral.dissis.notifications;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

public class SimpleNotificationTest {

  @Test
  public void userWithNoNotifications() {
    SimpleCollector<String> collector = new SimpleCollector<>();

    /*
     * NotificationSystem notificationSystem = ...
     * notificationSystem.handleNewEvent(userConfiguration, event);
     * */

    assertIterableEquals(List.of(), collector.getCollectedElements());
  }

  @Test
  public void userWithPushNotification() {
    SimpleCollector<String> collector = new SimpleCollector<>();

    /*
     * NotificationSystem notificationSystem = ...
     * notificationSystem.handleNewEvent(userConfiguration, event);
     * */

    assertIterableEquals(
        List.of(
            // notification
            ),
        collector.getCollectedElements());
  }

  @Test
  public void userWithEmailNotification() {
    SimpleCollector<String> collector = new SimpleCollector<>();

    /*
     * NotificationSystem notificationSystem = ...
     * notificationSystem.handleNewEvent(userConfiguration, event);
     * */

    assertIterableEquals(
        List.of(
            // notification
            ),
        collector.getCollectedElements());
  }

  @Test
  public void userWithSmsNotification() {
    SimpleCollector<String> collector = new SimpleCollector<>();

    /*
     * NotificationSystem notificationSystem = ...
     * notificationSystem.handleNewEvent(userConfiguration, event);
     * */

    assertIterableEquals(
        List.of(
            // notification
            ),
        collector.getCollectedElements());
  }

  @Test
  public void userWithAllNotifications() {
    SimpleCollector<String> collector = new SimpleCollector<>();

    /*
     * NotificationSystem notificationSystem = ...
     * notificationSystem.handleNewEvent(userConfiguration, event);
     * */

    assertIterableEquals(
        List.of(
            // notification
            // notification
            // notification
            ),
        collector.getCollectedElements());
  }
}
