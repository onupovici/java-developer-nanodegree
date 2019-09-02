//
// Note: This is commented out because Pricing Service API is converted to a microservice.
// Controller or Service is not needed anymore.
// Random price data is stored in h2 database and initially inserted using the data.sql
// Here are endpoints to access Pricing Service API Microservice:
// 1. GET http://localhost:8082/prices/{vehicleId}
// 2. POST http://localhost:8082/prices/
//      Request: { "vehicleId" : 12, "currency" : "USD", "price" : 31326.11 }
// 3. PUT http://localhost:8082/prices/{vehicleId}
//      Request: { "currency" : "USD", "price" : 31326.11 }
// 4. DELETE http://localhost:8082/prices/{vehicleId}
//
//package com.udacity.pricing.service;
//
//import com.udacity.pricing.domain.price.Price;
//
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.util.Map;
//import java.util.concurrent.ThreadLocalRandom;
//import java.util.stream.Collectors;
//import java.util.stream.LongStream;
//
///**
// * Implements the pricing service to get prices for each vehicle.
// */
//public class PricingService {
//
//    /**
//     * Holds {ID: Price} pairings (current implementation allows for 20 vehicles)
//     */
//    private static final Map<Long, Price> PRICES = LongStream
//            .range(1, 20)
//            .mapToObj(i -> new Price("USD", randomPrice(), i))
//            .collect(Collectors.toMap(Price::getVehicleId, p -> p));
//
//    /**
//     * If a valid vehicle ID, gets the price of the vehicle from the stored array.
//     * @param vehicleId ID number of the vehicle the price is requested for.
//     * @return price of the requested vehicle
//     * @throws PriceException vehicleID was not found
//     */
//    public static Price getPrice(Long vehicleId) throws PriceException {
//
//        if (!PRICES.containsKey(vehicleId)) {
//            throw new PriceException("Cannot find price for Vehicle " + vehicleId);
//        }
//
//        return PRICES.get(vehicleId);
//    }
//
//    /**
//     * Gets a random price to fill in for a given vehicle ID.
//     * @return random price for a vehicle
//     */
//    private static BigDecimal randomPrice() {
//        return new BigDecimal(ThreadLocalRandom.current().nextDouble(1, 5))
//                .multiply(new BigDecimal(5000d)).setScale(2, RoundingMode.HALF_UP);
//    }
//
//}
