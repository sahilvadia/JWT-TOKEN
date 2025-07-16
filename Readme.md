# JWT Token Project

This project provides a foundational implementation of JWT (JSON Web Token) based authentication and authorization within a Spring Boot application. It demonstrates how to secure RESTful API endpoints using a stateless approach, where user authentication is managed through self-contained tokens rather than server-side sessions.

## Overview

This application serves as a robust example for implementing modern authentication flows. It focuses on:

* **Stateless Authentication:** Eliminating the need for server-side sessions, enhancing scalability and simplifying horizontal deployment.
* **Secure API Endpoints:** Protecting resources by requiring valid JWTs for access.
* **Clear Separation of Concerns:** Demonstrating the roles of authentication, token generation, and authorization within a Spring Security context.

## Features

* **User Authentication:** Supports username and password-based login to obtain a JWT.
* **JWT Generation:** Creates signed JWTs upon successful authentication, containing user identity and roles.
* **JWT Validation:** Verifies incoming JWTs for authenticity, integrity, and expiration.
* **Role-Based Authorization:** Restricts access to specific API endpoints based on user roles (e.g., `USER`, `ADMIN`).
* **Stateless Session Management:** Configured to explicitly avoid HTTP sessions for security context, ideal for REST APIs.
* **Basic Authentication Support:** Configured for simple client-side testing (e.g., with Postman).
* **Form Login Support:** Provides a default login page for browser-based interaction.
* **Markdown (`.md`) format** for documentation.

## Technologies Used

* **Java 17+**
* **Spring Boot 3.x.x**
* **Spring Security 6.x.x** (for authentication and authorization)
* **JJWT (Java JWT)** library (for token creation and parsing)
* **Maven** (for dependency management and build automation)
* **BCryptPasswordEncoder** (for secure password hashing - *recommended*)
* **In-memory User Storage** (for demonstration purposes, easily extensible to database)

## Setup and Running

