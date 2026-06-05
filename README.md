# Customer Management API

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" />
  <img src="https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" />
  <img src="https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white" />
  <img src="https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white" />
  <img src="https://img.shields.io/badge/Flyway-CC0200?style=for-the-badge&logo=flyway&logoColor=white" />
  <img src="https://img.shields.io/badge/Lombok-E10000?style=for-the-badge&logo=lombok&logoColor=white" />
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Status-In_Progress-yellow?style=for-the-badge" />
  <img src="https://img.shields.io/badge/Architecture-Layered-blue?style=for-the-badge" />
  <img src="https://img.shields.io/badge/Pattern-REST_API-green?style=for-the-badge" />
</p>

---

## About This Project

**Customer Management API** is a production-inspired backend project built with **Java 21 + Spring Boot**, focused on applying real-world engineering practices found in fintechs and large-scale technology companies.

This project simulates the **incremental evolution of a real backend system**, applying clean architecture, professional patterns, and enterprise-grade decisions at every step.

---

## Architecture Overview

This API follows a **Layered Architecture**, separating responsibilities to maximize maintainability, testability, and scalability.

```
┌─────────────────────────────────┐
│         CLIENT (HTTP)           │
└────────────────┬────────────────┘
                 │
┌────────────────▼────────────────┐
│       CONTROLLER LAYER          │  ← HTTP request/response, endpoint mapping
└────────────────┬────────────────┘
                 │
┌────────────────▼────────────────┐
│        SERVICE LAYER            │  ← Business rules, application logic
└────────────────┬────────────────┘
                 │
┌────────────────▼────────────────┐
│       REPOSITORY LAYER          │  ← Data access, JPA persistence
└────────────────┬────────────────┘
                 │
┌────────────────▼────────────────┐
│       PostgreSQL DATABASE       │  ← Data persistence
└─────────────────────────────────┘
```

---

## System Design

```
Client
  │
  ▼
REST API ──────────────────────────────────────────────┐
CustomerController                                      │
  │                                                     │
  ▼                                                     │
CustomerService ──── Business Rules ──── Validations   │
  │                                                     │
  ▼                                                     │
CustomerRepository                                      │
(Spring Data JPA)                                       │
  │                                                     │
  ▼                                                     │
PostgreSQL ◄──────── Flyway Migrations ─────────────────┘
```

---

## Domain Model

```
Customer
├── id           (Long)        — Auto-generated primary key
├── name         (String)      — Customer full name
├── email        (String)      — Unique email address
├── cpf          (String)      — Brazilian tax identifier (unique)
└── phoneNumber  (String)      — Contact phone number
```

---

## API Endpoints

**Base URL:** `http://localhost:8080`

| Method   | Endpoint           | Description              | Status |
|----------|--------------------|--------------------------|--------|
| `POST`   | `/customers`       | Create a new customer    | ✅ Done |
| `GET`    | `/customers`       | List all customers       | ✅ Done |
| `GET`    | `/customers/{id}`  | Get customer by ID       | ✅ Done |
| `PUT`    | `/customers/{id}`  | Update customer data     | ✅ Done |
| `DELETE` | `/customers/{id}`  | Delete customer by ID    | ✅ Done |

### Request — Create / Update Customer

```json
{
  "name": "João Silva",
  "email": "joao.silva@email.com",
  "cpf": "123.456.789-00",
  "phoneNumber": "+55 81 99999-0000"
}
```

### Response — Customer Data

```json
{
  "id": 1,
  "name": "João Silva",
  "email": "joao.silva@email.com",
  "cpf": "123.456.789-00",
  "phoneNumber": "+55 81 99999-0000"
}
```

### Response — Error

```json
{
  "status": 404,
  "message": "Customer not found with id: 1",
  "timestamp": "2026-06-04T10:00:00"
}
```

---

## Project Structure

