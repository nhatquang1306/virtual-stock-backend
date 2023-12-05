package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class Portfolio {
    @JsonProperty("portfolio_id")
    private int portfolioId;
    @JsonProperty("user_id")
    private int userId;
    @JsonProperty("game_id")
    private int gameId;
    @JsonProperty("stock_ticker")
    private String ticker;
    @JsonProperty("stock_quantity")
    private BigDecimal quantity;
    @JsonProperty("current_price")
    private BigDecimal currentPrice;
    @JsonProperty("trade_price")
    private BigDecimal tradePrice;

    public Portfolio() {
    }

    public Portfolio(int portfolioId, int userId, int gameId, String ticker, BigDecimal quantity) {
        this.portfolioId = portfolioId;
        this.userId = userId;
        this.gameId = gameId;
        this.ticker = ticker;
        this.quantity = quantity;
    }

    public BigDecimal getTradePrice() {
        return tradePrice;
    }

    public void setTradePrice(BigDecimal tradePrice) {
        this.tradePrice = tradePrice;
    }

    public int getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(int portfolioId) {
        this.portfolioId = portfolioId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }
}