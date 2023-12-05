package com.techelevator.service;

import com.techelevator.model.Graph;
import com.techelevator.model.Stock;
import com.techelevator.model.StockGraph;

import java.time.LocalDate;
import java.util.List;

public interface StockAPIService {

    List<Stock> getStocks(LocalDate date);
    Graph getStockForDate(LocalDate date, String ticker);

}
