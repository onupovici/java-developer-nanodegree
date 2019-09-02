package com.udacity.vehicles.client.prices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Implements a class to interface with the Pricing Client for price data.
 */
@Component
public class PriceClient {

    private static final Logger log = LoggerFactory.getLogger(PriceClient.class);

    private final WebClient client;

    public PriceClient(WebClient pricing) {
        this.client = pricing;
    }

    // In a real-world application we'll want to add some resilience
    // to this method with retries/CB/failover capabilities
    // We may also want to cache the results so we don't need to
    // do a request every time
    /**
     * Gets a vehicle price from the pricing client, given vehicle ID.
     * @param vehicleId ID number of the vehicle for which to get the price
     * @return Currency and price of the requested vehicle,
     *   error message that the vehicle ID is invalid, or note that the
     *   service is down.
     */
    public String getPrice(Long vehicleId) {
        try {
            Price price = client
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/" + vehicleId)
                            .build()
                    )
                    .retrieve().bodyToMono(Price.class).block();

            return String.format("%s %s", price.getCurrency(), price.getPrice());

        } catch (Exception e) {
            log.error("Unexpected error retrieving price for vehicle {}", vehicleId, e);
        }
        return "(consult price)";
    }

    /**
     * Saves a price to pricing client with the given id
     * @param price Price in a String format "currency price"
     * @param vehicleId ID number of the vehicle for which to save the price
     * @return
     */
    public void updatePrice(String price, Long vehicleId) {
        Price priceObj = new Price(price);
        priceObj.setVehicleId(vehicleId);
        savePrice(priceObj);
    }

    /**
     * Updates a price of a given id with a random price
     * @param vehicleId ID number of the vehicle for which to delete the price
     */
    public void randomizePrice(Long vehicleId) {
        Price randomPrice = new Price("USD", randomPrice(), vehicleId);
        savePrice(randomPrice);
    }

    /**
     * Saves a price to pricing client
     * @param price
     */
    private void savePrice(Price price) {
        try {
            Price result = client
                    .post()
                    .uri(uriBuilder -> uriBuilder
                            .path("/")
                            .build()
                    )
                    .syncBody(price)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve().bodyToMono(Price.class).block();

        } catch (Exception e) {
            log.error("Unexpected error saving price for vehicle {}", price.getVehicleId(), e);
        }
    }

    /**
     * Gets a random price to fill in for a given vehicle ID.
     * @return random price for a vehicle
     */
    private static BigDecimal randomPrice() {
        return new BigDecimal(ThreadLocalRandom.current().nextDouble(1, 5))
                .multiply(new BigDecimal(5000d)).setScale(2, RoundingMode.HALF_UP);
    }

}
