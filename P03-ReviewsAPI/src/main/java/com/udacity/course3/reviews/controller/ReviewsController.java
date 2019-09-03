package com.udacity.course3.reviews.controller;

import com.udacity.course3.reviews.model.jpa.Product;
import com.udacity.course3.reviews.model.jpa.Review;
import com.udacity.course3.reviews.model.mongo.ReviewMongo;
import com.udacity.course3.reviews.repository.jpa.ProductRepository;
import com.udacity.course3.reviews.repository.jpa.ReviewRepository;
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
 * Spring REST controller for working with review entity.
 */
@RestController
public class ReviewsController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReviewMongoService reviewMongoService;

    /**
     * Creates a review for a product.
     * <p>
     * 1. Add argument for review entity. Use {@link RequestBody} annotation.
     * 2. Check for existence of product.
     * 3. If product not found, return NOT_FOUND.
     * 4. If found, save review.
     *
     * @param productId The id of the product.
     * @return The created review or 404 if product id is not found.
     */
    @RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.POST)
    @ApiOperation(value = "Creates a review for a product")
    public ResponseEntity<?> createReviewForProduct(@PathVariable("productId") Integer productId, @Valid @RequestBody Review review) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            // set product
            review.setProduct(optionalProduct.get());
            // set created time
            if (review.getReviewCreatedTime() == null) {
                review.setReviewCreatedTime(LocalDateTime.now());
            }
            // midterm: save review in MySQL
            Review newReview = reviewRepository.save(review);

            // final: save review in Mongodb
            ReviewMongo reviewMongo = reviewMongoService.save(newReview);

            return ResponseEntity.ok(reviewMongo);
        }
        else {
            throw new HttpServerErrorException(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Lists reviews by product.
     *
     * @param productId The id of the product.
     * @return The list of reviews.
     */
    @RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.GET)
    @ApiOperation(value = "Lists reviews by product")
    public ResponseEntity<List<?>> listReviewsForProduct(@PathVariable("productId") Integer productId) {
        // midterm: MySQL
        List<Review> reviews = reviewRepository.findByProductId(productId);
        // final: Mongodb
        List<ReviewMongo> reviewsMongo = reviewMongoService.findAllByProductId(productId);
        return ResponseEntity.ok(reviewsMongo);
    }
}