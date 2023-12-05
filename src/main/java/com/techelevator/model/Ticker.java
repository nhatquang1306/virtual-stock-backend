package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Ticker {


    private String ticker;
    @JsonProperty("name")
    private String stockName;
    private String type;

    public Ticker(String ticker, String stockName, String type) {
        this.ticker = ticker;
        this.stockName = stockName;
        this.type = type;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Ticker{" +
                "ticker='" + ticker + '\'' +
                ", stockName='" + stockName + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
