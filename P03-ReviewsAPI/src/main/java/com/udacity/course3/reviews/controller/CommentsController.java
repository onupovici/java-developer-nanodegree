package com.udacity.course3.reviews.controller;

import com.udacity.course3.reviews.model.jpa.Comment;
import com.udacity.course3.reviews.model.jpa.Review;
import com.udacity.course3.reviews.model.mongo.CommentMongo;
import com.udacity.course3.reviews.repository.jpa.CommentRepository;
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
 * Spring REST controller for working with comment entity.
 */
@RestController
@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewMongoService reviewMongoService;

    /**
     * Creates a comment for a review.
     *
     * 1. Add argument for comment entity. Use {@link RequestBody} annotation.
     * 2. Check for existence of review.
     * 3. If review not found, return NOT_FOUND.
     * 4. If found, save comment.
     *
     * @param reviewId The id of the review.
     */
    @RequestMapping(value = "/reviews/{reviewId}", method = RequestMethod.POST)
    @ApiOperation(value = "Creates a comment for a review")
    public ResponseEntity<?> createCommentForReview(@PathVariable("reviewId") Integer reviewId, @Valid @RequestBody Comment comment) {
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);
        if (optionalReview.isPresent()) {
            // set review
            comment.setReview(optionalReview.get());
            // set created time
            if (comment.getCommentCreatedTime() == null) {
                comment.setCommentCreatedTime(LocalDateTime.now());
            }
            // midterm: save comment in MySQL
            Comment newComment = commentRepository.save(comment);

            // final: save comment in Mongodb using productId of the review
            CommentMongo commentMongo = reviewMongoService.saveComment(reviewId, comment);

            return ResponseEntity.ok(commentMongo);
        }
        throw new HttpServerErrorException(HttpStatus.NOT_FOUND);
    }

    /**
     * List comments for a review.
     *
     * 2. Check for existence of review.
     * 3. If review not found, return NOT_FOUND.
     * 4. If found, return list of comments.
     *
     * @param reviewId The id of the review.
     */
    @RequestMapping(value = "/reviews/{reviewId}", method = RequestMethod.GET)
    @ApiOperation(value = "List comments for a review")
    public List<?> listCommentsForReview(@PathVariable("reviewId") Integer reviewId) {
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);
        if (optionalReview.isPresent()) {
            // midterm: MySQL
            List<Comment> comments = optionalReview.get().getComments();
            return comments;
        }
        throw new HttpServerErrorException(HttpStatus.NOT_FOUND);
    }
}