Follow these steps to get the project up and running on your local machine.

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/sahilvadia/JWT-TOKEN.git](https://github.com/sahilvadia/JWT-TOKEN.git)
    cd JWT-TOKEN
    ```

2.  **Configure the JWT Secret Key:**
    * Create a file named `application.properties` in the `src/main/resources/` directory.
    * **Generate a strong, random, Base64-encoded key.** You can use an online tool or a command like `openssl rand -base64 32` (for a 256-bit key suitable for HS256).
    * Add the following line to your `application.properties`, replacing `your_secure_and_static_base64_encoded_secret_key_here` with your generated key:
        ```properties
        # src/main/resources/application.properties

        # JWT Secret Key - CRITICAL FOR SECURITY!
        # This key MUST be kept secret and consistent across all application instances.
        jwt.secret=your_secure_and_static_base64_encoded_secret_key_here

        # Optional: Configure in-memory users for quick testing (overrides application.properties if UserDetailsService bean is defined)
        # spring.security.user.name=user
        # spring.security.user.password=password
        # spring.security.user.roles=USER
        ```
    * **Important:** Never commit your actual `jwt.secret` to version control. Use environment variables or a secrets management system in production.

3.  **Build the project:**
    * **For Maven:**
        ```bash
        mvn clean install
        ```
    * **For Gradle:**
        ```bash
        ./gradlew build
        ```

4.  **Run the application:**
    * **For Maven:**
        ```bash
        mvn spring-boot:run
        ```
    * **For Gradle:**
        ```bash
        ./gradlew bootRun
        ```
    The application will start on `http://localhost:8080` by default.

## Usage

This application provides endpoints for authentication and accessing secured resources.

### 1. Obtain JWT Token (Login)

Send a `POST` request to the `/login` endpoint with valid username and password credentials.

* **Endpoint:** `POST http://localhost:8080/login`
* **Authentication:** Use HTTP Basic Authentication with the credentials defined in `SecurityConfig.java` (e.g., `user:user` or `admin:adminpass` if you used `BCryptPasswordEncoder` and encoded them, or `admin:admin` if `NoOpPasswordEncoder`).

**Example (using cURL):**
```bash
curl -X POST -u user:user http://localhost:8080/login
````

Upon successful authentication, the server will return a JWT in the response body.

### 2\. Access Secured Endpoints

Once you have a JWT, include it in the `Authorization` header of subsequent requests to secured endpoints.

* **Header:** `Authorization: Bearer <your_jwt_token_here>`

**Example (using cURL for a user-only endpoint):**
Assuming `/api/user` is secured with `hasRole('USER')`

```bash
curl -X GET -H "Authorization: Bearer <your_jwt_token>" http://localhost:8080/api/user
```

**Example (using cURL for an admin-only endpoint):**
Assuming `/api/admin` is secured with `hasRole('ADMIN')`

```bash
curl -X GET -H "Authorization: Bearer <your_jwt_token_from_admin_login>" http://localhost:8080/api/admin
```

### Example Endpoints (as configured in `SecurityConfig.java`)

* `GET /public/**`: Accessible to all (unauthenticated).
* `GET /api/user`: Accessible to authenticated users with `USER` role. (You'll need to create this controller endpoint)
* `GET /api/admin`: Accessible to authenticated users with `ADMIN` role. (You'll need to create this controller endpoint)
* Any other endpoint: Requires authentication.

## Security Considerations

* **JWT Secret Key:** The `jwt.secret` in `application.properties` is the cryptographic key used to sign and verify your JWTs. **It must be kept absolutely secret.** In production, use environment variables, a secrets management service (e.g., HashiCorp Vault, AWS Secrets Manager, Azure Key Vault), or Kubernetes Secrets to manage this key. **Never hardcode it or commit it to a public repository.**

* **`NoOpPasswordEncoder`:** The provided `SecurityConfig` might use `NoOpPasswordEncoder.getInstance()` for simplicity. **This is highly insecure for production environments** as it stores passwords in plain text. Always use a strong password encoder like `BCryptPasswordEncoder` or `Argon2PasswordEncoder` in real applications. Ensure passwords stored in your `UserDetailsService` are properly encoded.

* **Token Expiration:** The current JWT expiration is set to 10 hours. For access tokens, shorter lifespans (e.g., 15-60 minutes) are often preferred, combined with a refresh token mechanism for longer user sessions, to reduce the window of opportunity for token compromise.

## Contribution Opportunities

This project provides a solid foundation, but there's always room for enhancement\! We encourage contributions from developers looking to deepen their understanding of Spring Boot, Spring Security, and JWT. Here are some specific tasks that would significantly improve the project and offer valuable learning experiences:

* **Implement `/api/user` and `/api/admin` Endpoints:** Create actual REST controller methods for these paths to demonstrate role-based access control in action. This involves simple `@RestController` classes with `@GetMapping` annotations.
* **Database Integration for User Storage:** Replace the `InMemoryUserDetailsManager` with a `UserDetailsService` implementation that fetches user details from a database (e.g., using Spring Data JPA with H2, MySQL, or PostgreSQL). This is a crucial step for real-world applications.
* **Refresh Token Mechanism:** Implement a refresh token flow to provide longer user sessions without requiring long-lived access tokens. This enhances security and user experience.
* **Error Handling and Custom Responses:** Implement more robust error handling for authentication failures, invalid tokens, and access denied scenarios, returning custom, user-friendly JSON responses.
* **Unit and Integration Tests:** Add comprehensive tests for JWT generation/validation, security configuration, and endpoint access.
* **Dockerization:** Create a `Dockerfile` to containerize the application, making it easier to deploy and manage.

These tasks are excellent ways to apply and showcase your skills in building secure, scalable Spring Boot applications\!

## Contributing

Contributions are welcome\! If you have suggestions for improvements or find any issues, please open an issue or submit a pull request.

