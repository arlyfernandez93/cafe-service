# Spring Boot Project

This is a Spring Boot project that implements a payment and debt management system based on the Hexagonal architecture. Below, the most relevant aspects of the project, its architecture, and how to use the available APIs are detailed.

## Architecture

The project uses **Hexagonal Architecture**, also known as **Ports and Adapters**. This architecture promotes the separation of application logic, providing better scalability and maintainability. The core logic of the application is decoupled from implementation details such as the user interface, database, and other external services.

### Implemented Patterns

- **DTO (Data Transfer Object)**: This pattern is used to transfer data between the presentation layer and the business layer. DTOs help avoid direct exposure of domain model entities, facilitating better encapsulation practices.

- **Repository**: The Repository pattern is implemented to abstract access to the data source (in this case, JSON files). This includes the logic for retrieving and handling data without exposing the details of how they are stored.

## Dependencies

This project uses the **Jackson** library from FasterXML for reading and writing JSON files, allowing for easy and efficient integration with data in JSON format.

## Available APIs

### Get Total Payment by User

This API allows you to obtain the total payment by a specific user.

```bash
curl --location --request GET 'http://localhost:8080/v1/payments/user/coach'
```

### Get outstanding amount debt by User

This API allows you to obtain the outstanding amount debt by a specific user.

```bash
curl --location --request GET 'http://localhost:8080/v1/debts/user/coach'
```

# API Documentation

The API documentation is available at the following URL:

[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

You can access this URL in your browser to explore the API endpoints and view detailed information about each of them.
