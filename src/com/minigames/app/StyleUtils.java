package com.minigames.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe utilitaire optimisée pour le style de l'application
 */
public class StyleUtils {
    // Cache des icônes
    private static Map<String, ImageIcon> iconCache = new HashMap<>();
    
    // Couleurs communes
    public static final Color BACKGROUND_DARK = new Color(30, 34, 42);
    public static final Color BACKGROUND_MEDIUM = new Color(40, 44, 52);
    public static final Color BACKGROUND_LIGHT = new Color(50, 54, 62);
    public static final Color ACCENT_BLUE = new Color(97, 175, 239);
    public static final Color ACCENT_GREEN = new Color(152, 195, 121);
    public static final Color ACCENT_YELLOW = new Color(229, 192, 123);
    public static final Color ACCENT_RED = new Color(224, 108, 117);
    public static final Color TEXT_BRIGHT = new Color(220, 223, 228);
    public static final Color TEXT_DIM = new Color(180, 183, 188);
    
    // Dimensions communes
    public static final Dimension WINDOW_SIZE = new Dimension(900, 700);
    
    /**
     * Applique un style à la fenêtre de jeu
     */
    public static void applyGameStyle(JFrame frame, String title) {
        frame.setTitle(title);
        frame.setSize(WINDOW_SIZE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Crée un panel avec un fond dégradé
     */
    public static JPanel createGradientPanel() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                
                // Fond dégradé
                GradientPaint gp = new GradientPaint(
                    0, 0, BACKGROUND_DARK, 
                    getWidth(), getHeight(), BACKGROUND_LIGHT
                );
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
    }
    
    /**
     * Crée un label de titre
     */
    public static JLabel createTitleLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(TEXT_BRIGHT);
        label.setFont(new Font("Segoe UI", Font.BOLD, 28));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }
    
    /**
     * Crée un label de sous-titre
     */
    public static JLabel createSubtitleLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(ACCENT_BLUE);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }
    
    /**
     * Crée un bouton stylisé
     */
    public static JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Effet de survol
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(brighten(color, 0.15f));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });
        
        return button;
    }
    
    /**
     * Crée un panel arrondi
     */
    public static JPanel createRoundPanel(Color background, int radius) {
        JPanel panel = new JPanel();
        panel.setBackground(background);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setOpaque(true);
        return panel;
    }
    
    /**
     * Crée une icône de pièce pour l'affichage des points (avec cache)
     */
    public static ImageIcon createCoinIcon() {
        // Vérifie d'abord le cache
        if (iconCache.containsKey("coin")) {
            return iconCache.get("coin");
        }
        
        int size = 24;
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        
        // Dessine une pièce dorée
        g2d.setColor(new Color(255, 215, 0)); // Couleur or
        g2d.fillOval(0, 0, size, size);
        
        g2d.dispose();
        
        // Met en cache avant de retourner
        ImageIcon icon = new ImageIcon(image);
        iconCache.put("coin", icon);
        return icon;
    }
    
    /**
     * Éclaircit une couleur selon le facteur spécifié
     */
    private static Color brighten(Color color, float factor) {
        int r = Math.min(255, (int)(color.getRed() * (1 + factor)));
        int g = Math.min(255, (int)(color.getGreen() * (1 + factor)));
        int b = Math.min(255, (int)(color.getBlue() * (1 + factor)));
        return new Color(r, g, b);
    }
}