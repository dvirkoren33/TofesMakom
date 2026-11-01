package com.example.tofesmakom; // שים לב לשנות לפי שם החבילה שלך

public class ScoreItem {
    private String playerName;
    private int score;
    private String date;

    // בנאי (Constructor)
    public ScoreItem(String playerName, int score, String date) {
        this.playerName = playerName;
        this.score = score;
        this.date = date;
    }

    // Getters
    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    public String getDate() {
        return date;
    }
}