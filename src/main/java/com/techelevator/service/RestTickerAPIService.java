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
            if (jsonNode.has("next_url") && this.i >= 4) {
                Thread.sleep(60000);
                this.i = 0;
                this.url = jsonNode.path("next_url").asText();
                getAllTickers(this.url);
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

    @Override
    public List<Ticker> resetTickers() {
        return getAllTickers(resourceBundle.getString("ticker.api.first.search.url"));
    }


}
