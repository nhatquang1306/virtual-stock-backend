package com.techelevator.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techelevator.dao.JdbcStockDao;
import com.techelevator.dao.StockDao;
import com.techelevator.model.Graph;
import com.techelevator.model.Stock;
import com.techelevator.model.StockGraph;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

// Connects through SpringBoot so that Spring knows that this is the class to run when we create a StockAPIService object
@Service
public class RestStockAPIService implements StockAPIService {

    RestTemplate restTemplate;
    ResourceBundle resourceBundle;
    String apiKey;
    String token;
    public RestStockAPIService() {
        this.restTemplate = new RestTemplate();
        resourceBundle = ResourceBundle.getBundle("application");
        apiKey = resourceBundle.getString("stock.api.key");
        token = "Bearer " + apiKey;
    }





    @Override
    public List<Stock> getStocks(LocalDate date) {
        // calculate the previous date as today's stock information is not available, go to friday if today is sunday or monday
        // Monday dayOfWeek value is 1
        if (date.getDayOfWeek().getValue() == 1) {
            date = date.minusDays(3);
        // Sunday dayOfWeek value is 7
        } else if (date.getDayOfWeek().getValue() == 7) {
            date = date.minusDays(2);
        } else {
            date = date.minusDays(1);
        }
        // call the api to get all stock information
        String url = "https://api.polygon.io/v2/aggs/grouped/locale/us/market/stocks/" + date + "?adjusted=true";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        List<Stock> listOfStocks = new ArrayList<>();
        try {
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            for(JsonNode element : jsonNode.path("results")) {
                String ticker = element.path("T").asText();
                String close = element.path("c").asText();
                String open = element.path("o").asText();
                String highest = element.path("h").asText();
                String lowest = element.path("l").asText();
                String dayTradingVolume = element.path("v").asText();
                String weightedAverage;
                if (element.has("vw")) {
                    weightedAverage = element.path("vw").asText();
                } else {
                    weightedAverage = "0";
                }

                BigDecimal closePrice = new BigDecimal(close);
                BigDecimal openPrice = new BigDecimal(open);
                BigDecimal highestPrice = new BigDecimal(highest);
                BigDecimal lowestPrice = new BigDecimal(lowest);
                BigDecimal weightedAveragePrice = new BigDecimal(weightedAverage);
                Stock stock = new Stock(ticker, closePrice, openPrice, highestPrice, lowestPrice, date, Double.parseDouble(dayTradingVolume), weightedAveragePrice);
                listOfStocks.add(stock);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return listOfStocks;
    }

    // call the api to get stock information for a certain date
    @Override
    public Graph getStockForDate(LocalDate date, String ticker) {
        StringBuilder sb = new StringBuilder();
        sb.append("https://api.polygon.io/v2/aggs/ticker/").append(ticker).append("/range/30/minute/").append(date).append("/").append(date).append("?adjusted=true&sort=asc&limit=120");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<StockGraph> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<StockGraph> response = restTemplate.exchange(sb.toString(), HttpMethod.GET, httpEntity, StockGraph.class);
        // build price chart with stock information
        Graph graph = new Graph();
        graph.buildGraph(response.getBody());
        return graph;
    }


}
