package com.techelevator.dao;

import com.techelevator.model.Portfolio;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
@Component

public class JdbcPortfolioDao implements PortfolioDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcPortfolioDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<Portfolio> viewCurrentStocks(String username, int gameId) {
        int hour = LocalTime.now().getHour();
        int minute = LocalTime.now().getMinute() >= 30 ? 1 : 0;
        List<Portfolio> portfolios = new ArrayList<>();
        String sql = "SELECT portfolio_id, user_id, game_id, stock_ticker, stock_quantity, trade_price, prices[" + (hour * 2 + minute + 1) + "] " +
                "FROM portfolio JOIN stock ON stock.ticker = portfolio.stock_ticker " +
                "WHERE user_id = (SELECT user_id FROM users WHERE username = ?) AND game_id = ? AND stock_quantity > 0";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, username, gameId);
        while (result.next()) {
            Portfolio portfolio = mapRowsToPortfolio(result);
            portfolio.setCurrentPrice(result.getBigDecimal("prices"));
            portfolios.add(portfolio);
        }
        return portfolios;
    }

    @Override
    public BigDecimal getQuantityOwned(String ticker, String username, int gameId) {
        String sql = "SELECT stock_quantity FROM portfolio WHERE stock_ticker = ? AND game_id = ? AND user_id = (SELECT user_id FROM users WHERE username = ?)";
        try {
            return jdbcTemplate.queryForObject(sql, BigDecimal.class, ticker, gameId, username);
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("Stock not currently owned", e);
        }
    }

    private Portfolio mapRowsToPortfolio (SqlRowSet rs) {
        Portfolio portfolio = new Portfolio();
        portfolio.setPortfolioId(rs.getInt("portfolio_id"));
        portfolio.setUserId(rs.getInt("user_id"));
        portfolio.setGameId(rs.getInt("game_id"));
        portfolio.setTicker(rs.getString("stock_ticker"));
        portfolio.setQuantity(rs.getBigDecimal("stock_quantity"));
        portfolio.setTradePrice(rs.getBigDecimal("trade_price"));
        return portfolio;
    }
}