```
src/main/java
│
├── controller
│   └── CustomerController.java        ← REST endpoints
│
├── service
│   └── CustomerService.java           ← Business logic
│
├── repository
│   └── CustomerRepository.java        ← JPA data access
│
├── entity
│   └── Customer.java                  ← Domain entity
│
├── dto
│   ├── CustomerRequestDTO.java        ← Incoming request body
│   ├── CustomerResponseDTO.java       ← Outgoing API response
│   └── ErrorResponseDTO.java          ← Standardized error format
│
├── exception
│   ├── CustomerNotFoundException.java ← Custom domain exception
│   └── GlobalExceptionHandler.java    ← @RestControllerAdvice
│
└── CustomerManagementApiApplication.java
```

---

## Tech Stack

| Layer              | Technology                        |
|--------------------|-----------------------------------|
| Language           | Java 21                           |
| Framework          | Spring Boot                       |
| Persistence        | Spring Data JPA (Hibernate)       |
| Validation         | Spring Validation                 |
| Database           | PostgreSQL                        |
| Migrations         | Flyway                            |
| Build Tool         | Maven                             |
| Boilerplate        | Lombok                            |
| Architecture       | Layered Architecture              |
| API Style          | REST                              |

---

## Engineering Decisions

### DTO Pattern

Entities are **never exposed directly** through the API. DTOs separate:

```
Customer (Entity)          ≠          CustomerResponseDTO (Contract)
  └── JPA annotations                   └── Clean, safe API response
  └── Database columns                  └── No internal data leaking
  └── Internal identifiers              └── Stable contract for clients
```

### Global Exception Handling

All errors are handled centrally via `@RestControllerAdvice`, eliminating scattered `try/catch` blocks and guaranteeing a consistent error response format across the entire API.

### Layered Architecture

Clear separation of concerns:

- **Controller** knows nothing about JPA
- **Service** knows nothing about HTTP
- **Repository** knows nothing about business rules

---

## What's Already Built

- [x] Full CRUD — Create, Read, Update, Delete
- [x] Layered Architecture (Controller → Service → Repository)
- [x] DTO Pattern (Request / Response separation)
- [x] Custom Exception: `CustomerNotFoundException`
- [x] Global Exception Handling (`@RestControllerAdvice`)
- [x] Standardized Error Response (`ErrorResponseDTO`)
- [x] Database Migrations with Flyway
- [x] PostgreSQL Integration

---

## Engineering Roadmap

The project evolves incrementally following production backend engineering standards.

### Phase 2 — API Quality
- [ ] Swagger / OpenAPI 3 Documentation
- [ ] Bean Validation (`@NotBlank`, `@Email`, `@CPF`)
- [ ] Structured Logging Strategy (SLF4J + Logback)
- [ ] Pagination & Filtering (`Pageable`)

### Phase 3 — Testing
- [ ] Unit Tests — JUnit 5 + Mockito
- [ ] Integration Tests — Spring Boot Test
- [ ] Testcontainers (real PostgreSQL in tests)
- [ ] Test Coverage Report

### Phase 4 — Infrastructure
- [ ] Dockerfile
- [ ] Docker Compose (app + database)
- [ ] CI/CD Pipeline (GitHub Actions)
- [ ] Health Check Endpoint (`/actuator/health`)

### Phase 5 — Security
- [ ] JWT Authentication
- [ ] Role-based Authorization
- [ ] Password Encoding (BCrypt)

### Phase 6 — Scalability & Observability
- [ ] Redis Cache Layer
- [ ] Async Messaging (RabbitMQ or Kafka)
- [ ] Prometheus + Grafana Metrics
- [ ] Distributed Tracing

---

## Running Locally

### Prerequisites

- Java 21+
- Maven
- PostgreSQL running on port `5433`

### Clone and run

```bash
git clone https://github.com/YOUR_USERNAME/customer-management-api.git
cd customer-management-api
./mvnw spring-boot:run
```

Application runs at:

```
http://localhost:8080
```

---

## Author

**Sérgio Ricardo Feitosa**

Backend Java Engineer in progress  transitioning from a legal career into software engineering with a focus on backend 

Building a strong foundation in Java, Spring Boot, clean architecture, and backend system design through consistent practice and real-world projects.

<p>
  <a href="https://linkedin.com/in/https://www.linkedin.com/in/s%C3%A9rgiofeitosa/">
    <img src="https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white" />
  </a>
  <a href="https://github.com/https://github.com/SergioFeitosaa">
    <img src="https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white" />
  </a>
</p>
