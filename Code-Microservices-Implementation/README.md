# SkillHub - Microservices Application

SkillHub is a freelance marketplace application built using a Microservices Architecture. It features separate services for Users, Jobs, and Bidding, all orchestrated via Service Discovery and an API Gateway. This README is for Milestone 4.

## Architecture

The system consists of the following components:

1.  **Config Server (8888):** Centralized configuration management.
2.  **Discovery Service (8761):** Service registry using Netflix Eureka.
3.  **API Gateway (9090):** Single entry point using Spring Cloud Gateway (Reactive).
4.  **User Service (8081):** Manages Clients and Freelancers (PostgreSQL/H2).
5.  **Job Service (8082):** Manages Job postings (Validates Users via Feign).
6.  **Bidding Service (8083):** Manages Bids (Validates Jobs & Users via Feign).

## Design Patterns Implemented

* **Singleton Pattern:** Used in `UserFactory` to ensure a single instance of the factory exists.
* **Factory Method Pattern:** Used to encapsulate the creation logic of `User` objects based on role (Client vs. Freelancer).
* **Builder Pattern:** Used via Lombok `@Builder` for clean object construction in Entities.
* **Facade Pattern:** Used in `UserRegistrationFacade` to hide the complexity of the Factory and Service interaction from the Controller.

## Prerequisites

* **Docker Desktop** (Running)
* **Java 21** (If running locally without Docker)
* **Maven**

## How to Run (Docker - Recommended)

For docker-compose.yml : 
* **in terminal run:**    docker-compose up --build

* **to stop all forced:**  docker-compose down

* **forced fresh start, forced clean build:**  docker-compose up --build --force-recreate

* **list containers processes:**  docker ps

The entire system is containerized. To start the application:

1.  Navigate to the root directory.
2.  Run the following command:

```bash
docker-compose up --build
 