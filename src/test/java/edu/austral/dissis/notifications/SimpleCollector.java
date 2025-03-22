package edu.austral.dissis.notifications;

import java.util.ArrayList;
import java.util.List;

public class SimpleCollector<T> {
  private final List<T> collectedElements = new ArrayList<>();

  public List<T> getCollectedElements() {
    return collectedElements;
  }

  public void addElement(T element) {
    collectedElements.add(element);
  }
}
