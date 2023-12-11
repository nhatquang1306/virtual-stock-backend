package com.techelevator.dao;

import com.techelevator.Application;
import com.techelevator.model.Stock;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class JdbcStockDao implements StockDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcStockDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void updateStockInfo(List<Stock> stocks) {
        LocalDate date = LocalDate.now();
        if (date.getDayOfWeek().getValue() == 1) {
            date = date.minusDays(3);
            // Sunday dayOfWeek value is 7
        } else if (date.getDayOfWeek().getValue() == 7) {
            date = date.minusDays(2);
        } else {
            date = date.minusDays(1);
        }
        String deleteSql = "DELETE FROM stock;";
        String addSql = "INSERT INTO stock (ticker, close_price, open_price, highest_price, " +
                "lowest_price, view_date, day_trading_volume, weighted_average_price) VALUES " +
                "(?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            jdbcTemplate.update(deleteSql);
            List<Object[]> list = new ArrayList<>();
            for (Stock stock : stocks) {
                Object[] object = new Object[] {
                        stock.getTicker(),
                        stock.getClosePrice(),
                        stock.getOpenPrice(),
                        stock.getHighestPrice(),
                        stock.getLowestPrice(),
                        date,
                        stock.getDayTradingVolume(),
                        stock.getWeightedAverage(),
                };
                list.add(object);
            }
            jdbcTemplate.batchUpdate(addSql, list);
        } catch (CannotGetJdbcConnectionException e) {
            throw new RuntimeException("Unable to connect to server or database", e);
        } catch (BadSqlGrammarException e) {
            throw new RuntimeException("SQL syntax error", e);
        }
    }

    @Override
    public List<Stock> getAllStocks() {
        int hour = LocalTime.now().getHour();
        int minute = LocalTime.now().getMinute() / 5;
        List<Stock> stocks = new ArrayList<>();
        String sql = "SELECT stock.ticker, close_price, open_price, highest_price, lowest_price, view_date, day_trading_volume, weighted_average_price, stock_company_name " +
                "FROM stock JOIN stock_ticker USING (ticker) ORDER BY (ticker);";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
        Random rand = new Random();
        while (result.next()) {
            Stock stock = mapRowsToStock(result);
            rand.setSeed(generateSeed(stock.getTicker(), hour * 12 + minute));
            stock.setName(result.getString("stock_company_name"));
            BigDecimal diff = stock.getHighestPrice().subtract(stock.getLowestPrice());
            stock.setCurrentPrice(stock.getLowestPrice().add(diff.multiply(BigDecimal.valueOf(rand.nextDouble()))));
            stocks.add(stock);
        }
        return stocks;
    }

    @Override
    public Stock getStockByTicker(String ticker) {
        int hour = LocalTime.now().getHour();
        int minute = LocalTime.now().getMinute() / 5;
        Stock stock = null;
        String sql = "SELECT stock.ticker, close_price, open_price, highest_price, lowest_price, view_date, day_trading_volume, weighted_average_price, stock_company_name " +
                "FROM stock JOIN stock_ticker USING (ticker) WHERE ticker = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, ticker);
        Random rand = new Random();
        if (result.next()) {
            stock = mapRowsToStock(result);
            rand.setSeed(generateSeed(ticker, hour * 12 + minute));
            stock.setName(result.getString("stock_company_name"));
            BigDecimal diff = stock.getHighestPrice().subtract(stock.getLowestPrice());
            stock.setCurrentPrice(stock.getLowestPrice().add(diff.multiply(BigDecimal.valueOf(rand.nextDouble()))));
        }
        return stock;
    }
    private int generateSeed(String ticker, int time) {
        int seed = Application.randomSeed + time;
        for (char c : ticker.toCharArray()) {
            seed += c;
        }
        return seed;
    }


    private Stock mapRowsToStock(SqlRowSet rs) {
        Stock stock = new Stock();
        stock.setTicker(rs.getString("ticker"));
        stock.setClosePrice(rs.getBigDecimal("close_price"));
        stock.setOpenPrice(rs.getBigDecimal("open_price"));
        stock.setHighestPrice(rs.getBigDecimal("highest_price"));
        stock.setLowestPrice(rs.getBigDecimal("lowest_price"));
        stock.setViewDate(rs.getTimestamp("view_date").toLocalDateTime().toLocalDate());
        stock.setDayTradingVolume(rs.getDouble("day_trading_volume"));
        stock.setWeightedAverage(rs.getBigDecimal("weighted_average_price"));
        return stock;
    }
}
