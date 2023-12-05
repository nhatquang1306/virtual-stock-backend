package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerRank {
    @JsonProperty("username")
    private String username;
    @JsonProperty("win_count")
    private int winCount;
    @JsonProperty("total_games")
    private int totalGames;
    @JsonProperty("win_percentage")
    private double winPercentage;
    @JsonProperty("rank")
    private int rank;

    public PlayerRank(String username, int winCount, int totalGames, double winPercentage, int rank) {
        this.username = username;
        this.winCount = winCount;
        this.totalGames = totalGames;
        this.winPercentage = winPercentage;
        this.rank = rank;
    }

    public PlayerRank() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getWinCount() {
        return winCount;
    }

    public void setWinCount(int winCount) {
        this.winCount = winCount;
    }

    public int getTotalGames() {
        return totalGames;
    }

    public void setTotalGames(int totalGames) {
        this.totalGames = totalGames;
    }

    public double getWinPercentage() {
        return winPercentage;
    }

    public void setWinPercentage(double winPercentage) {
        this.winPercentage = winPercentage;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
