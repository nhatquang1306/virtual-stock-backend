package com.techelevator.dao;

import com.techelevator.model.Stock;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcStockDao implements StockDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcStockDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void updateStockInfo(List<Stock> stocks) {
        System.out.println(stocks.size());
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
                "lowest_price, view_date, day_trading_volume, weighted_average_price, prices) VALUES " +
                "(?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            Connection connection = jdbcTemplate.getDataSource().getConnection();
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
                        connection.createArrayOf("numeric", stock.getPrices())
                };
                list.add(object);
            }
            jdbcTemplate.batchUpdate(addSql, list);
        } catch (CannotGetJdbcConnectionException e) {
            throw new RuntimeException("Unable to connect to server or database", e);
        } catch (BadSqlGrammarException e) {
            throw new RuntimeException("SQL syntax error", e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Stock> getAllStocks() {
        int hour = LocalTime.now().getHour();
        int minute = LocalTime.now().getMinute() >= 30 ? 1 : 0;
        List<Stock> stocks = new ArrayList<>();
        String sql = "SELECT stock.ticker, close_price, open_price, highest_price, lowest_price, view_date, day_trading_volume, weighted_average_price, stock_company_name, prices[" + (hour * 2 + minute + 1) + "] " +
                "FROM stock JOIN stock_ticker USING (ticker) ORDER BY (ticker);";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
        while (result.next()) {
            Stock stock = mapRowsToStock(result);
            stock.setName(result.getString("stock_company_name"));
            stock.setCurrentPrice(result.getBigDecimal("prices"));
            stocks.add(stock);
        }
        return stocks;
    }

    @Override
    public Stock getStockByTicker(String ticker) {
        int hour = LocalTime.now().getHour();
        int minute = LocalTime.now().getMinute() >= 30 ? 1 : 0;
        Stock stock = null;
        String sql = "SELECT stock.ticker, close_price, open_price, highest_price, lowest_price, view_date, day_trading_volume, weighted_average_price, stock_company_name, prices[" + (hour * 2 + minute + 1) + "] " +
                "FROM stock JOIN stock_ticker USING (ticker) WHERE ticker = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, ticker);
        if (result.next()) {
            stock = mapRowsToStock(result);
            stock.setName(result.getString("stock_company_name"));
            stock.setCurrentPrice(result.getBigDecimal("prices"));
        }
        return stock;
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
