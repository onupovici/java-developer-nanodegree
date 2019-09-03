package com.udacity.course3.reviews.controller;

import com.udacity.course3.reviews.model.jpa.Product;
import com.udacity.course3.reviews.model.mongo.ProductMongo;
import com.udacity.course3.reviews.model.mongo.ReviewMongo;
import com.udacity.course3.reviews.repository.jpa.ProductRepository;
import com.udacity.course3.reviews.service.ReviewMongoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Spring REST controller for working with product entity.
 */
@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReviewMongoService reviewMongoService;

    /**
     * Creates a product.
     *
     * 1. Accept product as argument. Use {@link RequestBody} annotation.
     * 2. Save product.
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Creates a product")
    public void createProduct(@Valid @RequestBody Product product) {
        // set created time
        if (product.getCreatedTime() == null) {
            product.setCreatedTime(LocalDateTime.now());
        }
        Product newProduct = productRepository.save(product);
    }

    /**
     * Finds a product by id.
     *
     * @param id The id of the product.
     * @return The product if found, or a 404 not found.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Finds a product by id")
    public ResponseEntity<?> findById(@PathVariable("id") Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            // final: get reviews from Mongodb
            List<ReviewMongo> reviewMongos = reviewMongoService.findAllByProductId(id);
            // final: update Product with data from both MySQL and Mongo
            ProductMongo result = new ProductMongo(optionalProduct.get());
            result.setReviews(reviewMongos);
            return ResponseEntity.ok(result);
        }
        else {
            throw new HttpServerErrorException(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Lists all products.
     *
     * @return The list of products.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiOperation(value = "Lists all products")
    public List<?> listProducts() {
        return productRepository.findAll();
    }
}