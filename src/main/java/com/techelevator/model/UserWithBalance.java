package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class UserWithBalance {
    @JsonProperty("game_balance")
    private BigDecimal balance;
    @JsonProperty("username")
    private String username;
    @JsonProperty("rank")
    private int rank;

    public UserWithBalance() {
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}