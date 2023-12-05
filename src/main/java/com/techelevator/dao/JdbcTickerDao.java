package com.techelevator.dao;

import com.techelevator.model.Stock;
import com.techelevator.model.Ticker;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTickerDao implements TickerDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcTickerDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public void updateTickerInfo(List<Ticker> tickers) {
        String deleteSql = "DELETE FROM stock_ticker;";
        String addSql = "INSERT INTO stock_ticker (ticker, stock_company_name, stock_type) " +
                "VALUES (?, ?, ?);";
//        try {
//            jdbcTemplate.update(deleteSql);
//            for (Ticker ticker : tickers) {
//                jdbcTemplate.update(addSql, ticker.getTicker(), ticker.getStockName(), ticker.getType());
//            }
//        } catch (CannotGetJdbcConnectionException e) {
//            throw new RuntimeException("Unable to connect to server or database", e);
//        } catch (BadSqlGrammarException e) {
//            throw new RuntimeException("SQL syntax error", e);
//        }
        try {
            jdbcTemplate.update(deleteSql);
            List<Object[]> list = new ArrayList<>();
            for (Ticker ticker : tickers) {
                Object[] object = new Object[] {
                        ticker.getTicker(),
                        ticker.getStockName(),
                        ticker.getType()
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
}
