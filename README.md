# Banking-Transactions-API

A RESTful API for managing bank accounts and transactions, implement with Java and Spring Boot.

## Features

- Create accounts with initial balance
- Deposit and withdraw funds
- Transfer money between accounts
- View transaction history
- Input validation and error handling

## Tech Stack

- Java 25
- Spring Boot 4.0.0
- Maven

### Run the Application

```bash
# Clone the repository
git clone https://github.com/alec-deng/Banking-Transactions-API.git

# Run with Maven
mvn spring-boot:run
```

The API will start at `http://localhost:8080`

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/accounts` | Create account |
| POST | `/api/accounts/deposit` | Deposit funds |
| POST | `/api/accounts/withdraw` | Withdraw funds |
| POST | `/api/accounts/transfer` | Transfer between accounts |
| GET | `/api/accounts` | Get all accounts |
| GET | `/api/accounts/{accountId}` | Get account by ID |
| GET | `/api/accounts/{accountId}/transactions` | Get transaction history |

## Example Usage

### Create Account
```bash
curl -X POST http://localhost:8080/api/accounts \
  -H "Content-Type: application/json" \
  -d '{"accountHolderName": "Alex", "initialBalance": 1000.00}'
```

### Deposit Money
```bash
curl -X POST http://localhost:8080/api/accounts/deposit \
  -H "Content-Type: application/json" \
  -d '{"accountId": "YOUR_ACCOUNT_ID", "amount": 500.00, "description": "Deposit"}'
```

### Transfer Funds
```bash
curl -X POST http://localhost:8080/api/accounts/transfer \
  -H "Content-Type: application/json" \
  -d '{"fromAccountId": "ID_1", "toAccountId": "ID_2", "amount": 200.00, "description": "Payment"}'
```

### Show All Users
```bash
curl -X GET http://localhost:8080/api/accounts
```