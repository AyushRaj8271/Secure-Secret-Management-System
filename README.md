# Secure Secret Management System

This project is a **Secure Secret Management System** developed using Java and Spring Boot, with PostgreSQL as the database. It provides secure storage, encryption, version control, role-based access control (RBAC), and audit logging for secrets. Additional features include pagination, search, sorting, secret rotation, and detailed reporting.

## Features

### Core Features
- **Secrets Management**: CRUD operations for secrets with encryption and metadata.
- **Encryption**: AES-based encryption for secrets, ensuring data security.
- **RBAC**: Role-based access control for Admins, Owners, and Users.
- **Version Control**: Automatic archiving of previous versions of secrets.
- **Audit Trails**: Detailed logging of all actions, including encrypted logs for sensitive data.

### Additional Functionalities
- Pagination, search, and sorting for efficient secret retrieval.
- Automatic re-encryption of outdated secrets via a scheduled cron job.
- Fine-grained access control with whitelisting.
- Usage reports for secrets.
- Data masking and rate limiting.
- Regex-based retrieval of secrets.

## Project Structure
### Controllers
- **User Controller**
    - `GET /api/users/{id}`: Retrieve a user by ID.
    - `PUT /api/users/{id}`: Update a user.
    - `DELETE /api/users/{id}`: Delete a user.
    - `POST /api/users/new`: Create a new user.
    - `GET /api/users`: List all users.
    - `GET /api/users/email/{email}`: Retrieve a user by email.

- **Secret Controller**
    - `GET /api/secrets/{id}`: Retrieve a secret by ID.
    - `PUT /api/secrets/{id}`: Update a secret.
    - `DELETE /api/secrets/{id}`: Delete a secret.
    - `POST /api/secrets/rotate/{id}`: Rotate a specific secret.
    - `POST /api/secrets/rotate-all`: Rotate all secrets.
    - `POST /api/secrets/new`: Create a new secret.
    - `GET /api/secrets`: List all secrets.
    - `GET /api/secrets/paging`: List secrets with pagination.

- **Report Controller**
    - `GET /api/reports/check`: Generate usage reports.

- **Archive Controller**
    - `GET /api/archive`: List archived secrets.
    - `GET /api/archive/{id}`: Retrieve a specific archived secret.

- **Hello World Controller**
    - `GET /`: Health check endpoint.

## Database
- **PostgreSQL** is used as the relational database.
- Key relational mappings:
    - **One-to-Many**: A user can own multiple secrets.
    - **Many-to-Many**: A secret can have multiple users with permissions.
- Master table for users: Includes `user_id`, `name`, `email`, and `role`.

## Milestones
### Milestone 1: Project Setup, Swagger, and CRUD with Encryption
- Spring Boot project setup with Swagger for API documentation.
- Implement CRUD operations for secrets with encryption.

### Milestone 2: RBAC and Version Control
- Role-based access control with Spring Security and JWT.
- Version control for secrets with archiving and audit logging.

### Milestone 3: Pagination, Sorting, Re-encryption, and Audit Trail
- Pagination, search, and sorting for secrets.
- Re-encryption cron job for outdated secrets.
- Detailed audit logging.

### Milestone 4: Fine-grained Access Control and Reporting
- Whitelisting for fine-grained RBAC.
- Usage reports for secrets.

### Milestone 5: Advanced Functionalities
- Automatic and manual secret rotation.
- Rate limiting.
- Data masking.
- Regex-based retrieval of secrets.

## Installation
1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd secure-secret-management
   ```
2. Set up PostgreSQL database and update the `application.yml` configuration.
3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

## Documentation
- Swagger API Documentation is available at `/swagger-ui.html`.

## Testing
- Unit tests with Mockito and JUnit are included for all major components.
- Run tests using:
  ```bash
  ./mvnw test
  ```

## Contributing
1. Fork the repository.
2. Create a new branch for your feature:
   ```bash
   git checkout -b feature-name
   ```
3. Commit and push your changes:
   ```bash
   git commit -m "Add new feature"
   git push origin feature-name
   ```
4. Open a pull request.

## License
This project is licensed under the MIT License.
