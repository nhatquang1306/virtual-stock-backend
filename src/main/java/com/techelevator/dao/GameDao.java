package com.techelevator.dao;

import com.techelevator.model.Game;
import com.techelevator.model.PlayerRank;
import com.techelevator.model.UserWithBalance;

import java.math.BigDecimal;
import java.util.List;

public interface GameDao {
    List<Game> getAllGames();
    Game getGameById(int gameId);
    List<Integer> getGamesForUser(String username);
    List<String> getUsersForGame(int gameId);
    List<UserWithBalance> getLeaderboard(int gameId);
    BigDecimal getUserBalance(String username, int gameId);
    Game createGame(String username, Game game);
    void joinGame(String username, int gameId);
    void leaveGame(String username, int gameId);
    String setWinner(int gameId);
    List<PlayerRank> getGlobalLeaderboard();

}
