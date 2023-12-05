package com.techelevator.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techelevator.model.Game;
import com.techelevator.model.PlayerRank;
import com.techelevator.model.UserWithBalance;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

@Component
public class JdbcGameDao implements GameDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcGameDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<Game> getAllGames() {
        List<Game> games = new ArrayList<>();
        String sql = "SELECT * FROM game ORDER BY game_id";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
        while (result.next()) {
            games.add(mapRowsToGame(result));
        }
        return games;
    }

    @Override
    public Game getGameById(int gameId) {
        String sql = "SELECT * FROM game WHERE game_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, gameId);
        if (result.next()) {
            return mapRowsToGame(result);
        }
        return null;
    }

    @Override
    public List<Integer> getGamesForUser(String username) {
        List<Integer> games = new ArrayList<>();
        String sql = "SELECT game_id FROM user_game WHERE user_id IN (SELECT user_id FROM users WHERE username = ?)";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, username);
        while (result.next()) {
            games.add(result.getInt("game_id"));
        }
        return games;
    }

    @Override
    public List<String> getUsersForGame(int gameId) {
        List<String> users = new ArrayList<>();
        String sql = "SELECT username FROM users WHERE user_id IN (SELECT user_id FROM user_game WHERE game_id = ?)";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, gameId);
        while (result.next()) {
            users.add(result.getString("username"));
        }
        return users;
    }

    @Override
    public List<UserWithBalance> getLeaderboard(int gameId) {
        List<UserWithBalance> leaderboard = new ArrayList<>();
        String sql = "SELECT username, game_balance, DENSE_RANK() OVER (ORDER BY game_balance DESC) AS rank FROM user_game " +
                "JOIN users USING (user_id) " +
                "WHERE game_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, gameId);
        while (result.next()) {
            UserWithBalance user = new UserWithBalance();
            user.setBalance(result.getBigDecimal("game_balance"));
            user.setUsername(result.getString("username"));
            user.setRank(result.getInt("rank"));
            leaderboard.add(user);
        }
        return leaderboard;
    }

    @Override
    public BigDecimal getUserBalance(String username, int gameId) {
        String sql = "SELECT game_balance FROM user_game WHERE user_id = (SELECT user_id FROM users WHERE username = ?) AND game_id = ?";
        return jdbcTemplate.queryForObject(sql, BigDecimal.class, username, gameId);
    }

    @Override
    public Game createGame(String username, Game game) {
        int id;
        String sql = "INSERT INTO game (game_name, start_date, end_date, creator_id) VALUES (?, ?, ?, (SELECT user_id FROM users WHERE username = ?)) RETURNING game_id";
        try {
            id = jdbcTemplate.queryForObject(sql, int.class, game.getName(), game.getStartDate(), game.getEndDate(), username);
        } catch (Exception e) {
            throw new Error(e.getMessage());
        }
        return getGameById(id);
    }

    @Override
    public void joinGame(String username, int gameId) {
        String sql = "INSERT INTO user_game (user_id, game_id, game_balance) " +
                "VALUES ((SELECT user_id FROM users WHERE username = ?), ?, 100000);";
        try {
            jdbcTemplate.update(sql, username, gameId);
        } catch (Exception e) {
            throw new Error(e.getMessage());
        }

    }

    @Override
    public void leaveGame(String username, int gameId) {
        String sql = "DELETE FROM user_game WHERE user_id = (SELECT user_id FROM users WHERE username = ?) AND game_id = ?";
        jdbcTemplate.update(username, gameId);
    }

    @Override
    public String setWinner(int gameId) {
        String getWinner = "SELECT username FROM users WHERE user_id = (SELECT user_id FROM user_game WHERE game_id = ? ORDER BY game_balance DESC LIMIT 1)";
        String username = jdbcTemplate.queryForObject(getWinner, String.class, gameId);
        String sql = "UPDATE game SET winner = ? WHERE game_id = ?";
        jdbcTemplate.update(sql, username, gameId);
        return username;
    }

    @Override
    public List<PlayerRank> getGlobalLeaderboard() {
        List<PlayerRank> leaderboard = new ArrayList<>();
        String sql = "WITH games_count AS " +
                "(SELECT username, COUNT(*) AS total_games FROM user_game JOIN users USING (user_id) " +
                "WHERE game_id IN (SELECT game_id FROM game WHERE winner IS NOT NULL) GROUP BY username)" +
                "SELECT username, total_games, wins, DENSE_RANK() OVER (ORDER BY wins DESC NULLS LAST) AS rank " +
                "FROM games_count LEFT JOIN " +
                "(SELECT COUNT(*) AS wins, winner FROM game WHERE winner IS NOT NULL " +
                "GROUP BY winner ORDER BY wins DESC) wins_count ON wins_count.winner = games_count.username;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            leaderboard.add(mapRowToPlayerRank(results));
        }
        return leaderboard;
    }

    private Game mapRowsToGame(SqlRowSet rs) {
        Game game = new Game();
        game.setGameId(rs.getInt("game_id"));
        game.setName(rs.getString("game_name"));
        game.setStartDate(rs.getTimestamp("start_date"));
        game.setEndDate(rs.getTimestamp("end_date"));
        game.setCreatorId(rs.getInt("creator_id"));
        game.setWinner(rs.getString("winner"));
        return game;
    }
    private PlayerRank mapRowToPlayerRank(SqlRowSet rs) {
        PlayerRank playerRank = new PlayerRank();
        playerRank.setUsername(rs.getString("username"));
        playerRank.setTotalGames(rs.getInt("total_games"));
        playerRank.setWinCount(rs.getInt("wins"));
        playerRank.setRank(rs.getInt("rank"));
        playerRank.setWinPercentage((double)playerRank.getWinCount() * 100 / playerRank.getTotalGames());
        return playerRank;
    }
}
