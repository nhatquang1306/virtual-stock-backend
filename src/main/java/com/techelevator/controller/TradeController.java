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

    // get all trades of user in a certain game
    @GetMapping(value = "/games/{id}/trades")
    public List<Trade> getTradesForUser(@PathVariable int id, Principal principal) {
        return tradeDao.getTradesForUser(principal.getName(), id);
    }

    // buy stock in a certain game
    @RequestMapping(value = "/games/{id}/buy", method = RequestMethod.POST)
    public Trade buyStock(Principal principal, @PathVariable int id, @RequestBody Trade trade) {
        return tradeDao.buyStock(principal.getName(), id, trade);
    }

    // sell stock in a certain game, can specify sell all
    @RequestMapping(value = "/games/{id}/sell/{all}", method = RequestMethod.POST)
    public Trade sellStock(Principal principal, @PathVariable int id, @RequestBody Trade trade, @PathVariable boolean all) {
        return tradeDao.sellStock(principal.getName(), id, trade, all);
    }

}
