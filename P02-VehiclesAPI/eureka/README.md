# Eureka Server

Eureka is responsible for the registration and discovery of pricing-service microservice.

The Pricing Service will be registered in this server.

The Vehicles API will be able to use the Eureka server to call the pricing service.

## Instructions

Via shell it can be started using

```
$ mvn clean package
```

```
$ java -jar target/eureka-0.0.1-SNAPSHOT.jar
```

## Usage
Eureka Registry is accessible via http://localhost:8761