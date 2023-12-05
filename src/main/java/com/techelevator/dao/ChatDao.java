package com.techelevator.dao;

import com.techelevator.model.Chat;

public interface ChatDao {
    void insertChat(String username, int gameId, Chat chat);
}
