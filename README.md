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
![Class Diagram](/Diagrams/ClassDiagramUML.jpg)

The Class Diagram has been designed with attention to deails and it suits the project`s needs.

## Sequence Diagrams

Two Sequence Diagrams have been designed for the 2nd Milestone of this project.

Job Creation Sequence Diagram:
![The Job Creation Sequence Diagram](/Diagrams/SequenceDiagramJobCreation.png)

Notification Process Sequence Diagram:
![The Notification Process Sequence Diagram:](/Diagrams/SequenceDiagramNotificationProcess.png)

## Architecture Analysis

**Monolithic Architecture**

* Description of structure:
All functionality (UI, business logic, persistence, and notification logic) lives in a single deployable application (one jar/war or a container image). Internally the app is modular (packages or layers): Presentation (web UI / REST controllers), Service layer (user management, job management, bidding, notifications facade), Data Access layer (repositories), and shared utilities (configuration singleton, factories, builders, facades).
* Data flow:
Example for New job posting:
Client submits job form → HTTP POST to JobController.
The controller calls JobService (Service layer) which uses JobBuilder to construct the job entity.
JobRepository persists the job to the single relational DB.
NotificationFacade triggers NotificationService (internal) which queries interested freelancers and creates notifications (stored in DB or pushed).
UI and APIs return success.
* Diagrams:
Component Diagram:
![Component Diagram:](/Diagrams/MonolitComponentDiagram.drawio.png)

Deployment Diagram:
![Deployment Diagram:](/Diagrams/MonolithDeplymentDiagram.drawio.png)
* Pros and Cons

Pros (for SkillHub):

        Simplicity: Easy to implement and reason about, good for the academic milestone 2.
        Lower overhead: Single codebase, single deployment pipeline; fewer infrastructure components, easy to observe and work on.
        Transactions: Simpler to maintain ACID (Atomicity, Consistency, Isolation, and Durability) transactions across operations (e.g. job creation + initial bid). Easier to link files between them, no complicated API needed.
        Easier local dev & testing: No need to run multiple services.

Cons (for SkillHub):

        Scalability limits: Harder to scale parts independently (e.g. notification bursts).
        Deployment risk: Any change requires redeploying the whole app. If something breaks, the whole thing breaks.
        Long-term complexity: As features grow, the codebase can become large and harder to maintain. Not possible to separate stuff and work independently.
        Barrier to polyglot tech: Cannot mix different technologies per service easily. (not advised for a big scale project like SkillHub intends to be)
        Testing: Unit tests fine, but integration tests may be heavier. Problems are also harder to identify, without being able to track very fast the source of error. Solving problems may result in creating other problems (common monolit behaviour).

**Microservices Architecture**
* Description of structure:
System split into small, independently deployable services by business capability. In the case of the SkillHub project, the following small / independently deployable services might be a very good idea. The list includes the following:
        Auth/User Service: user accounts, profile management.
        Job Service: job creation, editing, browsing.
        Bidding Service: proposals and bid lifecycle.
        Notification Service: sending notifications, interfaces to email/push.
        Gateway / API Aggregator: single entrypoint for clients (HTTP gateway / API Gateway).
        Shared DB per service (each service owns its data) or a combination where necessary.
        Service Discovery (if dynamic), and Config Service for centralized configuration.
* Data flow:
Example for New job posting:
Client → API Gateway → Job Service endpoint.
Job Service writes to its DB and emits an event JobCreated.
Notification Service subscribes to JobCreated event and creates notifications for matching freelancers (reads data either from the event or queries User Service - can be also called Profile Service - if more info needed).
Bidding Service is notified or remains passive until freelancers bid.

This example showcases both the data flow but also highlights the core advantages of the Microservices architecture.

* Diagrams
Component Diagram:
![Component Diagram:](/Diagrams/MicroservicesComponentsDiagram.drawio.png)

Deployment Diagram:
![Deployment Diagram:](/Diagrams/EventDrivenDeplymentDiagram2.drawio.png)

* Pros and Cons

Pros (for SkillHub):

        Independent scaling: For example: Scale Notification service separately during high load (e.g. many new jobs are posted on the platform in a short period of time).
        Technological freedom: Different services can use best-fit tech stacks (e.g. Node for real-time notifications). This ensures that in the future we will not be stuck with the same technology.
        Fault isolation: A failure in Bidding service doesn't necessarily take down Job browsing. The Bidding service does cooperate with other services but it is not riding in the same boat. As long as a service does not fully rely on the affected service, the first mentioned will not have serious issues.
        Team autonomy & parallel development: If a project expands, teams can own services and manage them with great care. For example, I will be able to build the Bidding service while my colleague will build the User Service. This also ensures better cooperation between teams.
        Easier CI/CD (Continuous Integration and Continuous Delivery/Deployment) for individual components: Faster iteration on small services.

Cons (for SkillHub):

        Operational complexity: Need containers/orchestration, logging, monitoring, tracing, service discovery. Attention will be split into many directions, the project manager shall not neglect any service in favour of others since the final functionality of the project consists in the ability to merge all services into one functioning application.
        Distributed transactions: Harder to maintain consistency; eventual consistency patterns needed.
        Higher infrastructure cost: Multiple services lead to more infrastructure & management overhead. Will they run in the Cloud, on our Servers, how will we connect them, how do we optimize them, etc. ?
        Testing complexity: Integration tests require many services or mocks. The integration is essential in order to have a functional app.
        Design overhead: Requires careful API design.

**Event-Driven Distributed Architecture**
I choose Event-Driven Architecture because SkillHub benefits from decoupled reaction to domain events.

* Description of structure:
Core idea: services react to domain events delivered through a message broker (e.g. Kafka). Command queries can still be implemented via HTTP endpoints, but side effects are handled via events.
The following list of services can represent the foundation of the Event-Driver Architecture in the case of SkillHub project:
Job Command Service (synchronous API for job CRUD): persists job and emits JobCreated.
Notification Worker: consumes JobCreated to compute recipients and send notifications (email, in-app).
Search/Indexing Worker: consumes events to update search index.
Activity Feed Worker: builds activity streams.
Bidding Service: receives events or provides its own commands/events (e.g., BidPlaced).
User/Profiles Service: emits UserUpdated.
Event Store / Broker: Kafka or event bus; may also include an event store for event sourcing if desired.
* Data flow:
Example of data flow in the case of Job posting:
JobCommandService receives request, validates, persists job.
JobCommandService emits a JobCreated event to broker with job payload.
Notification Worker consumes JobCreated, looks up matching freelancer profiles from User Service (or has precomputed matches), and emits NotificationCreated events or calls Notification Provider.
Search Worker consumes JobCreated and updates search index

*Diagrams
Component Diagram:
![Component Diagram:](/Diagrams/EventDrivenComponentDiagram2.drawio.png)

Deployment Diagram:
![Deployment Diagram:](/Diagrams/EventDrivenDeplymentDiagram2.drawio.png)

* Pros & Cons:

Pros (for SkillHub):

        Loose coupling: Producers and consumers evolve independently. It is very good for adding features like analytics, activity streams.
        Resilience & buffering: Brokers absorb spikes (e.g. many jobs posted), enabling backpressure handling.
        Extensibility: Add new consumers (e.g. indexing, analytics) without changing producers.
        Responsiveness: Near real-time processing of side-effects.
        Good fit for notifications & analytics: Natural mapping of domain flows.

Cons (for SkillHub):

        Complexity: Requires reliable broker, handling at-least-once/duplicate events, schema evolution for events.
        Operational overhead: Manage Kafka cluster.
        Eventual consistency: Data will be eventually consistent; some flows may require compensating actions.
        Debugging harder: Tracing flows across asynchronous boundaries is more complex.


**Comparison & Final Recommendation**
* Comparison:

    Monolith: Best for speed of initial development, simple deployment, easy transactions. Poor at scaling selected components.

    Microservices: Best for independent scaling, team autonomy, and long-term maintainability at cost of operational complexity.
    
    Event-Driven: Best for extensibility, decoupling, and handling async workflows like notifications and analytics; needs strong ops and careful design.

* Reasons to consider when choosing between the 3 architectures:
    SkillHub is an academic project whose goals include demonstrating good architecture, modularity, scalability and maintainability. A well-structured modular monolith achieves these goals for the course: you can show design patterns (Singleton, Factory, Builder, Facade), produce clean class/sequence diagrams, and provide deployment diagrams, all without the heavy infra baggage.
    This was done for Milestone 2, proving that Monolit has been a good decision.
    For full production-grade demands (live multi-tenant marketplace), Microservices + Event-Driven would be preferable. 
    In our case, Monolit would be a good choice. Another pragmatic choice can be Microservices architecture, altho it can be considered as too advanced for a university project who tries to emphatise more things related to SDT and is not solly focussed on microservices architecture.


* Final Decision
	For a full scale app the Microservices + Db + Event-Driven (Kafka) would be great. In our case, if the teacher agrees, Microservices or Monolith would be better. The most efficient would be the Monolit architecture sincer our app will not need future expansion. The programmers can easily fall into technical decisions that do not really solve the real life problems. Sometimes simple technology can give better results than complicated ways of doing the same thing. If the SDT Project will evolve, a Monolith architecture can be adapted to become a Microservice architecture by slowly breaking the monolith into multiple microservices.
	In our case, the SDT Project involves 2 more milestones involving only microservices. This is a decisive factor for our project. This leads us to choose microservices architecture, being more suitable for the SDT subject that we are doing at the university.




---

This document represents Milestone 3, for the SDT Project. All Diagrams have been draw in Draw.IO by the students. No AI tools have been used for the diagrams.