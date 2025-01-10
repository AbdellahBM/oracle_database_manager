Overview
This project delivers a comprehensive Oracle database management system utilizing Spring Boot for the backend and React for the frontend. The system facilitates database management tasks such as backup and recovery, performance optimization, security configurations, and Data Guard operations. The project is fully containerized using Docker and orchestrated with Docker Compose for simplified deployment.

Backend (Spring Boot)

Project Structure

Package Structure:
com.ma.oracle: Contains all backend-related components:
Controller: Manages RESTful APIs for HTTP request handling.
Service: Implements business logic.
Repository: Handles database operations with JPA and JdbcTemplate.
Config: Contains configurations for security, scheduling, and CORS.
Entite: Represents database entities.
DTO: Defines Data Transfer Objects for request and response payloads.




Controllers





DataGuardController.java


OracleBackUpController.java



Service
DatabaseController.java


Repository



BackupHistory.java




Key Features:
Database Backup and Recovery:


Full and incremental backups.
Tablespace-specific backups.
Point-in-time recovery.

Performance Optimization:


Analyze top SQL queries.
Monitor CPU usage, disk I/O, and active sessions.
Identify top wait events.
Security Configurations:


Enable Transparent Data Encryption (TDE).
Audit SQL actions.
Apply Virtual Private Database (VPD) policies.
Data Guard Operations:


Perform switch-over and failover operations.
Simulate Data Guard processes.
User Management:


Manage Oracle users (create, update, delete).
Configure roles and tablespace quotas.
Scheduling:


Automate full backups with Quartz.
Recalculate statistics using Oracle Scheduler.
Dependencies:
Spring Boot Starter Data JPA and Quartz for database interaction and task scheduling.
Oracle JDBC driver for database connectivity.
Lombok for reducing boilerplate code.
Spring Security for API protection.
Configurations:
application.properties: Stores database connection settings.
SecurityConfig: Manages authentication and authorization.
SchedulerConfig: Activates Quartz scheduling.
CorsConfig: Allows cross-origin API requests
application.properties


Technical Overview of the Project

1. Project Structure and Build Management
Build Tool: The application is built using Maven, configured via the pom.xml file. Maven handles the build lifecycle, dependency management, and plugins for tasks such as packaging and testing.


Maven Wrapper: Included in the project (mvnw and mvnw.cmd) to ensure consistent Maven execution across all environments, regardless of local installations or operating systems.


Key Dependencies:


Spring Boot Starters: Simplifies setup for web, data JPA, Quartz, and security modules.
Oracle JDBC Driver: Enables connectivity with Oracle databases.
Lombok: Reduces boilerplate with annotations for getters, setters, and constructors.
Logback/SLF4J: Implements robust logging.
2. Spring Boot Configuration
Application Properties: The application.properties file includes Oracle database configurations, such as URL, username, password, and other JPA-related properties.


Spring Boot Parent: The project inherits configurations and dependency management from spring-boot-starter-parent.


Java Version: Configured to use Java 23 for enhanced language features and performance.


3. Controllers and REST Endpoints
RESTful APIs: Key controllers expose endpoints for specific operations:
Database Management: Connects to the database and fetches table details.
Performance Optimization: Provides endpoints to monitor and optimize slow-running queries, manage active sessions, and retrieve statistics.
Data Guard Operations: Handles switch-over, failover, and Data Guard simulations.
Security Settings: Manages TDE, auditing, and VPD configurations.
User Management: APIs for Oracle user creation, update, and role management.

4. Service Layer
Business Logic: The service layer encapsulates business rules and interacts with repositories to process data. Examples include:
Backup Management: Handles Oracle database backups and restoration.
Performance Services: Optimizes database queries and recalculates statistics.
Security Services: Applies security policies such as encryption and auditing.

5. Repository Layer
Database Interactions:
JPA Repositories: Simplifies CRUD operations for entities like users, backup logs, and roles.
Custom SQL Execution: Uses JdbcTemplate for more complex database queries.




6. Security and Scheduling
Security Configuration:


Uses Spring Security for basic API protection.
Configures Cross-Origin Resource Sharing (CORS) to allow interactions with the frontend application.
Scheduled Tasks: Automates recurring jobs, such as:


Daily backups.
Regular recalculation of database statistics using Quartz.

7. Logging and Monitoring
Logback: Configured for structured application logging.
Custom Logging Levels: Fine-grained control for monitoring and debugging, configured through application.properties.

8. Entities and DTOs
Entities: Represent database tables with JPA annotations for persistence mapping.
DTOs: Facilitate structured data exchange between the frontend, backend, and database.











Front-End ( React )
Overview
This React-based front-end application is designed as a management dashboard for an Oracle database system. It provides features such as monitoring database status, managing backups, monitoring performance, ensuring security, and handling user management. The application leverages modularity and reusability by organizing functionality into distinct components.

Technologies and Tools
Framework: React with TypeScript for robust type-checking.
UI Styling: Tailwind CSS for utility-first styling.
Icons: Lucide-React for lightweight and customizable icons.
Build Tool: Vite for fast development and optimized builds.

Core Features
Database Status Monitoring:


Displays key information like connection status, database role, and archive mode.
Uses reusable StatusCard components for consistent design and modularity.
Backup Management:


Enables users to perform full, incremental, or archive log backups via an intuitive interface.
Utilizes asynchronous API calls (fetch) to interact with backend endpoints.
Provides visual feedback for ongoing actions and results using conditional styling (e.g., green for success, red for errors).
Includes a Backup History section for tracking past backup actions.
Performance Monitoring:


Displays performance metrics such as CPU usage and active sessions.
Offers insights into top SQL queries with a tabular view.
Uses a MetricCard component for dynamic rendering of performance metrics.
Security Management:


Shows the status of security features like Transparent Data Encryption (TDE), audit status, and VPD policies.
Displays an audit log for tracking sensitive actions.
Security information is presented via reusable SecurityCard components with appropriate visual cues.
User Management:


Allows user account creation, role management, and user deactivation.
Lists all users with their statuses, roles, and available actions.
Uses functional buttons for interaction, such as Create User and Manage Roles.

Application Layout
Navigation:


A responsive sidebar for switching between sections.
Active sections are highlighted with visual indicators for improved usability.
Mobile-friendly with a collapsible menu toggle.
Content:


Organized into modular sections rendered dynamically based on the active selection.
Each section is isolated in its component, ensuring code maintainability.



