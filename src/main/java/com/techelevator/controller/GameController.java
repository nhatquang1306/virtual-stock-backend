package com.techelevator.controller;

import com.techelevator.model.Game;
import com.techelevator.model.PlayerRank;
import com.techelevator.model.UserWithBalance;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.techelevator.dao.GameDao;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
public class GameController {
    private GameDao gameDao;

    public GameController(GameDao gameDao) {
        this.gameDao = gameDao;
    }

    // get all games in the database
    @GetMapping(value = "/games")
    public List<Game> getAllGames() {
        return gameDao.getAllGames();
    }

    // get game by id
    @GetMapping(value = "/games/{id}")
    public Game getGameById(@PathVariable int id) {
        return gameDao.getGameById(id);
    }

    // get games that the user participates in
    @GetMapping(value = "/games/participating")
    public List<Integer> getGamesForUser(Principal principal) {
        return gameDao.getGamesForUser(principal.getName());
    }

    // get list of users for a game
    @GetMapping(value = "/games/{id}/users")
    public List<String> getUsersForGame(@PathVariable int id) {
        return gameDao.getUsersForGame(id);
    }

    // get leaderboard for a game
    @GetMapping(value = "/games/{id}/leaderboard")
    public List<UserWithBalance> getLeaderboard(@PathVariable int id) {
        return gameDao.getLeaderboard(id);
    }

    // get user balance for a game
    @GetMapping(value = "/games/{gameId}/balance")
    public BigDecimal getUserBalance(@PathVariable int gameId, Principal principal) {
        return gameDao.getUserBalance(principal.getName(), gameId);
    }

    // create a game
    @RequestMapping(value = "/games/create", method = RequestMethod.POST)
    public Game createGame(Principal principal, @RequestBody Game game) {
        Game newGame;
        try {
            newGame = gameDao.createGame(principal.getName(), game);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return newGame;
    }

    // join a game
    @RequestMapping(value = "/games/{gameId}/join", method = RequestMethod.POST)
    public void joinGame(@PathVariable int gameId, Principal principal) {
        try {
            gameDao.joinGame(principal.getName(), gameId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    // leave a game
    @RequestMapping(value = "/games/{gameId}/leave", method = RequestMethod.DELETE)
    public void leaveGame(@PathVariable int gameId, Principal principal) {
        gameDao.leaveGame(principal.getName(), gameId);
    }

    // set the winner of a game
    @RequestMapping(value = "/games/{gameId}/winner", method = RequestMethod.POST)
    public String setWinner(@PathVariable int gameId) {
        return gameDao.setWinner(gameId);
    }

    // get global leaderboard
    @GetMapping(value = "/leaderboard")
    public List<PlayerRank> getGlobalLeaderboard() {
        return gameDao.getGlobalLeaderboard();
    }
}
