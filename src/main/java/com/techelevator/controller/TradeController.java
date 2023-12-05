package com.techelevator.controller;

import com.techelevator.dao.TradeDao;
import com.techelevator.model.Trade;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
public class TradeController {
    private TradeDao tradeDao;
    private TradeController(TradeDao tradeDao) {
        this.tradeDao = tradeDao;
    }
    @GetMapping(value = "/games/{id}/trades")
    public List<Trade> getTradesForUser(@PathVariable int id, Principal principal) {
        return tradeDao.getTradesForUser(principal.getName(), id);
    }
    @RequestMapping(value = "/games/{id}/buy", method = RequestMethod.POST)
    public Trade buyStock(Principal principal, @PathVariable int id, @RequestBody Trade trade) {
        return tradeDao.buyStock(principal.getName(), id, trade);
    }
    @RequestMapping(value = "/games/{id}/sell", method = RequestMethod.POST)
    public Trade sellStock(Principal principal, @PathVariable int id, @RequestBody Trade trade) {
        return tradeDao.sellStock(principal.getName(), id, trade);
    }

}
