package com.banking.transactions_api.service;

import com.banking.transactions_api.dto.*;
import com.banking.transactions_api.exception.*;
import com.banking.transactions_api.model.Account;
import com.banking.transactions_api.model.Transaction;
import com.banking.transactions_api.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

// Service layer handling logic for account operations
@Service
public class AccountService {

    private final AccountRepository accountRepository;

    // Constructor injection for dependency injection
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // Create a new account with initial balance
    public AccountResponse createAccount(CreateAccountRequest request) {
        Account account = new Account(
            request.getAccountHolderName(),
            request.getInitialBalance()
        );

        // Create initial deposit transaction
        if (request.getInitialBalance().compareTo(BigDecimal.ZERO) > 0) {
            Transaction initialDeposit = new Transaction(
                null,
                account.getId(),
                request.getInitialBalance(),
                Transaction.TransactionType.DEPOSIT,
                "Initial deposit"
            );
            account.addTransaction(initialDeposit);
        }

        Account savedAccount = accountRepository.save(account);
        return mapToAccountResponse(savedAccount);
    }

    // Transfer funds between accounts
    public synchronized TransactionResponse transferFunds(TransferRequest request) {
        // Validate accounts exist
        Account fromAccount = accountRepository.findById(request.getFromAccountId())
            .orElseThrow(() -> new AccountNotFoundException(request.getFromAccountId()));
        
        Account toAccount = accountRepository.findById(request.getToAccountId())
            .orElseThrow(() -> new AccountNotFoundException(request.getToAccountId()));

        // Validate same account transfer
        if (fromAccount.getId().equals(toAccount.getId())) {
            throw new InvalidTransactionException("Cannot transfer to the same account");
        }

        // Validate sufficient funds in fromAccount
        if (fromAccount.getBalance().compareTo(request.getAmount()) < 0) {
            throw new InsufficientFundsException(fromAccount.getId());
        }

        // Perform transfer
        fromAccount.setBalance(fromAccount.getBalance().subtract(request.getAmount()));
        toAccount.setBalance(toAccount.getBalance().add(request.getAmount()));

        // Create transaction record
        Transaction transaction = new Transaction(
            fromAccount.getId(),
            toAccount.getId(),
            request.getAmount(),
            Transaction.TransactionType.TRANSFER,
            request.getDescription() != null ? request.getDescription() : "Transfer"
        );

        // Add transaction to both accounts
        fromAccount.addTransaction(transaction);
        toAccount.addTransaction(transaction);

        // Save updated accounts
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        return mapToTransactionResponse(transaction);
    }

    // Deposit funds into an account
    public synchronized TransactionResponse deposit(DepositRequest request) {
        Account account = accountRepository.findById(request.getAccountId())
            .orElseThrow(() -> new AccountNotFoundException(request.getAccountId()));

        // Add funds to account
        account.setBalance(account.getBalance().add(request.getAmount()));

        // Create transaction record
        Transaction transaction = new Transaction(
            null,
            account.getId(),
            request.getAmount(),
            Transaction.TransactionType.DEPOSIT,
            request.getDescription() != null ? request.getDescription() : "Deposit"
        );

        // Add transaction to account
        account.addTransaction(transaction);

        // Save updated account
        accountRepository.save(account);

        return mapToTransactionResponse(transaction);
    }

    // Withdraw funds from an account
    public synchronized TransactionResponse withdraw(WithdrawalRequest request) {
        Account account = accountRepository.findById(request.getAccountId())
            .orElseThrow(() -> new AccountNotFoundException(request.getAccountId()));

        // Validate sufficient funds
        if (account.getBalance().compareTo(request.getAmount()) < 0) {
            throw new InsufficientFundsException(account.getId());
        }

        // Subtract funds from account
        account.setBalance(account.getBalance().subtract(request.getAmount()));

        // Create transaction record
        Transaction transaction = new Transaction(
            account.getId(),
            null,
            request.getAmount(),
            Transaction.TransactionType.WITHDRAWAL,
            request.getDescription() != null ? request.getDescription() : "Withdrawal"
        );

        // Add transaction to account
        account.addTransaction(transaction);

        // Save updated account
        accountRepository.save(account);

        return mapToTransactionResponse(transaction);
    }

    // Get transaction history for an account
    public List<TransactionResponse> getTransactionHistory(String accountId) {
        Account account = accountRepository.findById(accountId)
            .orElseThrow(() -> new AccountNotFoundException(accountId));

        return account.getTransactions().stream()
            .map(this::mapToTransactionResponse)
            .collect(Collectors.toList());
    }

    // Get account details by ID
    public AccountResponse getAccountById(String accountId) {
        Account account = accountRepository.findById(accountId)
            .orElseThrow(() -> new AccountNotFoundException(accountId));
        return mapToAccountResponse(account);
    }

    // Get all accounts
    public List<AccountResponse> getAllAccounts() {
        return accountRepository.findAll().stream()
            .map(this::mapToAccountResponse)
            .collect(Collectors.toList());
    }

    // Mapping methods
    private AccountResponse mapToAccountResponse(Account account) {
        return new AccountResponse(
            account.getId(),
            account.getAccountHolderName(),
            account.getBalance(),
            account.getCreatedAt()
        );
    }

    private TransactionResponse mapToTransactionResponse(Transaction transaction) {
        return new TransactionResponse(
            transaction.getId(),
            transaction.getFromAccountId(),
            transaction.getToAccountId(),
            transaction.getAmount(),
            transaction.getTimestamp(),
            transaction.getType().toString(),
            transaction.getDescription()
        );
    }
}