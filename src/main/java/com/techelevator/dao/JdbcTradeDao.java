package com.techelevator.dao;

import com.techelevator.model.Trade;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Component
public class JdbcTradeDao implements TradeDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTradeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // get all trades of a user for a game
    @Override
    public List<Trade> getTradesForUser(String username, int gameId) {
        List<Trade> trades = new ArrayList<>();
        String sql = "SELECT * FROM stock_trade WHERE user_id = (SELECT user_id FROM users WHERE username = ?) AND game_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, username, gameId);
        while (result.next()) {
            trades.add(mapRowToTrade(result));
        }
        return trades;
    }

    // get trade by trade id
    @Override
    public Trade getTradeById(int tradeId) {
        String sql = "SELECT * FROM stock_trade WHERE trade_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, tradeId);
        if (result.next()) {
            return mapRowToTrade(result);
        }
        return null;
    }

    // let a user buy stock in a game and update their balance and portfolio
    @Override
    public Trade buyStock(String username, int gameId, Trade trade) {
        String idSql = "SELECT user_id FROM users WHERE username = ?";
        int userId = jdbcTemplate.queryForObject(idSql, int.class, username);
        String tradeSql = "INSERT INTO stock_trade (user_id, game_id, trade_date, stock_ticker, trade_quantity, stock_trade_price, trade_type) VALUES (?, ?, ?, ?, ?, ?, 'Buy') RETURNING trade_id";
        int id = jdbcTemplate.queryForObject(tradeSql, int.class, userId, gameId, trade.getDate(), trade.getTicker(), trade.getQuantity(), trade.getPrice());
        String balanceSql = "UPDATE user_game SET game_balance = game_balance - ? WHERE user_id = ? AND game_id = ?";
        jdbcTemplate.update(balanceSql, trade.getPrice().multiply(trade.getQuantity()), userId, gameId);
        String portfolioSql = "INSERT INTO portfolio (user_id, game_id, stock_ticker, stock_quantity, trade_price) VALUES (?, ?, ?, ?, ?) " +
                "ON CONFLICT(user_id, game_id, stock_ticker) DO " +
                "UPDATE SET stock_quantity = portfolio.stock_quantity + ?, trade_price = ?";
        jdbcTemplate.update(portfolioSql, userId, gameId, trade.getTicker(), trade.getQuantity(), trade.getPrice(), trade.getQuantity(), trade.getPrice());
        return getTradeById(id);
    }

    // let a user sell stock in a game and update their balance and portfolio
    @Override
    public Trade sellStock(String username, int gameId, Trade trade, boolean all) {
        String idSql = "SELECT user_id FROM users WHERE username = ?";
        int userId = jdbcTemplate.queryForObject(idSql, int.class, username);
        String tradeSql = "INSERT INTO stock_trade (user_id, game_id, trade_date, stock_ticker, trade_quantity, stock_trade_price, trade_type) VALUES (?, ?, ?, ?, ?, ?, 'Sell') RETURNING trade_id";
        int id = jdbcTemplate.queryForObject(tradeSql, int.class, userId, gameId, trade.getDate(), trade.getTicker(), trade.getQuantity(), trade.getPrice());
        String balanceSql = "UPDATE user_game SET game_balance = game_balance + ? WHERE user_id = ? AND game_id = ?";
        jdbcTemplate.update(balanceSql, trade.getPrice().multiply(trade.getQuantity()), userId, gameId);
        if (all) {
            String portfolioSql = "UPDATE portfolio SET stock_quantity = 0 WHERE user_id = ? AND game_id = ? AND stock_ticker = ?";
            jdbcTemplate.update(portfolioSql, userId, gameId, trade.getTicker());
        } else {
            String portfolioSql = "UPDATE portfolio SET stock_quantity = stock_quantity - ? WHERE user_id = ? AND game_id = ? AND stock_ticker = ?";
            jdbcTemplate.update(portfolioSql, trade.getQuantity(), userId, gameId, trade.getTicker());
        }
        return getTradeById(id);
    }
    private Trade mapRowToTrade(SqlRowSet rs) {
        Trade trade = new Trade();
        trade.setTradeId(rs.getInt("trade_id"));
        trade.setDate(rs.getTimestamp("trade_date"));
        trade.setUserId(rs.getInt("user_id"));
        trade.setGameId(rs.getInt("game_id"));
        trade.setPrice(rs.getBigDecimal("stock_trade_price"));
        trade.setType(rs.getString("trade_type"));
        trade.setQuantity(rs.getBigDecimal("trade_quantity"));
        trade.setTicker(rs.getString("stock_ticker"));
        return trade;
    }
}
