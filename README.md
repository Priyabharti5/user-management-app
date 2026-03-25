# User Management System – Full Stack Application

A full stack **User Management System** built using **Spring Boot, React, PostgreSQL, JWT Authentication**.
This project demonstrates authentication, authorization, user management, dynamic search, and modern UI.

---

## Project Overview

This application allows users to:

* Register new users
* Login securely using JWT authentication
* Add new users (Authenticated Users - Protected API)
* Search users dynamically
* View users in table format
* Logout securely

The system uses **JWT Authentication + Spring Security** to protect APIs.

---

## Tech Stack

| Layer       | Technology            |
|-------------|-----------------------|
| Frontend    | React + Tailwind CSS  |
| Backend     | Spring Boot           |
| Database    | PostgreSQL            |
| Security    | Spring Security + JWT |
| API         | REST APIs             |
| Build Tool  | Maven                 |
| HTTP Client | Axios                 |

---

## Features

### Authentication

* Login using username & password
* JWT token generation
* Token stored in localStorage
* Protected APIs using JWT
* Logout functionality (Handled in frontend)

### User Management

* Add new user
* Search users dynamically
* View users in table
* Unique username & email validation

### Validation

* Frontend form validation
* Backend validation using Bean Validation
* Error handling with proper messages

### UI Features

* Modern UI using Tailwind
* Login page
* Add User page
* Search User page
* Navigation bar

---

## Project Architecture

```
Frontend (React)
        |
        |  HTTP Requests (Axios + JWT)
        ↓
Backend (Spring Boot)
        |
        |  JPA
        ↓
PostgreSQL Database
```

### Backend Layers

```
Controller → Service → Repository → Database
                ↓
              Mapper
                ↓
               DTO
```

---

## Project Structure

### Backend Structure

```
user-management-backend
│
├── controller
├── service
├── repository
├── entity
├── dto
├── mapper
├── security
├── config
└── exception

```

### Frontend Structure

```
user-management-ui
│
├── pages
├── components
├── services
├── hooks
├── utils
├── constants
├── App.jsx
└── index.js

```

---

## Local Setup

### 1. Clone Project

```
git clone https://github.com/Priyabharti5/user-management-system
cd user-mgm-app-backend

```

### 2. Backend Setup

#### Create Database

Create PostgreSQL database:

```
user_management_db

```

#### Update application.yml

```
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/user_management_db
    username: postgres
    password: postgres
    
```

### 3. Run Backend

```
mvn clean install
mvn spring-boot:run

```

Backend runs on:

```
http://localhost:8080

```

---

### 4. Frontend Setup

```
cd user-management-ui
npm install
npm start

```

Frontend runs on:

```
http://localhost:3000

```

---

## API Endpoints

### Public APIs

#### Login

```
POST /api/auth/login

```

Request:

```
{
  "username": "admin",
  "password": "admin123"
}

```

---

### Protected APIs (JWT Required)

#### Add User

```
POST /api/users/add
Authorization: Bearer Token

```

### Search Users

```
POST /api/users/search
Authorization: Bearer Token

```

---

## Security

| Feature             | Description                                  |
|---------------------|----------------------------------------------|
| JWT Authentication  | Token based authentication                   |
| Password Encryption | BCrypt                                       |
| Protected APIs      | Spring Security                              |
| CORS                | Enabled for frontend and backend interaction |
| Validation          | Frontend + Backend                           |
| Exception Handling  | Global Exception Handler                     |

---

## Testing Flow

### Test Flow

1. Start Backend
2. Start Frontend
3. Open application
4. Login using admin user [username: "admin", password: "admin123"]
5. Add new users
6. Search users
7. Logout

---

## What This Project Demonstrates

This project demonstrates:

* Spring Boot layered architecture
* DTO & Mapper pattern
* JWT Authentication
* Spring Security configuration
* React Hooks & Routing
* Axios API integration
* Form validation
* Dynamic search
* Exception handling
* Full stack integration

---

## Author

**Priya Bharti**

GitHub: https://github.com/Priyabharti5

Email: [priyabharti315@gmail.com](mailto:priyabharti315@gmail.com)

---
