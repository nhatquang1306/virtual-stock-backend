package com.techelevator.controller;

import com.techelevator.dao.PortfolioDao;
import com.techelevator.model.Portfolio;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
public class PortfolioController {
    PortfolioDao portfolioDao;
    public PortfolioController(PortfolioDao portfolioDao) {
        this.portfolioDao = portfolioDao;
    }

    // get all currently owned stocks
    @GetMapping(value = "/games/{id}/stocks")
    public List<Portfolio> viewCurrentStocks(@PathVariable int id, Principal principal) {
        return portfolioDao.viewCurrentStocks(principal.getName(), id);
    }

    // get the quantity owned of a specific stock
    @GetMapping(value = "/games/{id}/{ticker}/owned")
    public BigDecimal getQuantityOwned(@PathVariable int id, @PathVariable String ticker, Principal principal) {
        return portfolioDao.getQuantityOwned(ticker, principal.getName(), id);
    }
}
