package com.banking.transactions_api.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// Account entity representing a user's bank account
public class Account {
    private String id;
    private String accountHolderName;
    private BigDecimal balance;
    private LocalDateTime createdAt;
    private List<Transaction> transactions;

    public Account() {
        this.id = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.transactions = new ArrayList<>();
    }

    public Account(String accountHolderName, BigDecimal initialBalance) {
        this();
        this.accountHolderName = accountHolderName;
        this.balance = initialBalance;
    }

    // Getters and Setters
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

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }
}