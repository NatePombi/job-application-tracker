# Job Application Tracker


This is a Spring Boot application I’m building to keep track of job applications. The goal is to have a full system where I can register/login, add applications, update statuses, and eventually analyze progress.

---

What’s Working So Far:

* User registration and login with JWT authentication.

* Swagger UI is set up and working. I can test my APIs directly from the browser.

* Job Application endpoints are created (add, get, etc).

* Security is in place (you need a valid token to access protected endpoints).

---

Tech Stack:

* Spring Boot

* Spring Security (JWT based)

* Swagger/OpenAPI for API docs

* MySQL
* Mockito
* JUnit 5
* JPA Hibernate

---

Next Steps


* Improve validation on DTOs

* Write integration tests.

* Polish API responses for better readability.

---

How to Run:

1) Clone the repo

2) Run mvn spring-boot:run

3) Go to: http://localhost:8080/swagger-ui.html

4) Register a user (/auth/register), then log in (/auth/login) to get a JWT token.

5) Authorize in Swagger (top-right button), then try out the job application endpoints.