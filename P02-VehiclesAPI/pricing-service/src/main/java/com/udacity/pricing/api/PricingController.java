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
//package com.udacity.pricing.api;
//
//import com.udacity.pricing.domain.price.Price;
//import com.udacity.pricing.service.PriceException;
//import com.udacity.pricing.service.PricingService;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.server.ResponseStatusException;
//
///**
// * Implements a REST-based controller for the pricing service.
// */
//@RestController
//@RequestMapping("/services/price")
//public class PricingController {
//
//    /**
//     * Gets the price for a requested vehicle.
//     * @param vehicleId ID number of the vehicle for which the price is requested
//     * @return price of the vehicle, or error that it was not found.
//     */
//    @GetMapping
//    public Price get(@RequestParam Long vehicleId) {
//        try {
//            return PricingService.getPrice(vehicleId);
//        } catch (PriceException ex) {
//            throw new ResponseStatusException(
//                    HttpStatus.NOT_FOUND, "Price Not Found", ex);
//        }
//
//    }
//}