# Smart Clinic Management System - Architecture Design

## Section 1: Architecture Summary

This Spring Boot application implements a comprehensive three-tier web architecture that serves both traditional web users and modern API consumers. The system uses a hybrid approach with Spring MVC controllers for server-rendered Thymeleaf templates powering the Admin and Doctor dashboards, while REST API controllers serve all other modules including appointments, patient dashboards, and patient records. 

The application integrates with dual databases to optimize data storage - MySQL handles structured relational data for core entities like patients, doctors, appointments, and admin records using Spring Data JPA, while MongoDB stores flexible document-based prescription data through Spring Data MongoDB. This architecture ensures scalability and maintainability by routing all requests through a unified service layer that applies business logic and coordinates with appropriate repositories. The clean separation of concerns allows for independent development, testing, and scaling of each tier while maintaining strong data consistency and supporting both browser-based interactions and API-driven integrations.

## Section 2: Numbered Flow of Data and Control

1. **User Interface Interaction**: Users access the system through two main channels - web browsers accessing Thymeleaf-rendered HTML dashboards (AdminDashboard and DoctorDashboard) or external clients consuming REST APIs for appointment scheduling, patient records, and patient dashboard functionality.

2. **Request Routing to Controllers**: Incoming requests are routed to appropriate Spring Boot controllers based on URL paths and HTTP methods. Thymeleaf controllers handle server-side rendering requests and return HTML templates, while REST controllers process API requests and prepare JSON responses.

3. **Service Layer Processing**: All controllers delegate business logic to the Service Layer, which acts as the application's core processing engine. This layer enforces business rules, performs data validation, coordinates complex workflows across multiple entities, and maintains the separation between presentation logic and data access operations.

4. **Repository Layer Data Access**: The service layer communicates with the Repository Layer to perform all database operations. This includes MySQL repositories using Spring Data JPA for structured relational data management and MongoDB repositories using Spring Data MongoDB for flexible document storage and retrieval.

5. **Database Interaction**: Repositories interface directly with their respective database engines - MySQL for normalized relational data with strong consistency requirements, and MongoDB for flexible schema documents like prescriptions that may evolve over time and require rapid development cycles.

6. **Model Binding and Transformation**: Retrieved data is automatically mapped into Java model classes through framework-provided binding mechanisms. MySQL data becomes JPA entities annotated with @Entity, while MongoDB data transforms into document objects annotated with @Document, providing consistent object-oriented representations across the application.

7. **Response Generation and Delivery**: Finally, the processed models are used to generate appropriate responses - in MVC flows, models populate Thymeleaf templates to render dynamic HTML pages for browser display, while in REST flows, the same models are serialized into JSON format and delivered as HTTP API responses to consuming clients.
