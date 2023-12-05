package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

public class Game {
    @JsonProperty("game_id")
    private int gameId;
    @JsonProperty("game_name")
    private String name;
    @JsonProperty("start_date")
    private Timestamp startDate;
    @JsonProperty("end_date")
    private Timestamp endDate;
    @JsonProperty("creator_id")
    private int creatorId;
    @JsonProperty("winner")
    private String winner;

    public Game() {
    }
    public Game(int gameId, String name, Timestamp startDate, Timestamp endDate, int creatorId) {
        this.gameId = gameId;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.creatorId = creatorId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}



