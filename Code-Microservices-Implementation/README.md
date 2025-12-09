# SkillHub - Microservices Application (Dockerized)

SkillHub is a freelance marketplace application built using a robust
**Microservices Architecture**. It demonstrates core cloud-native
patterns like **Service Discovery**, **Centralized Configuration**, and
**Inter-Service Communication** via Feign Clients.

## 🏗️ Architecture Overview

The system consists of **6 Dockerized services** communicating via a
dedicated bridge network:

  -----------------------------------------------------------------------
  Service                          Port             Description
  --------------------- --------------------------- ---------------------
  **Config Server**               `8888`            Centralized
                                                    configuration
                                                    management
                                                    (Git/Local)

  **Discovery Service**           `8761`            Service Registry
                                                    using Netflix Eureka

  **API Gateway**                 `9090`            Single entry point
                                                    routing to all
                                                    microservices

  **User Service**                `8081`            Manages Clients and
                                                    Freelancers
                                                    (PostgreSQL/H2)

  **Job Service**                 `8082`            Manages Jobs
                                                    (validates Users via
                                                    Feign)

  **Bidding Service**             `8083`            Manages Bids
                                                    (validates Jobs &
                                                    Users via Feign)
  -----------------------------------------------------------------------

## 🚀 Design Patterns Implemented

The application implements the following design patterns:

-   **Singleton Pattern** --- in `UserFactory`
-   **Factory Method Pattern** --- creates different User role objects
-   **Builder Pattern** --- via Lombok for clean entity construction
-   **Facade Pattern** --- orchestrates Factory + Repository logic in
    `UserRegistrationFacade`

## 🐳 Docker Setup & Commands

All services are containerized. Run the commands below from the project
root:

This guide provides the exact steps required to run the **SkillHub
Dockerized Microservices Application**, validate configuration, and
perform the functional demo using Postman.

------------------------------------------------------------------------

## 🚀 Steps to Start the Application

### **1. Open Docker Desktop**

Ensure Docker Desktop is running before starting any containers.

------------------------------------------------------------------------

### **2. Clean Shutdown of Previous Containers**

In the project root, run:

``` bash
docker-compose down
```

This removes all containers and the network to ensure a clean
environment.

------------------------------------------------------------------------

### **3. Navigate to Project Root Directory**

In **Terminal / PowerShell**, go to:

    Code-Microservices-Implementation

------------------------------------------------------------------------

### **4. Start All Services**

Run either:

Normal start:

``` bash
docker-compose up --build
```

Force fresh rebuild:

``` bash
docker-compose up --build --force-recreate
```

------------------------------------------------------------------------

### **5. Check Service Discovery**

Visit:

    http://localhost:8761/

You should see all services listed as **UP**.

------------------------------------------------------------------------

### **6. Check Centralized Configuration**

Visit:

    http://localhost:8888/api-gateway/default

**Goal:** Confirm that the API Gateway uses configuration from the
Config Server rather than local files.

------------------------------------------------------------------------

### **7. Inspect Running Containers (Optional)**

Check status, restarts, or errors:

``` bash
docker ps
```

------------------------------------------------------------------------

### **8. Restart API Gateway (Recommended)**

This helps avoid race conditions where the gateway starts before
configuration is fully loaded:

``` bash
docker-compose restart api-gateway
```

------------------------------------------------------------------------

## 🔬 Functional Demo (Postman Collection)

Follow this exact order to validate inter-service communication and
logic.

------------------------------------------------------------------------

### **A. Setup Data & Factory Pattern**

#### 1. Run **POST Create Client**

Creates **User 1** with role **CLIENT**.

#### 2. Run **POST Create Freelancer**

Creates **User 2** with role **FREELANCER**.

**Goal:**\
Validate **Factory Method** and **Builder Pattern** by inspecting
created JSON.

#### 3. Run **GET All Users**

------------------------------------------------------------------------

### **B. Inter-Service Validation & Job Creation**

#### 1. Run **POST Create Job** (requires Client ID = 1)

Job Service must contact the User Service via **Feign** to verify that
User 1 is a Client.

#### 2. Run **GET Job by ID**

------------------------------------------------------------------------

### **C. Triple Validation & Bidding Logic**

#### 1. Run **POST Place Bid**

Requires: - Job ID = 1\
- Freelancer ID = 2

**Goal:**\
Demonstrates the Bidding Service contacting both Job & User Services
before saving the bid.

------------------------------------------------------------------------

### **D. Edge Case Test (Resilience & Validation)**

Run edge-case Postman requests:

-   Posting a Job with a **Freelancer** instead of a Client\
-   Placing a Bid with invalid Job/User

**Expected Result:**\
Application correctly returns **500 Internal Server Error** to indicate
failed validation.

------------------------------------------------------------------------

## 🧹 Shutdown Procedure

When finished:

1.  Return to the terminal running Docker Compose and press:

```{=html}
<!-- -->
```
    Ctrl + C

2.  Clean shutdown:

``` bash
docker-compose down
```

------------------------------------------------------------------------
