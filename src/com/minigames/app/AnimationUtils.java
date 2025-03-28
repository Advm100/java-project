package com.minigames.app;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

/**
 * Utilitaire simple pour les animations
 */
public class AnimationUtils {
    /**
     * Crée un effet de fondu pour un JPanel
     * 
     * @param panel Le panel à animer
     * @param fadeIn true pour un fondu d'entrée, false pour un fondu de sortie
     * @param duration Durée de l'animation en millisecondes
     */
    public static void fadePanel(JPanel panel, boolean fadeIn, int duration) {
        float opacity = fadeIn ? 0.0f : 1.0f;
        float step = 0.05f;
        
        // Rendre le panel transparent
        panel.setOpaque(false);
        
        // Timer pour l'animation
        Timer timer = new Timer(duration / 20, null);
        timer.addActionListener(e -> {
            if (fadeIn) {
                opacity += step;
                if (opacity >= 1.0f) {
                    opacity = 1.0f;
                    timer.stop();
                }
            } else {
                opacity -= step;
                if (opacity <= 0.0f) {
                    opacity = 0.0f;
                    timer.stop();
                }
            }
            
            // Appliquer l'opacité
            panel.putClientProperty("opacity", opacity);
            panel.repaint();
        });
        
        timer.start();
    }
    
    /**
     * Anime la rotation d'une carte (effet 3D)
     * 
     * @param cardButton Le bouton à animer
     * @param duration Durée de l'animation en millisecondes
     * @param onComplete Action à exécuter à la fin de l'animation
     */
    public static void flipCard(JButton cardButton, int duration, Runnable onComplete) {
        // Créer une image du bouton initial
        BufferedImage originalImage = new BufferedImage(
            cardButton.getWidth(), cardButton.getHeight(), BufferedImage.TYPE_INT_ARGB);
        cardButton.paint(originalImage.getGraphics());
        
        // Timer pour l'animation
        float[] scale = {1.0f}; // Facteur d'échelle
        boolean[] flipping = {true}; // Première moitié de l'animation
        
        Timer timer = new Timer(duration / 20, null);
        timer.addActionListener(e -> {
            if (flipping[0]) {
                // Réduire la largeur (première moitié)
                scale[0] -= 0.1f;
                if (scale[0] <= 0.0f) {
                    scale[0] = 0.0f;
                    flipping[0] = false;
                    
                    // Milieu de l'animation - mettre à jour l'apparence
                    if (onComplete != null) {
                        onComplete.run();
                    }
                }
            } else {
                // Augmenter la largeur (seconde moitié)
                scale[0] += 0.1f;
                if (scale[0] >= 1.0f) {
                    scale[0] = 1.0f;
                    timer.stop();
                }
            }
            
            // Forcer le redessin avec la nouvelle échelle
            cardButton.putClientProperty("flipScale", scale[0]);
            cardButton.repaint();
        });
        
        // Remplacer le paintComponent par une version avec effet 3D
        cardButton.putClientProperty("originalPaint", cardButton.getUI().getClass());
        cardButton.putClientProperty("flipScale", 1.0f);
        
        timer.start();
    }
    
    /**
     * Affiche une notification flottante
     * 
     * @param parent Le composant parent
     * @param message Le message à afficher
     * @param color La couleur du message
     * @param duration Durée d'affichage en millisecondes
     */
    public static void showFloatingNotification(Component parent, String message, Color color, int duration) {
        // Créer un JDialog pour la notification
        JDialog dialog = new JDialog();
        dialog.setUndecorated(true);
        dialog.setLayout(new BorderLayout());
        dialog.setBackground(new Color(0, 0, 0, 0));
        
        // Panel pour le contenu
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(40, 40, 40, 200));
        panel.setBorder(BorderFactory.createLineBorder(color, 2));
        
        // Étiquette pour le message
        JLabel label = new JLabel(message);
        label.setForeground(color);
        label.setFont(new Font("Segoe UI", Font.BOLD, 16));
        label.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Assembler les composants
        panel.add(label, BorderLayout.CENTER);
        dialog.add(panel, BorderLayout.CENTER);
        dialog.pack();
        
        // Positionner la notification en haut du parent
        Point location = parent.getLocationOnScreen();
        dialog.setLocation(
            location.x + (parent.getWidth() - dialog.getWidth()) / 2,
            location.y + 50
        );
        
        // Afficher la notification
        dialog.setVisible(true);
        
        // Timer pour le fondu
        new Timer(duration, e -> {
            dialog.dispose();
        }).start();
    }
    
    /**
     * Crée un effet de scintillement pour un composant
     * 
     * @param component Le composant à animer
     * @param color La couleur de scintillement
     * @param duration Durée de l'animation en millisecondes
     */
    public static void sparkleEffect(JComponent component, Color color, int duration) {
        // Créer un calque de peinture par-dessus le composant
        component.putClientProperty("sparkle", true);
        component.putClientProperty("sparkleColor", color);
        component.putClientProperty("sparkleIntensity", 1.0f);
        
        // Timer pour l'animation
        Timer timer = new Timer(duration / 10, null);
        timer.addActionListener(e -> {
            float intensity = (float) component.getClientProperty("sparkleIntensity");
            intensity -= 0.1f;
            
            if (intensity <= 0) {
                intensity = 0;
                component.putClientProperty("sparkle", false);
                timer.stop();
            }
            
            component.putClientProperty("sparkleIntensity", intensity);
            component.repaint();
        });
        
        timer.start();
    }
}