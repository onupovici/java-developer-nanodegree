package com.udacity.course3.reviews.model.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private int reviewId;

    @Column(name = "review_star")
    @NotNull
    private int reviewStar;

    @Column(name = "review_text")
    @NotNull
    private String reviewText;

    @Column(name = "review_user")
    @NotNull
    private String reviewUser;

    @Column(name = "review_created_time")
    private LocalDateTime reviewCreatedTime;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;

    @OneToMany(mappedBy = "review", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Comment> comments;

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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
