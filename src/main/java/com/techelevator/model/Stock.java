package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

public class Stock {

    @JsonProperty("T")
    private String ticker;
    @JsonProperty("c")
    private BigDecimal closePrice;
    @JsonProperty("o")
    private BigDecimal openPrice;
    @JsonProperty("h")
    private BigDecimal highestPrice;
    @JsonProperty("l")
    private BigDecimal lowestPrice;
    @JsonProperty("from")
    private LocalDate viewDate;
    @JsonProperty("v")
    private double dayTradingVolume;
    @JsonProperty("vw")
    private BigDecimal weightedAverage;
    @JsonProperty("prices")
    private BigDecimal[] prices;
    @JsonProperty("current_price")
    private BigDecimal currentPrice;
    private String name;

    public Stock() {
    }

    public Stock(String ticker, BigDecimal closePrice, BigDecimal openPrice, BigDecimal highestPrice, BigDecimal lowestPrice, LocalDate viewDate, double dayTradingVolume, BigDecimal weightedAverage, BigDecimal[] prices) {
        this.ticker = ticker;
        this.closePrice = closePrice;
        this.openPrice = openPrice;
        this.highestPrice = highestPrice;
        this.lowestPrice = lowestPrice;
        this.viewDate = viewDate;
        this.dayTradingVolume = dayTradingVolume;
        this.weightedAverage = weightedAverage;
        this.prices = prices;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public BigDecimal getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(BigDecimal closePrice) {
        this.closePrice = closePrice;
    }

    public BigDecimal getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(BigDecimal openPrice) {
        this.openPrice = openPrice;
    }

    public BigDecimal getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(BigDecimal highestPrice) {
        this.highestPrice = highestPrice;
    }

    public BigDecimal getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(BigDecimal lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public LocalDate getViewDate() {
        return viewDate;
    }

    public void setViewDate(LocalDate viewDate) {
        this.viewDate = viewDate;
    }

    public double getDayTradingVolume() {
        return dayTradingVolume;
    }

    public void setDayTradingVolume(double dayTradingVolume) {
        this.dayTradingVolume = dayTradingVolume;
    }

    public BigDecimal getWeightedAverage() {
        return weightedAverage;
    }

    public void setWeightedAverage(BigDecimal weightedAverage) {
        this.weightedAverage = weightedAverage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal[] getPrices() {
        return prices;
    }

    public void setPrices(BigDecimal[] prices) {
        this.prices = prices;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "ticker='" + ticker + '\'' +
                ", closePrice=" + closePrice +
                ", openPrice=" + openPrice +
                ", highestPrice=" + highestPrice +
                ", lowestPrice=" + lowestPrice +
                ", viewDate=" + viewDate +
                ", dayTradingVolume=" + dayTradingVolume +
                ", weightedAverage=" + weightedAverage +
                '}';
    }
}
