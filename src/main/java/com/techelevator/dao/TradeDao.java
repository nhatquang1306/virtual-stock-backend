package com.techelevator.dao;

import com.techelevator.model.Trade;

import java.math.BigDecimal;
import java.util.List;

public interface TradeDao {
    List<Trade> getTradesForUser(String username, int gameId);
    Trade getTradeById(int tradeId);
    Trade buyStock(String username, int gameId, Trade trade);
    Trade sellStock(String username, int gameId, Trade trade);

}
