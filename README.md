```markdown
# EduSphere: Student & Course Management API

A robust Spring Boot RESTful API for academic management, featuring bidirectional JPA mappings for Many-to-Many course enrollments and Many-to-One department bindings. Built with DTO data isolation, partial updates, Jakarta validation, and MySQL persistence via HikariCP.

---

## 🛠️ Tech Stack

* **Language:** Java 17+
* **Framework:** Spring Boot (Spring Web, Spring Data JPA, Spring Validation)
* **ORM:** Hibernate 
* **Database:** MySQL
* **Connection Pool:** HikariCP
* **Utilities:** Lombok

---

## 📌 Features

* **Relational Data Modeling:**
  * `Student` ↔ `Department` (Bidirectional `@ManyToOne` / `@OneToMany`)
  * `Student` ↔ `Course` (Bidirectional `@ManyToMany` with a custom `student_course` join table)
  * Embedded value objects (`@Embeddable Address`) inside the `Student` entity
  * Automated auditing (`createdAt`, `modifiedAt`) using an inherited `@MappedSuperclass`
* **Data Isolation with DTOs:** Flat Request DTOs and clean Java Record Response DTOs to decouple API contracts from the database schema and eliminate circular Jackson serialization loops.
* **Granular Updates:** Supports both full entity replacements (`PUT`) and selective field mutations (`PATCH`).
* **Input Validation:** Enforces strict regex validation for formats such as postal codes and dates of birth (`yyyy-MM-dd`), along with email constraints.

---

## 📋 API Endpoints

### 1. Student Endpoints (`/student`)

| Method | Endpoint | Description | Status Code |
| :--- | :--- | :--- | :--- |
| `POST` | `/student/create` | Register a new student | `201 Created` |
| `GET` | `/student/get` | Retrieve all students | `200 OK` |
| `GET` | `/student/get/{id}` | Retrieve a student by ID | `200 OK` |
| `PUT` | `/student/update/{id}` | Replace an entire student record | `200 OK` |
| `PATCH` | `/student/patch/{id}` | Partially update student fields | `200 OK` |
| `DELETE` | `/student/delete/{id}` | Remove a student record | `204 No Content` |

### 2. Department Endpoints (`/department`)

| Method | Endpoint | Description | Status Code |
| :--- | :--- | :--- | :--- |
| `POST` | `/department/create` | Create a new department | `201 Created` |
| `GET` | `/department/get` | Retrieve all departments | `200 OK` |
| `GET` | `/department/get/{id}` | Retrieve department by ID | `200 OK` |

### 3. Course Endpoints (`/course`)

| Method | Endpoint | Description | Status Code |
| :--- | :--- | :--- | :--- |
| `POST` | `/course/create` | Create a new course | `201 Created` |
| `GET` | `/course/get` | Retrieve all courses with enrolled students | `200 OK` |
| `GET` | `/course/get/{id}` | Retrieve course by ID | `200 OK` |

---

## 🧪 Sample Request & Response

### Register Student (`POST /student/create`)

**Request Body:**
```json
{
  "name": "Abinesh",
  "email": "abinesh@gmail.com",
  "dob": "2002-05-15",
  "gpa": 8.75,
  "address": {
    "street": "Anna Nagar Main Road",
    "city": "Chennai",
    "zipcode": "600040"
  },
  "departmentId": 1,
  "courseIds": [1, 2, 3]
}

```

**Response Body:**

```json
{
  "id": 1,
  "name": "Abinesh",
  "email": "abinesh@gmail.com",
  "dob": "2002-05-15",
  "gpa": 8.75,
  "address": {
    "street": "Anna Nagar Main Road",
    "city": "Chennai",
    "zipcode": "600040"
  },
  "department": {
    "id": 1,
    "name": "CSE"
  },
  "courses": [
    {
      "id": 1,
      "name": "AI",
      "credits": 3
    },
    {
      "id": 2,
      "name": "ML",
      "credits": 3
    },
    {
      "id": 3,
      "name": "cpp",
      "credits": 3
    }
  ]
}

```

---

## 🚀 Getting Started

### 1. Prerequisites

* Java Development Kit (JDK 17 or higher)
* MySQL Server (running locally on port `3306`)
* Maven

### 2. Configure Database

Create a database in MySQL:

```sql
CREATE DATABASE Project;

```

Update your `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/Project?useSSL=false&serverTimezone=UTC
spring.datasource.username=YOUR_MYSQL_USERNAME
spring.datasource.password=YOUR_MYSQL_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

```

### 3. Run the Application

Open your terminal in the root directory:

```bash
./mvnw clean spring-boot:run

```

The server will initialize on port `8080`.

```

```