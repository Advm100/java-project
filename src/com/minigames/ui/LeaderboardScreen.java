package com.minigames.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

import com.minigames.app.StyleUtils;
import com.minigames.user.LeaderboardManager;
import com.minigames.user.LeaderboardManager.LeaderboardEntry;

/**
 * Écran pour afficher les classements
 */
public class LeaderboardScreen extends JFrame {
    private MainMenu mainMenu;
    
    public LeaderboardScreen(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
        
        StyleUtils.applyGameStyle(this, "Mini-Games - Classements");
        
        // Panneau principal avec fond dégradé
        JPanel mainPanel = StyleUtils.createGradientPanel();
        mainPanel.setLayout(new BorderLayout(0, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        // Titre
        JLabel titleLabel = StyleUtils.createTitleLabel("CLASSEMENTS");
        
        // Panneau d'onglets pour les différents jeux
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabbedPane.setForeground(StyleUtils.TEXT_BRIGHT);
        tabbedPane.setBackground(StyleUtils.BACKGROUND_MEDIUM);
        
        // Ajouter un onglet pour chaque jeu
        tabbedPane.addTab("Memory Game", createLeaderboardPanel("Memory Game"));
        tabbedPane.addTab("Snake Game", createLeaderboardPanel("Snake Game"));
        
        // Bouton de retour
        JButton backButton = StyleUtils.createStyledButton("RETOUR AU MENU", StyleUtils.ACCENT_BLUE);
        backButton.addActionListener(e -> {
            dispose();
            mainMenu.setVisible(true);
        });
        
        // Ajouter les composants
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        mainPanel.add(backButton, BorderLayout.SOUTH);
        
        setContentPane(mainPanel);
        setSize(600, 500);
        setLocationRelativeTo(null);
    }
    
    /**
     * Crée un panneau pour afficher le classement d'un jeu
     */
    private JPanel createLeaderboardPanel(String gameName) {
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setOpaque(false);
        
        // En-tête
        JPanel headerPanel = new JPanel(new GridLayout(1, 3));
        headerPanel.setBackground(StyleUtils.ACCENT_BLUE);
        
        JLabel rankHeader = new JLabel("Rang");
        rankHeader.setForeground(Color.WHITE);
        rankHeader.setFont(new Font("Segoe UI", Font.BOLD, 14));
        rankHeader.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel nameHeader = new JLabel("Joueur");
        nameHeader.setForeground(Color.WHITE);
        nameHeader.setFont(new Font("Segoe UI", Font.BOLD, 14));
        nameHeader.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel scoreHeader = new JLabel("Score");
        scoreHeader.setForeground(Color.WHITE);
        scoreHeader.setFont(new Font("Segoe UI", Font.BOLD, 14));
        scoreHeader.setHorizontalAlignment(SwingConstants.CENTER);
        
        headerPanel.add(rankHeader);
        headerPanel.add(nameHeader);
        headerPanel.add(scoreHeader);
        
        // Liste des scores
        List<LeaderboardEntry> leaderboard = LeaderboardManager.getLeaderboard(gameName);
        
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setOpaque(false);
        
        if (leaderboard.isEmpty()) {
            JLabel emptyLabel = new JLabel("Aucun score enregistré pour ce jeu");
            emptyLabel.setForeground(StyleUtils.TEXT_DIM);
            emptyLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            listPanel.add(Box.createVerticalGlue());
            listPanel.add(emptyLabel);
            listPanel.add(Box.createVerticalGlue());
        } else {
            for (int i = 0; i < leaderboard.size(); i++) {
                LeaderboardEntry entry = leaderboard.get(i);
                
                JPanel entryPanel = createLeaderboardEntryPanel(i + 1, entry);
                listPanel.add(entryPanel);
                
                // Ajouter un séparateur entre les entrées (sauf la dernière)
                if (i < leaderboard.size() - 1) {
                    listPanel.add(Box.createVerticalStrut(5));
                    JSeparator separator = new JSeparator();
                    separator.setForeground(new Color(70, 74, 82));
                    separator.setMaximumSize(new Dimension(Short.MAX_VALUE, 1));
                    listPanel.add(separator);
                    listPanel.add(Box.createVerticalStrut(5));
                }
            }
        }
        
        // Panneau défilable
        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Crée un panneau pour une entrée de classement
     */
    private JPanel createLeaderboardEntryPanel(int rank, LeaderboardEntry entry) {
        JPanel panel = new JPanel(new GridLayout(1, 3));
        panel.setOpaque(false);
        
        // Déterminer la couleur en fonction du rang
        Color textColor;
        if (rank == 1) {
            textColor = new Color(255, 215, 0); // Or
        } else if (rank == 2) {
            textColor = new Color(192, 192, 192); // Argent
        } else if (rank == 3) {
            textColor = new Color(205, 127, 50); // Bronze
        } else {
            textColor = StyleUtils.TEXT_BRIGHT;
        }
        
        // Rang
        JLabel rankLabel = new JLabel("#" + rank);
        rankLabel.setForeground(textColor);
        rankLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        rankLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Nom d'utilisateur
        JLabel nameLabel = new JLabel(entry.getUsername());
        nameLabel.setForeground(textColor);
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Score
        JLabel scoreLabel = new JLabel(String.valueOf(entry.getScore()));
        scoreLabel.setForeground(textColor);
        scoreLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        panel.add(rankLabel);
        panel.add(nameLabel);
        panel.add(scoreLabel);
        
        return panel;
    }
}