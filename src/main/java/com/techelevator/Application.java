package com.techelevator;

import com.techelevator.dao.JdbcTickerDao;
import com.techelevator.dao.JdbcStockDao;
import com.techelevator.dao.TickerDao;
import com.techelevator.dao.StockDao;
import com.techelevator.service.RestStockAPIService;
import com.techelevator.service.RestTickerAPIService;
import com.techelevator.service.StockAPIService;
import com.techelevator.service.TickerAPIService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.time.LocalDate;
import java.util.ResourceBundle;

@SpringBootApplication
public class Application {
    public static int randomSeed;



    public static void main(String[] args) {
        randomSeed = (int)(Math.random() * Integer.MAX_VALUE / 2);
        SpringApplication.run(Application.class, args);
//        ResourceBundle resourceBundle = ResourceBundle.getBundle("application");
//        StockAPIService service = new RestStockAPIService();
//        LocalDate date = LocalDate.now();
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("org.postgresql.Driver");
//        dataSource.setUrl(resourceBundle.getString("spring.datasource.url"));
//        dataSource.setUsername(resourceBundle.getString("spring.datasource.username"));
//        dataSource.setPassword(resourceBundle.getString("spring.datasource.password"));
//
//        StockDao stockDao = new JdbcStockDao(new JdbcTemplate(dataSource));
//        stockDao.updateStockInfo(service.getStocks(date));

//        String tickerFirstUrl = resourceBundle.getString("ticker.api.first.search.url");
//        TickerAPIService tickerService = new RestTickerAPIService();
//
//        TickerDao tickerDao = new JdbcTickerDao(new JdbcTemplate(dataSource));
//        tickerDao.updateTickerInfo(tickerService.getAllTickers(tickerFirstUrl));
    }

}
