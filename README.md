# Flight Booking Application – Spring Boot
### Week-4 Assignment

#### A complete Flight Booking System built with Spring Boot 3, REST APIs, PostgreSQL, JPA/Hibernate, Lombok, and Session-based Authentication.
### Functionalities:-
Search Flights
Add Flight Inventory (Admin)
User Registration & Login
Ticket Booking
Booking History
Ticket Lookup via PNR
Cancel Booking (with 24-hour rule)
Validation, Exception Handling, Logging
JPA Mapping with Airline → Flight → Booking → Passenger

### Features:-
1. #### User Features

Register new account
Login using email & password (Session-based Auth)
Search flights by Origin, Destination, Date
Book flights
Add passenger details
Retrieve ticket by PNR
View booking history
Cancel booking (only if more than 24 hours remain)

2. #### Admin Features

Add airline inventory
Add flights with schedule, seats, etc.

### Project Architecture:-
```
com.flightapp
│
├── controller
│   ├── FlightController
│   ├── BookingController
│   └── UserController
│
├── service
│   ├── FlightService
│   ├── BookingService
│   └── UserService
│
├── service.impl
│   ├── FlightServiceImpl
│   ├── BookingServiceImpl
│   └── UserServiceImpl
│
├── repository
│   ├── FlightRepository
│   ├── BookingRepository
│   ├── UserRepository
│   ├── AirlineRepository
│   └── PassengerRepository
│
├── dto
│   ├── request
│   ├── response
│
├── model
│   ├── Airline
│   ├── Flight
│   ├── Booking
│   ├── Passenger
│   └── AppUser
│
└── exception
    ├── GlobalExceptionHandler
    ├── ResourceNotFoundException
    ├── BadRequestException
    └── BookingException
```
### Database (PostgreSQL):-
#### Application Properties

spring.datasource.url=jdbc:postgresql://localhost:5432/flight_booking
spring.datasource.username=postgres
spring.datasource.password=postgres123

spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

### API Endpoints:-
1. User APIs
```
Method	               Endpoint	                            Description
POST	        /api/v1.0/flight/register	                   Register user
POST	         /api/v1.0/flight/login	                  Login and store session
GET	        /api/v1.0/flight/booking/history/{email}      	Booking history
```
2. Flight APIs
```
Method	                   Endpoint                          	Description
POST	            /api/v1.0/flight/airline/inventory/add	Add flight inventory
POST              	/api/v1.0/flight/search	                 search flights
```
3. Booking APIs
```
Method	             Endpoint	                          Description
POST	      /api/v1.0/flight/booking/{flightId}         Book ticket
GET	          /api/v1.0/flight/ticket/{pnr}	           Fetch ticket by PNR
DELETE	   /api/v1.0/flight/booking/cancel/{pnr}        Cancel booking
```
### Testing and Coverage:-
Run Unit Tests
```
mvn clean test
```
Enable JaCoCo Coverage
```
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.10</version>
    <executions>
        <execution>
            <goals>
                <goal>prepare-agent</goal>
            </goals>
        </execution>
        <execution>
            <id>report</id>
            <phase>test</phase>
            <goals>
                <goal>report</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```
Coverage Report Location
```
target/site/jacoco/index.html
```

### How to run:-
Clone Repository 
```
git clone <repo-url>
```
Import STS/Eclipse as Maven Project
Run Application
```
mvn spring-boot:run
```
### Technology Used

Spring Boot 3

Spring Web

Spring Data JPA

PostgreSQL

Lombok

Session-based Authentication

JUnit & Mockito

JaCoCo

SonarQube

### License
Free to use for learning & development.

