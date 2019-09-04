package com.udacity.course3.reviews.model.mongo;

import com.udacity.course3.reviews.model.jpa.Product;

import java.time.LocalDateTime;
import java.util.List;

public class ProductMongo {

    private int id;

    private String productName;

    private LocalDateTime createdTime;

    private List<ReviewMongo> reviews;

    public ProductMongo(Product product) {
        this.id = product.getId();
        this.productName = product.getProductName();
        this.createdTime = product.getCreatedTime();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public List<ReviewMongo> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewMongo> reviews) {
        this.reviews = reviews;
    }
}
