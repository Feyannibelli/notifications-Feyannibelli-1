package edu.austral.dissis.notifications;

public class User {
  private final String id;
  private final String name;
  private final UserPreference preferences;

  public User(String id, String name, UserPreference preferences) {
    this.id = id;
    this.name = name;
    this.preferences = preferences;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public UserPreference getPreferences() {
    return preferences;
  }
}
