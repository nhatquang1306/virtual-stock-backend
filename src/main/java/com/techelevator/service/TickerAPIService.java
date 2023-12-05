package com.techelevator.service;

import com.techelevator.model.Ticker;

import java.util.List;

public interface TickerAPIService {

    List<Ticker> getAllTickers(String url);
    List<Ticker> resetTickers();

}
