# Springboot Sample Application
This application is a sample backend API made with Java Spring Boot that serves as a product management along with transactions and role-based accesses using JWT Authentication.  

## Specifications
* Spring Boot 3.1.9
* Java 17
* MySQL 8.3.0

## Getting Started
* Database seeder with dummy data can be found [here](./misc/database_seeder.sql)
* Postman collection for testing purposes can be found [here](./misc/postman_collection.json)

## Credentials

### Database Connection
The following data are the default configuration for database integration. You can change it [here](./src/main/resources/application.properties) 
* ConnectionString: jdbc:mysql://localhost:3306/sample_db?allowPublicKeyRetrieval=true&useSSL=false
* Username: root
* Password: Dbaccess01

### Dummy data
- *Login either by using email or username*
- *Registration can only be done by users with the role ADMIN*

**Admin:**
* username: admin
* email: admin@example.com
* password: admin

**Customer:**
* username: john_doe
* email: john@example.com
* password: pass

## Things-to-note
* Role based access control is managed using Jwt Token
* Parameters for updating and deleting data will be done by passing the id using **RequestParam**
* Data deletion is handled using *soft-delete*, by setting the dataStatus to 'D' (non-active), instead of deleting the record itself

## Documentation
Here are the endpoint accesses based on the user authorization
Examples can be seen in the *postman_collection.json* file

### No-Auth
* Login
* Unused Endpoints (Error: Not found)

### Admin
* User (CRUD)
* Profile (Get, Update & CreateIfNotExist)
* Product (CRUD)
* Transaction (CRUD)
* Report (GetByDate & GetByProduct)
* Logout (Invalidate current Jwt Token)

### Customer
* Profile (Get, Update & CreateIfNotExist)
* Report (GetByDate & GetByProduct)
* Logout (Invalidate current Jwt Token)

