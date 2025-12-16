package com.banking.transactions_api.repository;

import com.banking.transactions_api.model.Account;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

// Repository layer for managing account data in memory
// Uses ConcurrentHashMap for thread-safety
@Repository
public class AccountRepository {
    
    private final Map<String, Account> accounts = new ConcurrentHashMap<>();

    // Create/Update an account
    public Account save(Account account) {
        accounts.put(account.getId(), account);
        return account;
    }

    // Find an account by ID
    public Optional<Account> findById(String id) {
        return Optional.ofNullable(accounts.get(id));
    }

    // Find all accounts
    public List<Account> findAll() {
        return new ArrayList<>(accounts.values());
    }
}