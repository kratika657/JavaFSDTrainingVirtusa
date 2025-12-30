# Banking System Simulator – Phase 2 (Microservices Architecture)

## Project Overview
This project is a **Distributed Banking System Simulator** developed using **Microservices Architecture** with **Spring Boot and MongoDB**.  
It is a refactored version of a monolithic banking application, split into independent microservices that communicate using REST APIs and are managed through **Eureka Service Discovery** and **API Gateway**.

The system simulates real-world banking operations such as account management, transactions, and notifications while ensuring **fault isolation, scalability, and resiliency**.

---

## Learning Objectives Achieved
- Designed and implemented microservices architecture using Spring Boot  
- Implemented **Eureka Server** for service discovery  
- Used **Spring Cloud API Gateway** for request routing  
- Implemented inter-service communication using **REST / Feign Client**  
- Applied **Resilience4j Circuit Breaker** for fault tolerance  
- Used **MongoDB per microservice** for independent data persistence  
- Implemented centralized logging using **SLF4J + Logback**  
- Containerized services using **Docker**  

---

## Microservices Architecture

### Services Overview

| Microservice | Description | Database |
|--------------|------------|----------|
| Account Service | Manages account creation, retrieval, updates | accounts_db |
| Transaction Service | Handles deposits, withdrawals, transfers | test |
| Notification Service | Sends transaction notifications | — |
| API Gateway | Routes external requests | — |
| Eureka Server | Service discovery | — |

---

## Architecture Flow (Conceptual)

Client  
→ API Gateway  
→ Eureka Service Discovery  
→ Account / Transaction / Notification Services  
→ MongoDB (per service)

---

## Microservices Details

### 1. Account Service
**Port:** `8081`  
**Database:** `accounts_db`

#### Responsibilities
- Create, retrieve, update, and delete accounts  
- Maintain account balances  
- Activate or deactivate accounts  

#### APIs

| Operation | Method | Endpoint |
|---------|--------|----------|
| Create Account | POST | `/api/accounts` |
| Get Account | GET | `/api/accounts/{accountNumber}` |
| Update Balance | PUT | `/api/accounts/{accountNumber}/balance` |
| Activate / Deactivate | PUT | `/api/accounts/{accountNumber}/status` |

---

### 2. Transaction Service
**Port:** `8082`  
**Database:** 'test'

#### Responsibilities
- Handle deposits, withdrawals, and transfers  
- Communicate with Account Service to update balances  
- Trigger Notification Service  

#### APIs

| Operation | Method | Endpoint |
|---------|--------|----------|
| Deposit | POST | `/api/transactions/deposit` |
| Withdraw | POST | `/api/transactions/withdraw` |
| Transfer | POST | `/api/transactions/transfer` |
| Get Transactions | GET | `/api/transactions/account/{accountNumber}` |

---

### 3. Notification Service
**Port:** `8083`

#### Responsibilities
- Receive transaction events  
- Send simulated email/log notifications  

#### API

| Operation | Method | Endpoint |
|---------|--------|----------|
| Send Notification | POST | `/api/notifications/send` |

---

### 4. Service Discovery & Gateway

#### Eureka Server
- Port: `8761`
- Registers and discovers all microservices  
- URL: `http://localhost:8761`

#### API Gateway
- Port: `8080`
- Single entry point for all client requests  

**Routing Rules**
- `/api/accounts/**` → Account Service  
- `/api/transactions/**` → Transaction Service  
- `/api/notifications/**` → Notification Service  

---

## Inter-Service Communication
- Transaction Service → Account Service (balance updates)
- Transaction Service → Notification Service (alerts)
- Implemented using **Feign Client / RestTemplate**
- **Resilience4j Circuit Breaker** ensures graceful failure handling

---

## Configuration
| Service | Port |
|-------|------|
| Account Service | 8081 |
| Transaction Service | 8082 |
| Notification Service | 8083 |
| API Gateway | 8080 |
| Eureka Server | 8761 |

---

## Logging
- Centralized logging using **SLF4J + Logback**
- Correlation IDs used for tracing requests across services

---

## Testing
- Unit testing with **JUnit & Mockito**
- End-to-end testing using **Postman**
- Tested complete flow:
  - Deposit
  - Transfer
  - Notification Trigger

---

## Docker Setup

### Create Docker Network
```bash
docker network create bank-network

## Build Services

mvn clean package

## Run Containers

docker run -d --name eureka-server --network bank-network -p 8761:8761 eureka-server
docker run -d --name api-gateway --network bank-network -p 8080:8080 api-gateway
docker run -d --name mongo --network bank-network -p 27017:27017 mongo
docker run -d --name account-service --network bank-network -p 8081:8081 account-service
docker run -d --name transaction-service --network bank-network -p 8082:8082 transaction-service
docker run -d --name notification-service --network bank-network -p 8083:8083 notification-service

