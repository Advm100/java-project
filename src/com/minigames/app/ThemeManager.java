package com.minigames.app;

import java.awt.Color;
import java.io.Serializable;

/**
 * Gestionnaire de thème simplifié
 */
public class ThemeManager implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Types de thèmes disponibles
    public enum Theme {
        DARK("Sombre"),
        LIGHT("Clair"),
        BLUE("Bleu");
        
        private final String displayName;
        
        Theme(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    // Instance unique (Singleton)
    private static ThemeManager instance;
    
    // Thème actuel
    private Theme currentTheme = Theme.DARK;
    
    // Constructeur privé (Singleton)
    private ThemeManager() { }
    
    /**
     * Obtenir l'instance unique
     */
    public static ThemeManager getInstance() {
        if (instance == null) {
            instance = new ThemeManager();
        }
        return instance;
    }
    
    /**
     * Obtenir le thème actuel
     */
    public Theme getCurrentTheme() {
        return currentTheme;
    }
    
    /**
     * Définir le thème actuel
     */
    public void setTheme(Theme theme) {
        this.currentTheme = theme;
        updateColors();
    }
    
    /**
     * Passer au thème suivant
     */
    public void nextTheme() {
        Theme[] themes = Theme.values();
        int nextIndex = (currentTheme.ordinal() + 1) % themes.length;
        setTheme(themes[nextIndex]);
    }
    
    /**
     * Mettre à jour les couleurs dans StyleUtils
     */
    private void updateColors() {
        switch (currentTheme) {
            case LIGHT:
                // Thème clair
                StyleUtils.BACKGROUND_DARK = new Color(230, 230, 235);
                StyleUtils.BACKGROUND_MEDIUM = new Color(240, 240, 245);
                StyleUtils.BACKGROUND_LIGHT = new Color(250, 250, 255);
                StyleUtils.ACCENT_BLUE = new Color(70, 130, 220);
                StyleUtils.TEXT_BRIGHT = new Color(30, 30, 35);
                StyleUtils.TEXT_DIM = new Color(100, 100, 110);
                break;
                
            case BLUE:
                // Thème bleu
                StyleUtils.BACKGROUND_DARK = new Color(25, 35, 55);
                StyleUtils.BACKGROUND_MEDIUM = new Color(35, 45, 65);
                StyleUtils.BACKGROUND_LIGHT = new Color(45, 55, 75);
                StyleUtils.ACCENT_BLUE = new Color(100, 170, 255);
                StyleUtils.TEXT_BRIGHT = new Color(220, 230, 255);
                StyleUtils.TEXT_DIM = new Color(170, 190, 225);
                break;
                
            case DARK:
            default:
                // Thème sombre (par défaut)
                StyleUtils.BACKGROUND_DARK = new Color(30, 34, 42);
                StyleUtils.BACKGROUND_MEDIUM = new Color(40, 44, 52);
                StyleUtils.BACKGROUND_LIGHT = new Color(50, 54, 62);
                StyleUtils.ACCENT_BLUE = new Color(97, 175, 239);
                StyleUtils.TEXT_BRIGHT = new Color(220, 223, 228);
                StyleUtils.TEXT_DIM = new Color(180, 183, 188);
                break;
        }
    }
}