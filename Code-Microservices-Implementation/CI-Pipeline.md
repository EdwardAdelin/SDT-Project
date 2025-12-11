# CI/CD Pipeline Documentation

This project utilizes **GitHub Actions** to implement a continuous integration and delivery pipeline.

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