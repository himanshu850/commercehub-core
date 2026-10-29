# CommerceHub Core

CommerceHub is a Java 21 e-commerce and inventory management application developed as a hands-on enterprise Java learning project.

## Features

- Physical and digital product management
- Product search, sorting, and filtering
- Inventory management and purchasing
- Thread-safe inventory reservations
- Discount strategies
- Factory and Builder design patterns
- Generic repository abstraction
- Automated testing with JUnit 5 and Mockito

## Technologies

Java 21, Maven, JUnit 5, Mockito, Git and GitHub.

## Project Structure

- `model` — Product domain models
- `factory` — Product creation
- `builder` — Fluent product construction
- `strategy` — Discount algorithms
- `repository` — Product persistence abstraction
- `service` — Business operations
- `concurrency` — Threading and inventory simulations
- `exception` — Custom exceptions

## Run Tests

```bash
mvn clean test
```

## Build

```bash
mvn clean package
```

## Run the Application

Open the project in Eclipse and run `com.commercehub.App` as a Java Application.

## Roadmap

- Spring Boot REST API
- PostgreSQL and Spring Data JPA
- Authentication and authorization
- Angular frontend
- Docker and AWS deployment

## Status

Phase 1 — Core Java fundamentals, design patterns, concurrency, and automated testing.
