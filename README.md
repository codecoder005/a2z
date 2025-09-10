# 🚀 A2Z Project

A2Z is a **Spring Boot–based user and enrollment management system**.  
It provides RESTful APIs for **user operations**, **flexible search**, and **enrollments**, along with a **health check API**.  
The project uses **Spring Boot, Spring Data JPA, Lombok**, and follows **clean architecture** principles.

---

## ✨ Features

- 👤 **User Management**
    - Retrieve a single user by ID
    - Fetch all users
    - Search users with flexible filters

- 📝 **Enrollment API**
    - Validate and enroll new users with strict constraints
    - Built-in support for validation annotations (`@NotNull`, `@Pattern`, `@Future`, etc.)
    - Automatic mapping between DTOs

- 🔎 **Search Options**
    - Operators: `EQUALS`, `NOT_EQUALS`, `LIKE`, `LESS_THAN`, `GREATER_THAN`
    - Sorting: `ASC` / `DESC`

- 🩺 **Health Check**
    - `/ping` endpoint to verify service availability

- ⚡ **Exception Handling**
    - `AppException`, `UserNotFoundException` for clear error reporting
    - Errors returned as **Problem Details JSON**

---

## 🏗️ Project Structure

```
a2z
 ├── domain
 │   ├── request
 │   │   ├── SearchUsersRequest.java
 │   │   └── EnrollmentRequest.java
 │   ├── response
 │   │   ├── UserDTO.java
 │   │   ├── EnrollmentResponse.java
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
 │   ├── PingAPI.java
 │   └── EnrollmentAPIController.java
 │
 ├── enums
 │   ├── SearchStrategy.java
 │   └── SortingOrder.java
 │
 └── Application.java
```

---

## 📚 Domain Models

### `EnrollmentRequest`
Represents a user enrollment request with validation constraints.

**Key fields:**
- `isChallenged`, `isSalaried` → Boolean flags (`@AssertFalse`, `@AssertTrue`)
- `age`, `height`, `weight`, `salary`, `revenue` → Numeric with limits
- `email`, `username`, `password`
- `dob`, `attendedOn`, `travellingOn`, `maturityDate`
- `searchPattern`
- `papers` (non-empty list)
- `EnrollmentAddress`

**Example:**
```json
{
  "isChallenged": false,
  "isSalaried": true,
  "age": 25,
  "height": 175,
  "weight": 70.5,
  "salary": 25000,
  "revenue": 1500000,
  "email": "jdoe@example.com",
  "username": "jdoe",
  "password": "StrongPass@123",
  "firstName": "John",
  "lastName": "Doe",
  "gender": "MALE",
  "dob": "2000-01-15",
  "attendedOn": "2025-09-01",
  "travellingOn": "2025-09-20",
  "maturityDate": "2026-09-10",
  "searchPattern": ".*",
  "papers": ["ID_PROOF", "ADDRESS_PROOF"],
  "address": {
    "addressLine1": "123 Main St",
    "city": "New York",
    "state": "NY",
    "country": "USA",
    "zipcode": "10001",
    "addressType": "PERMANENT"
  }
}
```

---

### `EnrollmentResponse`
Represents the outcome of an enrollment request.
- Contains validated and mapped fields from `EnrollmentRequest`

---

### `UserDTO`
Represents a persisted user.
- Identity: `userEntityPK`
- Profile: `username`, `firstName`, `lastName`, `email`, `gender`
- Audit: `createdBy`, `createdOn`, `updatedBy`, `updatedOn`

---

### `SearchUsersRequest`
Flexible user search query.
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

### 2. **Enrollment API**
- **Endpoint:** `POST /enroll`
- **Request:** `EnrollmentRequest`
- **Response (201 Created):**
```json
{
  "username": "jdoe",
  "firstName": "John",
  "lastName": "Doe",
  "email": "jdoe@example.com",
  "gender": "MALE",
  "dob": "2000-01-15",
  "maturityDate": "2026-09-10"
}
```

---

### 3. **Get User by ID**
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

### 4. **Get All Users**
- **Endpoint:** `GET /api/v1/users`
- **Response:** List of `UserDTO`

---

### 5. **Search Users**
- **Endpoint:** `POST /search`
- **Request Body:** `SearchUsersRequest`
- **Response:** Filtered list of `UserDTO`

---

## ⚠️ Exception Handling

- `AppException` → Application-level errors
- `UserNotFoundException` → Missing user case
- **Validation errors** → Returned as `400 Bad Request` with field-specific messages

Example error:
```json
{
  "type": "about:blank",
  "title": "Bad Request",
  "status": 400,
  "detail": "'age' should not be less than 18",
  "instance": "/enroll"
}
```

---

## ⚙️ Tech Stack

- **Java 21+**
- **Spring Boot 3.5.5+**
- **Spring Data JPA + Hibernate**
- **Lombok**
- **Maven**
- **ModelMapper**

---

## 🚀 Getting Started

### Prerequisites
- Java 21+
- Maven 3.9+
- PostgreSQL / MySQL

### Build & Run
```bash
# Build
mvn clean install

# Run
mvn spring-boot:run
```

### Access APIs
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- Health Check: `http://localhost:8080/ping`

---

## 🧑‍💻 Developer Notes

- Run `mvn javadoc:javadoc` for API docs (`target/site/apidocs`)
- Branch naming: `feature/JIRA-123-description`
- Tests should cover:
    - Enrollment validation
    - User retrieval
    - Search queries
    - Exception handling

---

## 📜 License

Licensed under the **MIT License** – free to use and modify.  
