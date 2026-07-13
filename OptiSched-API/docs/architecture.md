# OptiSched Architecture

## Overview

OptiSched is a RESTful web application designed to generate optimized academic timetables for higher education institutions.

The project combines software engineering principles with operations research techniques to automate the scheduling process while satisfying a set of academic and operational constraints.

The application is built using a layered architecture, where each layer has a well-defined responsibility, improving maintainability, scalability, and testability.

---

## Objectives

The main objectives of the project are:

- Manage academic scheduling data.
- Store professors, subjects, classrooms, and availability information.
- Generate valid academic timetables.
- Apply optimization techniques to find high-quality scheduling solutions.
- Provide a clean and extensible backend architecture.

---

## High-Level Architecture

```
                    Client
                       │
                  HTTP REST API
                       │
               Spring Controllers
                       │
                   Services
          ┌────────────┴────────────┐
          │                         │
     Repositories         Optimization Engine
          │                         │
          └────────────┬────────────┘
                       │
                  PostgreSQL
```

The application follows a layered architecture where each component has a specific responsibility.

Controllers expose REST endpoints.

Services contain business logic and coordinate application workflows.

Repositories provide persistence using Spring Data JPA.

The Optimization Engine is responsible for generating optimized schedules based on the available academic data.

---

## Architecture Layers

### Presentation Layer

The presentation layer exposes the application's REST API.

Responsibilities:

- Receive HTTP requests
- Validate request data
- Return HTTP responses
- Delegate business operations to the service layer

Technologies:

- Spring Boot
- Spring Web

---

### Service Layer

The service layer contains the application's business logic.

Responsibilities:

- Coordinate use cases
- Apply business rules
- Validate domain constraints
- Invoke the optimization engine
- Manage transactions

The optimization engine is also orchestrated from this layer.

---

### Persistence Layer

The persistence layer is responsible for all database operations.

Responsibilities:

- CRUD operations
- Query execution
- Entity persistence

Technology:

- Spring Data JPA
- Hibernate

---

### Domain Layer

The domain layer represents the academic scheduling problem through entities and relationships.

Current domain entities:

- Professor
- Subject
- Course
- Semester
- ProfessorQualification
- SubjectOffering
- Availability
- Classroom
- TimeSlot
- Schedule
- ScheduleEntry

---

### Optimization Layer

The Optimization Layer is responsible for generating academic timetables.

Input:

- Professors
- Subjects
- Subject Offerings
- Classrooms
- Time Slots
- Availability
- Qualifications

Output:

- Schedule
- Schedule Entries

Initially, the project focuses on generating feasible schedules while satisfying all hard constraints.

Support for soft constraints and objective optimization will be added in future versions.

---

## Data Flow

The scheduling process follows the workflow below.

```
Academic Data
      │
      ▼
Database
      │
      ▼
Service Layer
      │
      ▼
Optimization Engine
      │
      ▼
Generated Schedule
      │
      ▼
Database
      │
      ▼
REST API
```

---

## Technology Stack

Backend

- Java 21
- Spring Boot
- Spring Data JPA
- Hibernate

Database

- PostgreSQL

Build Tool

- Maven

Containerization

- Docker

Development Tools

- IntelliJ IDEA
- Git
- GitHub

Future Technologies

- Spring Security
- JWT Authentication
- Docker Compose
- AWS
- Gurobi Optimizer

---

## Project Structure

```
src
└── main
    ├── java
    │   └── com.optisched
    │       ├── controller
    │       ├── service
    │       ├── repository
    │       ├── entity
    │       ├── dto
    │       ├── config
    │       ├── optimization
    │       └── exception
    │
    └── resources
        ├── application.properties
        └── data.sql
```

Each package has a single responsibility, following separation of concerns principles.

---

## Design Principles

The project is designed according to the following principles:

- Separation of Concerns
- Single Responsibility Principle
- Layered Architecture
- Domain-Driven Design concepts
- RESTful API design
- Extensibility for future optimization features

---

## Future Improvements

Planned features include:

- Authentication and authorization using Spring Security and JWT.
- Docker Compose environment.
- Cloud deployment on AWS.
- Integration with the Gurobi Optimizer.
- Support for soft constraints.
- Multiple optimization strategies.
- Schedule comparison.
- Timetable export.
- Administrative dashboard.

---

## Version

Current architecture: MVP (Version 1)

The current architecture focuses on solving the core academic scheduling problem while maintaining a clean and extensible foundation for future enhancements.