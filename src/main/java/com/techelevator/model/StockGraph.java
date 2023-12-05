package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

public class StockGraph {
    @JsonProperty("results")
    List<Point> points;


    public StockGraph() {
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "StockGraph{" +
                "points=" + points +
                '}';
    }
    public static class Point {
        @JsonProperty("v")
        private long volume;
        @JsonProperty("o")
        private BigDecimal open;
        @JsonProperty("c")
        private BigDecimal close;
        @JsonProperty("h")
        private BigDecimal high;
        @JsonProperty("l")
        private BigDecimal low;

        public Point() {
        }

        public long getVolume() {
            return volume;
        }

        public void setVolume(long volume) {
            this.volume = volume;
        }

        public BigDecimal getOpen() {
            return open;
        }

        public void setOpen(BigDecimal open) {
            this.open = open;
        }

        public BigDecimal getClose() {
            return close;
        }

        public void setClose(BigDecimal close) {
            this.close = close;
        }

        public BigDecimal getHigh() {
            return high;
        }

        public void setHigh(BigDecimal high) {
            this.high = high;
        }

        public BigDecimal getLow() {
            return low;
        }

        public void setLow(BigDecimal low) {
            this.low = low;
        }

        @Override
        public String toString() {
            return "Point{" +
                    "volume=" + volume +
                    ", open=" + open +
                    ", close=" + close +
                    ", high=" + high +
                    ", low=" + low +
                    '}';
        }
    }

}
