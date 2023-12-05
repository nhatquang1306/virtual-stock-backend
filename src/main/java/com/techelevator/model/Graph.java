package com.techelevator.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Graph {
    List<Double> graph;
    BigDecimal max;
    BigDecimal min;
    BigDecimal open;
    BigDecimal close;
    long volume;
    double distance;

    public Graph() {
    }

    public void buildGraph(StockGraph stockGraph) {
        graph = new ArrayList<>();
        List<StockGraph.Point> points = stockGraph.getPoints();
        max = points.get(0).getHigh();
        min = points.get(0).getLow();
        open = points.get(0).getOpen();
        close = points.get(points.size() - 1).getClose();
        volume = points.get(0).getVolume();
        distance = 1 / ((double)points.size() * 2 + 1);
        int indexMax = 0;
        int indexMin = 0;
        for (int i = 1; i < points.size(); i++) {
            if (max.compareTo(points.get(i).getHigh()) < 0) {
                max = points.get(i).getHigh();
                indexMax = i;
            }
            if (min.compareTo(points.get(i).getLow()) > 0) {
                min = points.get(i).getLow();
                indexMin = i;
            }
            volume += points.get(i).getVolume();
        }
        double high = max.doubleValue();
        double low = min.doubleValue();
        double step = (high - low) / 6;
        high += step / 2;
        low -= step / 2;
        for (int i = 0; i < points.size(); i++) {
            graph.add((points.get(i).getOpen().doubleValue() - low) / (high - low));
            if (i == indexMax) {
                graph.add(((high - step / 2) - low) / (high - low));
            }
            if (i == indexMin) {
                graph.add(step / (2 * (high - low)));
            }
            graph.add((points.get(i).getClose().doubleValue() - low) / (high - low));
        }
    }

    public List<Double> getGraph() {
        return graph;
    }

    public void setGraph(List<Double> graph) {
        this.graph = graph;
    }

    public BigDecimal getMax() {
        return max;
    }

    public void setMax(BigDecimal max) {
        this.max = max;
    }

    public BigDecimal getMin() {
        return min;
    }

    public void setMin(BigDecimal min) {
        this.min = min;
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

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
