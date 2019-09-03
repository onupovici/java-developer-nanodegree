package com.udacity.course3.reviews.repository.mongo;

import com.udacity.course3.reviews.model.mongo.ReviewMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewMongoRepository extends MongoRepository<ReviewMongo, String> {

    List<ReviewMongo> findAllByProductId(Integer productId);

    ReviewMongo findByProductId(Integer productId);

    ReviewMongo findByReviewId(Integer reviewId);
}
