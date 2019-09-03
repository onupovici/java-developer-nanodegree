package com.udacity.course3.reviews.service;

import com.udacity.course3.reviews.model.jpa.Comment;
import com.udacity.course3.reviews.model.jpa.Review;
import com.udacity.course3.reviews.model.mongo.CommentMongo;
import com.udacity.course3.reviews.model.mongo.ReviewMongo;
import com.udacity.course3.reviews.repository.mongo.ReviewMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewMongoService {

    @Autowired
    private ReviewMongoRepository reviewMongoRepository;

    /**
     * Saves a review to Mongodb
     * @param review
     */
    public ReviewMongo save(Review review) {
        // convert Review JPA to Review Mongo model
        ReviewMongo reviewMongo = new ReviewMongo(review);

        // convert and add Comment Mongo data
        List<CommentMongo> commentMongos = getListCommentMongo(review.getComments());
        if (!commentMongos.isEmpty()) {
            reviewMongo.setComments(commentMongos);
        }

        // save review to Mongodb
        return reviewMongoRepository.save(reviewMongo);
    }

    /**
     * Saves a comment for a given review in Mongodb
     * @param reviewId
     * @param comment
     * @return
     */
    public CommentMongo saveComment(Integer reviewId, Comment comment) {
        // get review document
        ReviewMongo reviewMongo = reviewMongoRepository.findByReviewId(reviewId);
        if (reviewMongo == null) {
            throw new HttpServerErrorException(HttpStatus.NOT_FOUND);
        }
        // convert new Comment JPA to Comment Mongo document
        CommentMongo commentMongo = new CommentMongo(comment);
        // create new list of comments and add new Comment
        List<CommentMongo> commentMongos = new ArrayList<>();
        commentMongos.add(commentMongo);
        // get current comment list and combine
        if (reviewMongo.getComments() != null && !reviewMongo.getComments().isEmpty()) {
            commentMongos.addAll(reviewMongo.getComments());
        }
        // save updated review
        reviewMongo.setComments(commentMongos);
        ReviewMongo result = reviewMongoRepository.save(reviewMongo);
        return commentMongo;
    }

    /**
     * Retrieves all reviews from Mongodb
     * @return
     */
    public List<ReviewMongo> findAll() {
        return reviewMongoRepository.findAll();
    }

    /**
     * Retrieves a list of reviews with the given product id from Mongodb
     * @param productId
     * @return
     */
    public List<ReviewMongo> findAllByProductId(Integer productId) {
        return reviewMongoRepository.findAllByProductId(productId);
    }

    /**
     * Retrieves a review with the given product id from Mongodb
     * @param productId
     * @return
     */
    public ReviewMongo findByProductId(Integer productId) {
        return reviewMongoRepository.findByProductId(productId);
    }

    /**
     * Convert List of Comment JPA into List of Comment Mongodb
     * @param commentsJpa
     * @return
     */
    public List<CommentMongo> getListCommentMongo(List<Comment> commentsJpa) {
        List<CommentMongo> commentMongos = new ArrayList<>();
        if (commentsJpa != null && !commentsJpa.isEmpty()) {
            for (Comment comment : commentsJpa) {
                commentMongos.add(new CommentMongo(comment));
            }
        }
        return commentMongos;
    }
}
