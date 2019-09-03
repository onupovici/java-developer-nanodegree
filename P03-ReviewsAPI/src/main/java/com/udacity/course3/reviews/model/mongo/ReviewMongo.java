package com.udacity.course3.reviews.model.mongo;

import com.udacity.course3.reviews.model.jpa.Review;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "reviews")
public class ReviewMongo {

    @Id
    private String id;

    private int reviewId;

    private int reviewStar;

    private String reviewText;

    private String reviewUser;

    private LocalDateTime reviewCreatedTime;

    private int productId;

    private List<CommentMongo> comments;

    public ReviewMongo() {}

    public ReviewMongo(Review review) {
        this.reviewId = review.getReviewId();
        this.reviewStar = review.getReviewStar();
        this.reviewText = review.getReviewText();
        this.reviewUser = review.getReviewUser();
        this.reviewCreatedTime = review.getReviewCreatedTime();
        this.productId = review.getProduct().getId();
    }

    public ReviewMongo(int reviewId, int reviewStar, String reviewText, String reviewUser, LocalDateTime reviewCreatedTime, int productId, List<CommentMongo> comments) {
        this.reviewId = reviewId;
        this.reviewStar = reviewStar;
        this.reviewText = reviewText;
        this.reviewUser = reviewUser;
        this.reviewCreatedTime = reviewCreatedTime;
        this.productId = productId;
        this.comments = comments;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getReviewStar() {
        return reviewStar;
    }

    public void setReviewStar(int reviewStar) {
        this.reviewStar = reviewStar;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public String getReviewUser() {
        return reviewUser;
    }

    public void setReviewUser(String reviewUser) {
        this.reviewUser = reviewUser;
    }

    public LocalDateTime getReviewCreatedTime() {
        return reviewCreatedTime;
    }

    public void setReviewCreatedTime(LocalDateTime reviewCreatedTime) {
        this.reviewCreatedTime = reviewCreatedTime;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public List<CommentMongo> getComments() {
        return comments;
    }

    public void setComments(List<CommentMongo> comments) {
        this.comments = comments;
    }
}
