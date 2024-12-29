# Secure Secret Management System

This project is a **Secure Secret Management System** developed using Java and Spring Boot, with PostgreSQL as the database. It provides secure storage, encryption, version control, role-based access control (RBAC), and audit logging for secrets. Additional features include pagination, search, sorting, secret rotation, and detailed reporting.

## Features

### Core Features
- **Secrets Management**: CRUD operations for secrets with encryption and metadata.
- **Encryption**: AES-based encryption for secrets, ensuring data security.
- **RBAC**: Role-based access control for Admins, Owners, and Users.
- **Version Control**: Automatic archiving of previous versions of secrets.
- **Audit Trails**: Detailed logging of all actions, including encrypted logs for sensitive data.

### Additional Features
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

## Installation
1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd secure-secret-management
