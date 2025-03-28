package com.minigames.app;

import javax.swing.*;
import com.minigames.ui.LoginScreen;
import com.minigames.user.UserManager;

/**
 * Main application class that serves as the entry point
 */
public class MiniGamesApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginScreen loginScreen = new LoginScreen();
            loginScreen.setVisible(true);
        });
    }
    
    public static void addPoints(int points) {
        UserManager.getInstance().addPointsToCurrentUser(points);
    }
    
    public static int getTotalPoints() {
        if (UserManager.getInstance().getCurrentUser() != null) {
            return UserManager.getInstance().getCurrentUser().getTotalPoints();
        }
        return 0;
    }
}