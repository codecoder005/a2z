# A2Z Project

A2Z is a **Spring Boot–based application** designed to manage users efficiently.  
It provides RESTful APIs to create, search, and retrieve users, along with a simple health check API.  
This project uses **Spring Boot, Spring Data JPA, Lombok**, and follows clean architecture practices.

---

## ✨ Features

- 📌 **User Management**
    - Retrieve a single user by ID
    - Fetch all users
    - Search users with flexible filters (field, strategy, sorting)

- 🔎 **Search Options**
    - Rich query support with `EQUALS`, `NOT_EQUALS`, `LIKE`, `LESS_THAN`, `GREATER_THAN`, etc.
    - Sorting support (`ASC`, `DESC`)

- 🩺 **Health Check**
    - `/ping` endpoint to verify service availability

- ⚡ **Exception Handling**
    - Custom exceptions (`AppException`, `UserNotFoundException`) for clearer error reporting

---

## 🏗️ Project Structure

```
a2z
 ├── domain
 │   ├── request
 │   │   └── SearchUsersRequest.java
 │   ├── response
 │   │   ├── UserDTO.java
 │   │   └── PingAPIResponse.java
 │   └── model
 │       ├── UserEntity.java
 │       └── UserEntityPK.java
 │
 ├── exception
 │   ├── AppException.java
 │   └── UserNotFoundException.java
 │
 ├── repository
 │   └── UserRepository.java
 │
 ├── controller
 │   ├── UserRestAPI.java
 │   └── PingAPI.java
 │
 ├── enums
 │   ├── SearchStrategy.java
 │   └── SortingOrder.java
 │
 └── Application.java
```

---

## 📚 Domain Model

### `UserDTO`
Represents a user in the system.  
Contains identity, profile, and audit fields:
- `userEntityPK`
- `username`, `firstName`, `lastName`, `email`, `password`
- `gender`
- `createdBy`, `createdOn`, `updatedBy`, `updatedOn`

---

### `SearchUsersRequest`
Allows flexible searching of users.
- `searchOptions` → List of field-based conditions
- `sortBy` → Field to sort by (default: `userId`)
- `sortingOrder` → ASC / DESC

#### Example Search
```json
{
  "searchOptions": [
    { "field": "email", "data": "example@gmail.com", "searchStrategy": "EQUALS" },
    { "field": "firstName", "data": "Ram", "searchStrategy": "LIKE" }
  ],
  "sortBy": "createdOn",
  "sortingOrder": "DESC"
}
```

---

## 🌐 REST APIs

### 1. **Ping API**
- **Endpoint:** `GET /ping`
- **Response:**
```json
{
  "status": 200,
  "message": "pong",
  "timestamp": "2025-09-06T08:30:00"
}
```

---

### 2. **Get User by ID**
- **Endpoint:** `GET /api/v1/users/{userId}`
- **Response:**
```json
{
  "username": "jdoe",
  "firstName": "John",
  "lastName": "Doe",
  "email": "jdoe@example.com",
  "gender": "MALE",
  "createdOn": "2025-09-01T12:30:00"
}
```

---

### 3. **Get All Users**
- **Endpoint:** `GET /api/users/v1`
- **Response:** List of `UserDTO`

---

### 4. **Search Users**
- **Endpoint:** `POST /search`
- **Request Body:** `SearchUsersRequest`
- **Response:** Filtered list of `UserDTO`

---

## ⚠️ Exception Handling

- `AppException` → Base class for application-level errors
- `UserNotFoundException` → Raised when a user is missing
- All errors are returned as **Problem Details JSON** with HTTP status codes.

---

## ⚙️ Tech Stack

- **Java 21+**
- **Spring Boot 3.5.5+**
- **Spring Data JPA**
- **Hibernate**
- **Lombok**
- **Maven**

---

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Maven 3.9+
- Database (e.g., PostgreSQL / MySQL)

### Build & Run
```bash
# Build
mvn clean install

# Run
mvn spring-boot:run
```

### Access APIs
- Swagger / OpenAPI: `http://localhost:8080/swagger-ui.html`
- Health Check: `http://localhost:8080/ping`

---

## 🧑‍💻 Developer Notes

- Use `mvn javadoc:javadoc` to generate JavaDocs (`target/site/apidocs`)
- Follow branch naming convention: `feature/JIRA-123-description`
- Unit tests and integration tests should cover:
    - User retrieval
    - Search queries
    - Exception handling

---

## 📜 License

This project is licensed under the MIT License – feel free to use and modify it.
