# Banking System Simulator – Spring Boot & MongoDB

This project is a simple banking application built using Spring Boot.  
The goal is to simulate basic banking operations such as creating accounts, depositing money, withdrawing money, transferring funds between accounts, and viewing account transactions.  
MongoDB is used as the backend database to store account and transaction details.

---

## 1. Project Overview

The application exposes RESTful APIs that allow a client (like Postman) to interact with the banking system.  
The main idea is to apply a layered architecture and follow proper coding practices using **controller**, **service**, and **repository** layers.

The system handles common banking scenarios such as:

* Creating a bank account
* Fetching account details
* Adding funds
* Withdrawing funds (if the balance is sufficient)
* Transferring money between two accounts
* Viewing all transactions related to an account

It also includes **custom exception handling** and **logging** for major operations.

---

## 2. Technologies Used

* Spring Boot
* Spring Web
* Spring Data MongoDB
* MongoDB
* JUnit 5 & Mockito for unit testing
* Maven as the build tool

---

## 3. Project Structure
src/main/java/com/java/spr
├── controller → REST API endpoints
├── service → Business logic
├── repo → MongoDB repositories
├── model → Account and Transaction documents
├── exception → Custom exceptions + global exception handler

src/test/java/com/java/spr
├── AccountServiceTest
└── AccountRepositoryTest


---

## 4. API Endpoints

Below are the main APIs used in this project:

* **Create Account**: `POST /api/accounts`
* **Get Account Details**: `GET /api/accounts/{accountNumber}`
* **Deposit Amount**: `PUT /api/accounts/{accountNumber}/deposit?amount={amount}`
* **Withdraw Amount**: `PUT /api/accounts/{accountNumber}/withdraw?amount={amount}`
* **Transfer Amount**: `POST /api/accounts/transfer?sourceAccount={source}&destinationAccount={destination}&amount={amount}`
* **View All Transactions of an Account**: `GET /api/accounts/{accountNumber}/transactions`
* **Optional: List All Accounts**: `GET /api/accounts`

These APIs can be tested easily using **Postman**.

---

## 5. MongoDB Collections

Two main collections are created in the configured database (**bankingdb**):

### accounts collection

Stores account details such as:
- account number
- holder name
- balance
- status
- creation timestamp

### transactions collection

Stores each deposit, withdrawal, and transfer:
- type of transaction (DEPOSIT, WITHDRAW, TRANSFER)
- amount
- timestamp
- status
- source / destination account numbers

---

## 6. How to Run the Project

* Make sure MongoDB is running on your system (default port 27017).
* Open the project in IntelliJ, Eclipse, or any IDE supporting Spring Boot.
* Check `application.properties` for MongoDB settings:

```properties
spring.data.mongodb.database=bankingdb
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
