package com.minigames.user;

import java.io.*;
import java.util.*;

/**
 * Classe pour gérer un classement des meilleurs scores
 */
public class LeaderboardManager {
    private static final String LEADERBOARD_FILE = "leaderboard.ser";
    private static Map<String, List<LeaderboardEntry>> gameLeaderboards = new HashMap<>();
    
    // Nombre maximum d'entrées par jeu
    private static final int MAX_ENTRIES = 10;
    
    // Classe pour stocker une entrée de classement
    public static class LeaderboardEntry implements Serializable {
        private static final long serialVersionUID = 1L;
        
        private String username;
        private int score;
        private long date;
        
        public LeaderboardEntry(String username, int score) {
            this.username = username;
            this.score = score;
            this.date = System.currentTimeMillis();
        }
        
        public String getUsername() {
            return username;
        }
        
        public int getScore() {
            return score;
        }
        
        public long getDate() {
            return date;
        }
        
        public String getFormattedDate() {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
            return sdf.format(new java.util.Date(date));
        }
    }
    
    // Initialiser les classements
    static {
        loadLeaderboards();
    }
    
    // Ajouter un score au classement
    public static void addScore(String gameName, String username, int score) {
        // Récupérer le classement du jeu
        List<LeaderboardEntry> gameLeaderboard = gameLeaderboards.getOrDefault(
            gameName, new ArrayList<>());
        
        // Ajouter le nouveau score
        gameLeaderboard.add(new LeaderboardEntry(username, score));
        
        // Trier par score (décroissant)
        Collections.sort(gameLeaderboard, (e1, e2) -> Integer.compare(e2.getScore(), e1.getScore()));
        
        // Garder seulement les MAX_ENTRIES meilleurs scores
        if (gameLeaderboard.size() > MAX_ENTRIES) {
            gameLeaderboard = new ArrayList<>(gameLeaderboard.subList(0, MAX_ENTRIES));
        }
        
        // Mettre à jour le classement
        gameLeaderboards.put(gameName, gameLeaderboard);
        
        // Sauvegarder les changements
        saveLeaderboards();
    }
    
    // Obtenir le classement d'un jeu
    public static List<LeaderboardEntry> getLeaderboard(String gameName) {
        return new ArrayList<>(gameLeaderboards.getOrDefault(gameName, new ArrayList<>()));
    }
    
    // Obtenir le rang d'un utilisateur
    public static int getUserRank(String gameName, String username) {
        List<LeaderboardEntry> leaderboard = getLeaderboard(gameName);
        
        for (int i = 0; i < leaderboard.size(); i++) {
            if (leaderboard.get(i).getUsername().equals(username)) {
                return i + 1; // +1 car les indices commencent à 0
            }
        }
        
        return -1; // Utilisateur non trouvé dans le classement
    }
    
    // Charger les classements depuis le fichier
    private static void loadLeaderboards() {
        try {
            File file = new File(LEADERBOARD_FILE);
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                gameLeaderboards = (Map<String, List<LeaderboardEntry>>) ois.readObject();
                ois.close();
                fis.close();
            }
        } catch (Exception e) {
            System.out.println("Erreur lors du chargement des classements: " + e.getMessage());
            gameLeaderboards = new HashMap<>();
        }
    }
    
    // Sauvegarder les classements dans le fichier
    private static void saveLeaderboards() {
        try {
            FileOutputStream fos = new FileOutputStream(LEADERBOARD_FILE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(gameLeaderboards);
            oos.close();
            fos.close();
        } catch (Exception e) {
            System.out.println("Erreur lors de la sauvegarde des classements: " + e.getMessage());
        }
    }
}