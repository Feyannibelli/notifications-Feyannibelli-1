package edu.austral.dissis.notifications;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

public class PriorityNotificationTest {

  @Test
  public void highPriorityOnlyUserGetsHighPriorityNotification() {
    SimpleCollector<String> collector = new SimpleCollector<>();

    /*
     * NotificationSystem notificationSystem = ...
     * notificationSystem.handleNewEvent(userConfiguration, highPriorityEvent);
     * notificationSystem.handleNewEvent(userConfiguration, mediumPriorityEvent);
     * notificationSystem.handleNewEvent(userConfiguration, lowPriorityEvent);
     * */

    assertIterableEquals(
        List.of(
            // notification
            ),
        collector.getCollectedElements());
  }

  public void mediumAndHighPriorityUserGetsMediumAndHighNotifiactions() {
    SimpleCollector<String> collector = new SimpleCollector<>();

    /*
     * NotificationSystem notificationSystem = ...
     * notificationSystem.handleNewEvent(userConfiguration, highPriorityEvent);
     * notificationSystem.handleNewEvent(userConfiguration, mediumPriorityEvent);
     * notificationSystem.handleNewEvent(userConfiguration, lowPriorityEvent);
     * */

    assertIterableEquals(
        List.of(
            // notification
            // notification
            ),
        collector.getCollectedElements());
  }
}
