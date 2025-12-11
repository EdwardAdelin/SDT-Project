# SkillHub - Microservices Application (Dockerized with RabbitMQ)

This project implements a scalable freelance marketplace (SkillHub), demonstrating core cloud-native patterns, Asynchronous Communication (RabbitMQ), and a foundational Continuous Integration (CI) pipeline.

SkillHub is a freelance marketplace application built using a robust
**Microservices Architecture**. It demonstrates core cloud-native
patterns like **Service Discovery**, **Centralized Configuration**, and
**Inter-Service Communication** via Feign Clients.

## 🏗️ Architecture Overview

The system consists of **7 Dockerized services** communicating via a
dedicated bridge network:


  Service,                          Port,            Description
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

  **Notification Service**        `8084`            Message consumer
                                                    (Rabbit MQ)
                                                    used 

  -----------------------------------------------------------------------

## 🐰 Asynchronous Communication (RabbitMQ)

The system uses RabbitMQ to decouple the Bidding process from Notifications, improving fault tolerance and responsiveness.
* Benefits:
- * Fault Tolerance: If the Notification Service fails, messages are held safely in the queue. The Bidding Service remains functional, ensuring bids are never lost.
- * Low Latency: The user receives an immediate 201 Created response from the Bidding Service without waiting for the slow, potentially long-running notification task (e.g., sending an email) to complete.
* Asynchronous Flow:
- * Bidding Service (Producer): Saves the bid to the database.
It sends a message to the skillhub.events exchange with the routing key bid.placed.
- * RabbitMQ Broker: Routes the message to the listening notifications.queue.
- * Notification Service (Consumer): Picks up the message and performs the secondary action (e.g., logging a confirmation).


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

In Terminal / Powershell go to root directory (Code-Microservices-Implementation).

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

Verify Message Broker: **Check** the RabbitMQ Management Dashboard: http://localhost:15672 (Login: guest/guest). The notifications.queue should be present.


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

After that is reccomended that you restart: BIDDING SERVICE AND  NOTIFICATION SERVICE (to make sure no race condition appears)

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

## Message Broker Testing

(Postman)

- Run the full sequence (A-D), paying special attention to step C to verify the asynchronous message delivery.
A. Setup Data
Run "POST Create Client" and "POST Create Freelancer". (Users ID 1 & 2).
Run "POST Create Job" (Requires Client ID 1). (Job ID 1).

B. Asynchronous Validation (The RabbitMQ Test)
Run "POST Place Bid". (Requires Job ID 1 and Freelancer ID 2).
Proof of Concept: Immediately check the Notification Service logs for the confirmation message:
docker-compose logs --tail=20 notification-service

Goal: You must see the --- RECEIVED ASYNC NOTIFICATION --- message.
C. Edge Case Test
Run "Edge Case" requests for Job and Bid to demonstrate input validation.

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

## ⚙️ 6. CI/CD Pipeline (Automation)

The project uses GitHub Actions to automate the build, test, and deployment packaging process.

The pipeline builds, tests, and verifies Docker deployment on PRs/pushes to main. See CI-Pipeline.md for details.

**Full details are also available separately in: [CI-Pipeline.md](CI-Pipeline.md)**

* **Trigger**: The pipeline runs automatically whenever a Pull Request is opened targeting the `main` branch.
* **Build & Test**: The pipeline runs `mvn clean package` to compile code and run unit tests for all 7 services.
* **Deployment Check**: The pipeline runs `docker compose build` to verify that all Docker images can be successfully created from the codebase.

## ⚙️ Workflow Configuration

The pipeline is defined in `.github/workflows/ci-pipeline.yml`.

### Triggers
* **Push to Main:** Triggers a deployment check when code is merged.
* **Pull Request:** Triggers a validation run to prevent broken code from being merged.

## 🚀 Pipeline Stages

The pipeline performs three critical checks to ensure production readiness:

### 1. Environment Setup
* Provisions an **Ubuntu** runner.
* Installs **Java 21 (Amazon Corretto)**.
* Restores Maven dependencies from cache to speed up execution.

### 2. Build & Automated Testing
* **Command:** `mvn clean package`
* **Purpose:**
    * Compiles the Java source code for all 7 microservices.
    * Runs **Unit Tests** (JUnit) to verify application logic and context loading.
    * Packages the application into executable JAR files.

### 3. Docker Deployment Verification
* **Command:** `docker compose build`
* **Purpose:**
    * Reads the `docker-compose.yml` configuration.
    * Validates all `Dockerfile` instructions.
    * Builds the actual Docker images that would be used in production.
    * This step ensures that the **deployment artifacts** are valid and ready to run.

## ✅ How to Check Pipeline Status

1. Go to the **Actions** tab in the GitHub repository.
2. Click on the latest workflow run.
3. Verify that both **"Build and Test"** and **"Build Docker Images"** steps have a green checkmark.

CI simulates deployment, actual local runs use docker-compose up --build post-merge

## 4. Note on Local Deployment

While this pipeline simulates a deployment in the CI environment to verify stability, it does not push containers to your local machine.

In Milestone 4 a GUI / Front-End was not needed. The app was tested and used via Postman.
For this reason, deployment is done on GitHub, health of containers is checked, app runs for 60 seconds.

A deployment on a remote server / machine could be tested in futere in a GUI will be implemented. 

For a manual testing via POST / GET requests, futere instructions can be used. The Postman colection can be used, it can be found in the GitHub repository, branches of Milestone 4 and Milestone 5.


**To deploy locally after a successful merge:**
1.  Pull the latest code: `git pull origin main`
2.  Run the deployment command: `docker-compose up --build`