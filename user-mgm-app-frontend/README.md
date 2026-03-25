# 👥 User Management App – Full Stack REST API

![React](https://img.shields.io/badge/React-19.2.0-61DAFB?logo=react)  
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-brightgreen?logo=springboot)  
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Latest-316192?logo=postgresql)  
![JWT](https://img.shields.io/badge/JWT-Authentication-blue)  
![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)

---

## 📋 Table of Contents

- [**Overview**](#-overview)
- [**Tech Stack**](#-tech-stack)
- [**Features**](#-features)
- [**Architecture**](#-architecture)
- [**Getting Started**](#-getting-started)
- [**API Documentation**](#-api-documentation)
- [**Project Structure**](#-project-structure)
- [**Testing**](#-testing)

---

## 🎯 Overview

- **User Management App** is a modern, full-stack web application that demonstrates **professional authentication, form validation, and RESTful API integration**. 
- It allows users to register, login with JWT tokens, and search users in a secure, scalable manner.

- Built as a **practical demonstration** of:
  - ✅ Secure user authentication (JWT + Spring Security)
  - ✅ Clean separation of concerns (Frontend ↔ Backend)
  - ✅ Form validation (Client-side + Server-side)
  - ✅ Protected API endpoints
  - ✅ Responsive UI with modern frameworks

- **Perfect for**: Portfolio projects, startup MVPs, and learning full-stack development.

---

## 🛠️ Tech Stack

| Layer          | Technology                        | Purpose                      |
|----------------|-----------------------------------|------------------------------|
| **Frontend**   | React 19 + Tailwind CSS           | Modern UI, responsive design |
| **Backend**    | Spring Boot 3.5.7                 | REST APIs, business logic    |
| **Database**   | PostgreSQL                        | Data persistence             |
| **Auth**       | JWT + Spring Security             | Secure authentication        |
| **Validation** | React Hook Form + Bean Validation | Client & server validation   |
| **HTTP**       | Axios                             | API communication            |
| **Routing**    | React Router v7                   | Page navigation              |

---

## ✨ Features

### 🔐 Authentication
- User registration with email verification
- Secure login with JWT token generation
- Password encryption (BCrypt)
- Token persistence with automatic refresh

### 👤 User Management
- Create user accounts with validation
- Search users by (userId, username, firstName, lastName, email)
- View user profiles (protected routes)
- Logout & session management

### ✔️ Validation
- **Frontend**: Real-time form validation with inline error messages
- **Backend**: Server-side validation with detailed error responses
- Username uniqueness check
- Email format validation
- Password strength requirements

### 📱 UI/UX
- Clean, responsive design (mobile-first)
- Loading spinners during API calls
- Toast notifications (success/error)
- Protected routes with automatic redirects
- Seamless navigation between pages

---

## 🏗️ Architecture

```
┌─────────────────────────────────────────────────────────┐
│                  FRONTEND (React)                       │
│  ┌──────────────────────────────────────────────────┐   │
│  │  Pages: Home | Login | Register | User List      │   │
│  └──────────────────────────────────────────────────┘   │
│  ┌──────────────────────────────────────────────────┐   │
│  │  Services: API Integration | Error Handling      │   │
│  └──────────────────────────────────────────────────┘   │
└──────────────────┬──────────────────────────────────────┘
                   │ Axios (JWT Token)
                   ▼
┌─────────────────────────────────────────────────────────┐
│              API GATEWAY (Port 8080)                    │
│          - JWT Validation | CORS Handling               │
└──────────────────┬──────────────────────────────────────┘
                   │
        ┌──────────┴──────────┐
        ▼                     ▼
    POST /users/add   POST /users/search
    POST /auth/login   (Protected)
        ▼
┌─────────────────────────────────────────────────────────┐
│          BACKEND (Spring Boot 3.5.7)                    │
│  ┌──────────────────────────────────────────────────┐   │
│  │ Controllers | Services | Repositories | Security │   │
│  └──────────────────────────────────────────────────┘   │
└──────────────────┬──────────────────────────────────────┘
                   │
                   ▼
        ┌──────────────────────┐
        │   PostgreSQL DB      │
        │  - users table       │
        │  - auditing fields   │
        └──────────────────────┘
```

---

## 🚀 Getting Started

### Prerequisites
- **Node.js** v18+ (Frontend)
- **Java** 21 (Backend)
- **PostgreSQL** running locally

### Backend Setup

```bash
# 1. Clone repository
git clone https://github.com/priya/user-management-app.git
cd user-management-app/backend

  # 2. Create PostgreSQL database
createdb user_management

# 3. Update application.yaml
# Edit: src/main/resources/application.yaml
# Set: datasource.url, datasource.username, datasource.password

# 4. Build & run      
mvn clean install
mvn spring-boot:run

# Server runs on http://localhost:8080
```

### Frontend Setup

```bash
  # 1. Navigate to frontend
cd ../user-management-ui

# 2. Install dependencies
npm install

# 3. Start development server
npm start
# App opens at http://localhost:3000
```

### Verify Setup
- **Backend Health**: `curl http://localhost:8080/actuator/health`
- **Frontend**: `http://localhost:3000` (should load Home page)

---

## 📡 API Documentation

### Authentication

**Login** (Get JWT Token)
```
POST /api/auth/login
Body: { userName: string, password: string }
Response: { token: string, username: string }
```

**Register** (Create User)
```
POST /api/users/add
Body: { userName, firstName, lastName, email, password }
Response: { status: string, userId: number }
```

### Protected Endpoints (Require JWT Token)

**Search Users**
```
POST /api/users/search
Headers: Authorization: Bearer {token}
Body: { userId?, userName?, firstName?, lastName?, email? }
Response: User[] (filtered results)
```

---

## 📁 Project Structure

### Backend
```
backend/
├── src/main/java/com/pb/user_management/
│   ├── config/          (Security, JPA Auditing, OpenAPI)
│   ├── controller/      (REST endpoints)
│   ├── service/         (Business logic)
│   ├── repository/      (Database queries)
│   ├── entity/          (JPA models)
│   ├── dto/             (Data transfer objects)
│   ├── exception/       (Custom exceptions)
│   └── mapper/          (Entity ↔ DTO conversion)
└── application.yaml     (Database & port config)
```

### Frontend
```
frontend/
├── src/
│   ├── pages/           (HomePage, LoginPage, RegisterPage, UserListPage)
│   ├── components/      (Navbar, Loader, reusable UI)
│   ├── services/        (API calls, axios setup)
│   ├── hooks/           (useAuth - authentication context)
│   ├── utils/           (errorHandler, helpers)
│   ├── constants.js     (API endpoints, validation rules)
│   ├── App.jsx          (Router setup)
│   └── index.js         (Entry point)
```

---

## ✅ Testing

### Manual Testing Workflow

**1. Register User**
```
Home → Register → Fill form → Submit
    ✅ See success message with userId   
```

**2. Login**
```
Home → Login → Enter credentials → Submit
    ✅ JWT token saved | Redirected to /users page
```

**3. Search Users**
```
On /users page → Enter search filters → Click Search
    ✅ Table updates | Shows result count
```

**4. Logout**
```
Click Logout button
    ✅ Redirected to /login | localStorage cleared
```

### Error Scenarios
- ❌ Duplicate username → See error toast
- ❌ Invalid email → Validation error shown inline
- ❌ Network down → Graceful error message
- ❌ Invalid token → Auto-redirect to login

---

## 🔒 Security Features

- ✅ **JWT Authentication** - Stateless, secure token-based auth
- ✅ **BCrypt Password Hashing** - Industry-standard encryption
- ✅ **CORS Configuration** - Safe cross-origin requests
- ✅ **Role-Based Access Control** - Protected endpoints
- ✅ **Input Validation** - Client + Server-side
- ✅ **JPA Auditing** - Track who created/modified data

---

## 🤝 Contributing

We welcome contributions! Here's how:

1. Fork the repository
2. Create feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Open Pull Request

---

## 📄 License

This project is licensed under the **MIT License** - see LICENSE file for details.

---

## 🎓 Learning Outcomes

By studying this project, you'll understand:
- ✅ Full-stack web development (React + Spring Boot)
- ✅ RESTful API design principles
- ✅ JWT authentication flow
- ✅ Form validation (client & server)
- ✅ Error handling strategies
- ✅ Component architecture & hooks
- ✅ Context API for state management

---

###### *Built with ❤️ for learning and practical demonstration* 🚀

---

## 👨‍💻 Author

**Priya Bharti**

[![GitHub](https://img.shields.io/badge/GitHub-100000?style=flat-square&logo=github&logoColor=white)](https://github.com/yourusername)  
[![LinkedIn](https://img.shields.io/badge/LinkedIn-0A66C2?style=flat-square&logo=linkedin&logoColor=white)](https://linkedin.com/in/yourprofile)  
[![Email](https://img.shields.io/badge/Email-D14836?style=flat-square&logo=gmail&logoColor=white)](mailto:your.email@gmail.com)