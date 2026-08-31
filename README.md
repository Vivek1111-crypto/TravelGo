#  TravelGo – Travel & Hotel Booking System

TravelGo is a **Spring Boot Microservices-based Travel and Hotel Booking System**.
The application allows users to authenticate, browse properties and rooms,
and create and manage hotel bookings.

The project follows a **Microservices Architecture** with service discovery,
API Gateway, JWT-based authentication, and inter-service communication.

---

##  Architecture

```text
                         ┌─────────────────────┐
                         │       Client        │
                         └──────────┬──────────┘
                                    │
                                    ▼
                         ┌─────────────────────┐
                         │     API Gateway     │
                         └──────────┬──────────┘
                                    │
                    ┌───────────────┼───────────────┐
                    │               │               │
                    ▼               ▼               ▼
             ┌────────────┐ ┌────────────┐ ┌──────────────┐
             │    Auth    │ │  Property  │ │   Booking    │
             │  Service   │ │  Service   │ │   Service    │
             └────────────┘ └────────────┘ └──────────────┘
                    │               │               │
                    └───────────────┼───────────────┘
                                    │
                                    ▼
                         ┌─────────────────────┐
                         │   Eureka Server     │
                         │ Service Discovery   │
                         └─────────────────────┘

 Microservices
1.  Auth Service

Responsible for:

User registration
User login
JWT authentication
JWT token generation
Role-based authorization
User management
Refresh token functionality

2. Property Service

Responsible for:

Property management
Room management
Adding properties
Adding rooms
Retrieving property information
Retrieving room information

3.  Booking Service

Responsible for:

Creating bookings
Retrieving booking details
Managing booking information
Validating property and room information
Communication with Property Service

4.  API Gateway

The API Gateway acts as the single entry point for client requests.

Responsibilities:

Request routing
Centralized entry point
JWT validation
Forwarding requests to appropriate microservices

5.  Eureka Server

Eureka Server provides service discovery.

Each microservice registers itself with Eureka, allowing services to
discover and communicate with each other without hardcoding service
locations.

Tech Stack
-------------
Technology	                 Purpose
Java	                     Programming Language
Spring                    Boot	Backend Framework
Spring                   Cloud	Microservices Infrastructure
Spring                  Security	Authentication & Authorization
Spring                  Data JPA	Database Access
Spring Cloud Gateway	   API Gateway
Netflix Eureka	          Service Discovery
OpenFeign	                Inter-Service Communication
JWT	                       Authentication
MySQL                     	Database
Maven                     	Build Tool
Git & GitHub                 Version Control


Authentication & Authorization

TravelGo uses JWT-based authentication.

The authentication flow is:
User
  │
  ▼
API Gateway
  │
  ▼
Auth Service
  │
  ├── Validate Credentials
  │
  └── Generate JWT
          │
          ▼
       Client
          │
          │ JWT Token
          ▼
   Protected Services
Protected APIs validate the JWT token before allowing access.

The application also supports role-based authorization.

Booking Flow

A typical booking request follows this flow:
Client
   │
   ▼
API Gateway
   │
   ▼
Booking Service
   │
   ▼
Property Service
   │
   ▼
Validate Property / Room
   │
   ▼
Create Booking
   │
   ▼
Database

Project Structure
TravelGo/
│
├── TravelGo-auth-service1/
│
├── travelgo-Eureka-server1/
│
├── travelgo-api-gateway/
│
├── travelgo-Booking-service/
│
└── travelgo-property-service/

How to Run
Prerequisites

Make sure the following are installed:

Java
Maven
MySQL
Git

Start Services

Start the services in the following order
1. Eureka Server
2. Auth Service
3. Property Service
4. Booking Service
5. API Gateway
Each service can be started as a Spring Boot application from STS
or using Maven.
Configuration

Each microservice contains its own:
application.properties

Configure the required:

Database URL
Database username
Database password
Server port
Eureka server URL
JWT configuration
Other environment-specific properties

Do not commit real passwords, API keys, JWT secrets, or other sensitive
credentials to GitHub.
Key Features
Microservices Architecture
 API Gateway
 Service Discovery using Eureka
 JWT Authentication
 Role-Based Authorization
 User Registration and Login
 Property Management
 Room Management
 Hotel Booking
 Inter-Service Communication
 REST APIs
 Centralized request routing

Future Enhancements
Payment Service
Notification Service
Kafka-based asynchronous communication
Frontend Integration
Docker containerization
Centralized configuration
Distributed tracing
CI/CD pipeline
Cloud deployment


Author

Vivek

GitHub: Vivek1111-crypto

### Step 2 — Preview it

After pasting:

1. Click **Preview** at the top.
2. Check how the README looks.
3. If everything looks good, scroll **all the way down**.

### Step 3 — Commit the README

At the bottom you'll see **Commit changes**.

For the commit message, enter:

```text
Add project README
