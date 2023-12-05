package com.techelevator.dao;

import com.techelevator.model.Stock;

import java.util.List;

public interface StockDao {

    void updateStockInfo(List<Stock> stocks);
    List<Stock> getAllStocks();
    Stock getStockByTicker(String ticker);


}
