package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Trade {
    @JsonProperty("trade_id")
    private int tradeId;
    @JsonProperty("trade_date")
    private Timestamp date;
    @JsonProperty("user_id")
    private int userId;
    @JsonProperty("game_id")
    private int gameId;
    @JsonProperty("stock_ticker")
    private String ticker;
    @JsonProperty("trade_type")
    private String type;
    @JsonProperty("trade_quantity")
    private BigDecimal quantity;
    @JsonProperty("stock_trade_price")
    private BigDecimal price;

    public Trade() {
    }

    public Trade(int tradeId, Timestamp date, int userId, int gameId, String ticker, String type, BigDecimal quantity, BigDecimal price) {
        this.tradeId = tradeId;
        this.date = date;
        this.userId = userId;
        this.gameId = gameId;
        this.ticker = ticker;
        this.type = type;
        this.quantity = quantity;
        this.price = price;
    }

    public int getTradeId() {
        return tradeId;
    }

    public void setTradeId(int tradeId) {
        this.tradeId = tradeId;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}