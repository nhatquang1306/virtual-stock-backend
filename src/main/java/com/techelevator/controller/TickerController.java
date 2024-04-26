package com.techelevator.controller;

import com.techelevator.dao.StockDao;
import com.techelevator.dao.TickerDao;
import com.techelevator.model.Ticker;
import com.techelevator.service.StockAPIService;
import com.techelevator.service.TickerAPIService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@CrossOrigin
public class TickerController {

    TickerAPIService service;
    TickerDao tickerDao;

    public TickerController(TickerAPIService service, TickerDao tickerDao) {
        this.service = service;
        this.tickerDao = tickerDao;
    }

    // reset the ticker database
    @RequestMapping(value = "/tickers/reset/", method = RequestMethod.POST)
    public void resetTickers(Principal principal) {
        if (principal.getName().equals("quang") || principal.getName().equals("james") || principal.getName().equals("nick")) {
            tickerDao.updateTickerInfo(service.resetTickers());
        }
    }
}
