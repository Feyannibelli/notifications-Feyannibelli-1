package edu.austral.dissis.notifications;

public class User {
    private final UserPreferences preferences;

    public User(UserPreferences preferences) {
        this.preferences = preferences;
    }

    public UserPreferences getPreferences() {
        return preferences;
    }
}
