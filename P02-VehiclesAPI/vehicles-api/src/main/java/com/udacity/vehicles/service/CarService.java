package com.udacity.vehicles.service;

import com.udacity.vehicles.client.maps.MapsClient;
import com.udacity.vehicles.client.prices.PriceClient;
import com.udacity.vehicles.domain.Location;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implements the car service create, read, update or delete
 * information about vehicles, as well as gather related
 * location and price data when desired.
 */
@Service
public class CarService {

    private final CarRepository repository;
    private final MapsClient mapsClient;
    private final PriceClient priceClient;

    public CarService(CarRepository repository, MapsClient mapsClient, PriceClient priceClient) {
        this.repository = repository;
        this.mapsClient = mapsClient;
        this.priceClient = priceClient;
    }

    /**
     * Gathers a list of all vehicles
     * @return a list of all vehicles in the CarRepository
     */
    public List<Car> list() {
        return repository.findAll();
    }

    /**
     * Gets car information by ID (or throws exception if non-existent)
     * @param id the ID number of the car to gather information on
     * @return the requested car's information, including location and price
     */
    public Car findById(Long id) {
        Car car;
        Optional<Car> optionalCar = repository.findById(id);
        if (optionalCar.isPresent()) {
            car = optionalCar.get();
        }
        else {
            throw new CarNotFoundException();
        }

        String price = priceClient.getPrice(car.getId());
        car.setPrice(price);

        Location address = mapsClient.getAddress(car.getLocation());
        car.setLocation(address);

        return car;
    }

    /**
     * Either creates or updates a vehicle, based on prior existence of car
     * @param car A car object, which can be either new or existing
     * @return the new/updated car is stored in the repository
     */
    public Car save(Car car) {
        // save car
        Car savedCar;
        if (car.getId() != null) {
            savedCar = repository.findById(car.getId())
                    .map(carToBeUpdated -> {
                        carToBeUpdated.setDetails(car.getDetails());
                        carToBeUpdated.setLocation(car.getLocation());
                        return repository.save(carToBeUpdated);
                    }).orElseThrow(CarNotFoundException::new);
        }
        else {
            savedCar = repository.save(car);
        }

        // save and set price
        if (car.getPrice() != null && !car.getPrice().isEmpty()) {
            // if car has a price, save price
            priceClient.updatePrice(savedCar.getPrice(), savedCar.getId());
        }
        else {
            // else, assign random price
            String price = priceClient.getPrice(savedCar.getId());
            savedCar.setPrice(price);
        }
        return savedCar;
    }

    /**
     * Deletes a given car by ID
     * @param id the ID number of the car to delete
     */
    public void delete(Long id) {
        Optional<Car> optionalCar = repository.findById(id);
        if (optionalCar.isPresent()) {
            Car car = optionalCar.get();
            // delete car
            repository.delete(car);
            // update price with random price
            priceClient.randomizePrice(car.getId());
        }
        else {
            throw new CarNotFoundException();
        }
    }
}
