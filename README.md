# Springboot-Base-Project

Project to study Spring Boot resources and develop unit and integrations tests.

## What does the project do?

The following resources have been developed:

* Access security control for users considering user profile;
* End Point (REST) for authentication and new user registration;
* End Points (REST) for user (CRUD);
* Unit tests with mock for repositories, services and controllers;
* Integration tests for controllers;
* Data validations for database operations;
* Handling exceptions for returning End Points;

## Resources Used

* Java 8;
* Spring Boot;
* Spring Security;
* Lombok (for making essential methods available);
* PostgreSQL;
* H2 Database for unit tests;
* JUnit;
* JWT Token;

## Settings

* Clone the project;
* Create "springboot-base" database in PostgreSQL;
* Update connection data to PostgreSQL in the "application.properties" file;
* Run "mvn clean install" command;

##Expected result

Java back-end service that provides end-points for the client layer through token authentication.
