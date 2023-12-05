package com.techelevator.dao;

import com.techelevator.model.Portfolio;

import java.math.BigDecimal;
import java.util.List;

public interface PortfolioDao {
    List<Portfolio> viewCurrentStocks(String username, int gameId);
    BigDecimal getQuantityOwned(String ticker, String username, int gameId);
}
