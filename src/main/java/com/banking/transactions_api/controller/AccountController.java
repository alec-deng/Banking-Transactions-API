package com.banking.transactions_api.controller;

import com.banking.transactions_api.dto.*;
import com.banking.transactions_api.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for banking operations
 */
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    // Constructor injection
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // Create a new account
    // POST /api/accounts
    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(
            @Valid @RequestBody CreateAccountRequest request) {
        AccountResponse response = accountService.createAccount(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Transfer funds between accounts
    // POST /api/accounts/transfer
    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponse> transferFunds(
            @Valid @RequestBody TransferRequest request) {
        TransactionResponse response = accountService.transferFunds(request);
        return ResponseEntity.ok(response);
    }

    // Deposit funds into an account
    // POST /api/accounts/deposit
    @PostMapping("/deposit")
    public ResponseEntity<TransactionResponse> deposit(
            @Valid @RequestBody DepositRequest request) {
        TransactionResponse response = accountService.deposit(request);
        return ResponseEntity.ok(response);
    }

    // Withdraw funds from an account
    // POST /api/accounts/withdraw
    @PostMapping("/withdraw")
    public ResponseEntity<TransactionResponse> withdraw(
            @Valid @RequestBody WithdrawalRequest request) {
        TransactionResponse response = accountService.withdraw(request);
        return ResponseEntity.ok(response);
    }

    // Get transaction history for an account
    // GET /api/accounts/{accountId}/transactions
    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<List<TransactionResponse>> getTransactionHistory(
            @PathVariable String accountId) {
        List<TransactionResponse> transactions = 
            accountService.getTransactionHistory(accountId);
        return ResponseEntity.ok(transactions);
    }

    // Get account details by ID
    // GET /api/accounts/{accountId}
    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponse> getAccount(
            @PathVariable String accountId) {
        AccountResponse response = accountService.getAccountById(accountId);
        return ResponseEntity.ok(response);
    }

    // Get all accounts
    // GET /api/accounts
    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAllAccounts() {
        List<AccountResponse> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }
}