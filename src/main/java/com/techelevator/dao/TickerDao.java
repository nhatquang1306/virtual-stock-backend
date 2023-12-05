package com.techelevator.dao;

import com.techelevator.model.Ticker;
import java.util.List;

public interface TickerDao {

    void updateTickerInfo(List<Ticker> tickers);

}
