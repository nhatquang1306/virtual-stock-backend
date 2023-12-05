package com.techelevator.dao;

import com.techelevator.model.Chat;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcChatDao implements ChatDao {

    private final JdbcTemplate jdbcTemplate;
    public JdbcChatDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public void insertChat(String username, int gameId, Chat chat) {
        String sql = "INSERT INTO chat (user_id, game_id, chat_date, chat_content) VALUES ((SELECT user_id FROM users WHERE username = ?), ?, ?, ?)";
        jdbcTemplate.update(sql, username, gameId, chat.getDate(), chat.getContent());
    }
}
