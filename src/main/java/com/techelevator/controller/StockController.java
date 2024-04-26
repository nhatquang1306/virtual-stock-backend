package com.techelevator.controller;

import com.techelevator.Application;
import com.techelevator.dao.StockDao;
import com.techelevator.dao.TickerDao;
import com.techelevator.model.Graph;
import com.techelevator.model.Stock;
import com.techelevator.service.StockAPIService;
import com.techelevator.service.TickerAPIService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin
public class StockController {

    StockAPIService service;
    StockDao stockDao;

    public StockController(StockAPIService service, StockDao stockDao) {
        this.service = service;
        this.stockDao = stockDao;
    }

    // get all stocks in the database
    @GetMapping(value = "/stocks")
    public List<Stock> getAllStocks() {
        return stockDao.getAllStocks();
    }

    // get stock by ticker
    @GetMapping(value = "/stocks/{ticker}")
    public Stock getStockByTicker(@PathVariable String ticker) {
        return stockDao.getStockByTicker(ticker);
    }

    // get stock information for a specific date
    @GetMapping(value = "/stocks/{ticker}/{date}")
    public Graph getStockByDate(@PathVariable String ticker, @PathVariable String date) {
        return service.getStockForDate(LocalDate.parse(date), ticker);
    }

    // reset the stock database
    @RequestMapping(value = "stocks/reset/", method = RequestMethod.POST)
    public void resetDatabase(Principal principal) {
        // regenerate a random master seed when database is reset
        Application.randomSeed = (int)(Math.random() * Integer.MAX_VALUE / 2);
        if (principal.getName().equals("quang") || principal.getName().equals("james") || principal.getName().equals("nick")) {
            stockDao.updateStockInfo(service.getStocks(LocalDate.now()));
        }
    }


}
