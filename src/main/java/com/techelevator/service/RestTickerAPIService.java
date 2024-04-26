package com.techelevator.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techelevator.model.Ticker;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Service
public class RestTickerAPIService implements TickerAPIService {

    ResourceBundle resourceBundle = ResourceBundle.getBundle("application");
    String apiKey = resourceBundle.getString("stock.api.key");

    private String url;
    private int i = 0;
    private List<Ticker> allTickers = new ArrayList<>();

    RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<Ticker> getAllTickers(String url) {
        String token = "Bearer " + apiKey;
        this.url = url;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        // call the api to get ticker information, can only get 1000 at a time
        ResponseEntity<String> response = restTemplate.exchange(this.url, HttpMethod.GET, httpEntity, String.class);
        this.i++;
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            for (JsonNode element : jsonNode.path("results")) {
                String ticker = element.path("ticker").asText();
                String stockName = element.path("name").asText();
                String type = element.path("type").asText();

                Ticker nextTicker = new Ticker(ticker, stockName, type);
                allTickers.add(nextTicker);
            }
            // the api only allows 5 calls per minute, variable i indicates the number of calls made so far
            // if there are more tickers and 5 calls were made, sleep for 1 minute
            if (jsonNode.has("next_url") && this.i >= 4) {
                Thread.sleep(60000);
                this.i = 0;
                this.url = jsonNode.path("next_url").asText();
                getAllTickers(this.url);
            // if there are more tickers, call the api again and increment the number of calls
            } else if (jsonNode.has("next_url") && this.i <= 4) {
                this.url = jsonNode.path("next_url").asText();
                getAllTickers(this.url);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return allTickers;
    }

    // reset all tickers in the database
    @Override
    public List<Ticker> resetTickers() {
        return getAllTickers(resourceBundle.getString("ticker.api.first.search.url"));
    }


}
