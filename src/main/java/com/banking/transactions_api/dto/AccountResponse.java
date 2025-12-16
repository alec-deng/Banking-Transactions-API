package com.banking.transactions_api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountResponse {
    @JsonProperty("accountId")
    private String id;
    private String accountHolderName;
    private BigDecimal balance;
    private LocalDateTime createdAt;

    public AccountResponse(String id, String accountHolderName, 
                          BigDecimal balance, LocalDateTime createdAt) {
        this.id = id;
        this.accountHolderName = accountHolderName;
        this.balance = balance;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}