package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

public class Chat {
    @JsonProperty("chat_id")
    private int chatId;
    @JsonProperty("user_id")
    private int userId;
    @JsonProperty("game_id")
    private int gameId;
    @JsonProperty("chat_date")
    private Timestamp date;
    @JsonProperty("chat_content")
    private String content;

    public Chat() {
    }
    public Chat(int chatId, int userId, int gameId, Timestamp date, String content) {
        this.chatId = chatId;
        this.userId = userId;
        this.gameId = gameId;
        this.date = date;
        this.content = content;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
