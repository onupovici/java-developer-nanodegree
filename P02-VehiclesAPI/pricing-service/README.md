# Pricing Service

The Pricing Service is a REST WebService that simulates a backend that
would store and retrieve the price of a vehicle given a vehicle id as
input. In this project, you will convert it to a microservice.

## Features

- REST WebService integrated with Spring Boot

## Instructions

Via shell it can be started using

```
$ mvn clean package
```

```
$ java -jar target/pricing-service-0.0.1-SNAPSHOT.jar
```

It can also be imported in your IDE as a Maven project.

## Usage

Pricing service endpoint is http://localhost:8082/prices/ and have the basic CRUD calls.

## API

### Create a Price

`POST` `http://localhost:8082/prices/` 
```json
{ 
  "vehicleId" : 12, 
  "currency" : "USD", 
  "price" : 31326.11
}
```

### Retrieve a Price

`GET` `http://localhost:8082/prices/{vehicleId}`

### Update a Price

`PUT` `http://localhost:8082/prices/{vehicleId}`
```json
{ 
  "currency" : "USD", 
  "price" : 31326.11 
}
```

You can also add price in request body with format 
`"price": "<currency> <price>"`. 
Vehicles API will save this price for the given vehicle id.

### Delete a Price

`DELETE` `http://localhost:8082/prices/{vehicleId}`

## Notes

* PricingService and PricingController in Pricing Service API are commented 
because Pricing Service is converted to a microservice. Controller or Service is not needed anymore.

* Random price data is stored in h2 database and initially inserted using the data.sql