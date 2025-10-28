# SkillHub – Freelance Work Marketplace

## Team Members

* JERCĂU Hadasa-Ștefana
* FINICHIU Eduard-Adelin

## Project Description

SkillHub is a freelance work marketplace designed to connect clients with freelancers offering a wide range of digital services. Clients can create and post job requests, such as graphic design, programming, writing, or consulting tasks. Freelancers can browse available job listings, place bids or send service offers, and communicate with clients to negotiate project terms.

The platform aims to simulate essential workflows found in professional freelance platforms, focusing on task posting, proposal submissions, basic messaging, and profile management. SkillHub emphasizes modularity, scalability, and maintainability through the use of design patterns and architectural best practices. The goal is not to implement a full commercial system, but rather to demonstrate a clean, flexible architecture that supports future expansion.

## Core Features

* **User Management**: Clients and freelancers with different capabilities.
* **Job Posting & Browsing**: Clients post jobs; freelancers explore and respond.
* **Bidding System**: Freelancers submit proposals with rates and descriptions.
* **Notifications**: Alerts for new job postings or proposal updates.

## Chosen Design Patterns and Justification

### 1. Singleton Pattern

**Usage**: Centralized management of non-duplicable system resources (Centralized configuration / database manager)

**Justification**: Ensures a single shared instance for managing system-wide resources such as configuration settings or a global database connection. This prevents duplication and ensures consistency across the platform, avoiding conflicts from multiple instances. In modern, testable architectures, especially when aiming for future service separation (as mentioned in the Future Considerations section, at the end of the readme file), directly using a Singleton for a Database Manager can make unit testing extremely difficult (it becomes hard to mock the database). A Dependency Injection (DI) Container managing a single instance (as a scoped or singleton dependency) is often preferred over the classic Singleton pattern for core services. However, for a simple Configuration Manager, it remains a viable choice. Thus, the Singleton Pattern is well suited for this academic project.

**Benefits over a simpler approach**: Unlike a simple global variable or static class, the Singleton design pattern guarantees controlled instantiation and can enforce lazy initialization (creating the resource only when it is first requested). This provides a single, thread-safe access point, preventing initialization race conditions or conflicts that can arise in concurrent execution environments.

### 2. Factory Method Pattern

**Usage**: Creating different types of users (Client, Freelancer) or job types (Fixed-price, Hourly).

**Justification**: Provides a flexible and scalable way to instantiate various object types without exposing the creation logic. It simplifies adding new user or job categories in the future and reduces tight coupling. 

**Benefits over a simpler approach**: This prevents the non-scalable "conditional bloat" of using large if/else or switch blocks for object creation. It centralizes polymorphism and drastically reduces coupling between the application logic and concrete class names.

### 3. Builder Pattern

**Usage**: Constructing complex objects like job posts or freelancer profiles step by step.

**Justification**: Allows building objects with optional fields (skills, budget, deadlines) in a readable and flexible manner, avoiding long constructors or multiple optional parameters. It improves code readability and prevents the problem of "telescoping constructors". A telescoping constructor is generally considered to be an anti-pattern.

**Benefits over a simpler approach**: The Builder guarantees that the object is only retrieved (via the build() method) once all required fields are set and construction is complete. This promotes robustness and provides significantly cleaner, more readable instantiation code compared to cumbersome constructors or multiple setter calls.

### 4. Facade Pattern

**Usage**: Simplifying interactions with complex subsystems, such as notifications or payment processing.

**Justification**: Provides a single, unified interface to these subsystems, improving maintainability and making it easier for other components to interact with them without knowing their internal complexity. This choice fully supports the goal of modularity and maintainability by shielding the rest of the application from internal service changes.

**Benefits over a simpler approach**: The Facade achieves maximum decoupling. Service layers (like the Bidding System) interact only with the Facade (NotificationFacade.sendJobAlert(...)) and remain completely isolated from the subsystem's internal complexity, configuration, and potential instability. This ensures that changes within the subsystem (e.g., switching from one email provider to another) do not ripple through the entire application.

## Future Architectural Considerations

SkillHub is designed with expandability in mind. In future milestones:

* We will explore monolithic vs microservices vs event-driven architectures.
* Later implementation will separate services such as User, Job, and Messaging.
* Support for message queues and CI/CD pipelines will be added.

## Class Diagram 

A Class Diagram has been designed for the 2nd Milestone of this project.

Class Diagram:
![Class Diagram](/Diagrams/Class%20Diagram%20UML%20-%20vers3%20-%20white.jpg)

The Class Diagram has been designed with attention to deails and it suits the project`s needs.

## Sequence Diagrams

Two Sequence Diagrams have been designed for the 2nd Milestone of this project.

Job Creation Sequence Diagram:
![The Job Creation Sequence Diagram](/Diagrams/Sequence%20Diagram%20Job%20Creation.png)

Notification Process Sequence Diagram:
![The Notification Process Sequence Diagram:](/Diagrams/Sequence%20Diagram%20Notification%20Process.png)

---

This document represents Milestone 2, for the SDT Project, establishing the foundation of the project concept and architecture.
