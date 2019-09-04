package com.udacity.course3.reviews.repository;

import com.udacity.course3.reviews.model.jpa.Review;
import com.udacity.course3.reviews.repository.jpa.ReviewRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * Guidelines used:
 * https://ajayiyengar.com/2017/07/08/testing-jpa-entities-in-a-spring-boot-application/
 * https://hellokoding.com/spring-boot-test-data-layer-example-with-datajpatest/
 * https://stackoverflow.com/questions/41092716/how-to-reset-between-tests
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ReviewRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ReviewRepository reviewRepository;

    @Before
    public void setup() {
        // given review
        Review review = new Review();
        review.setReviewStar(5);
        review.setReviewText("Testing review");
        review.setReviewUser("user1");
        review.setReviewCreatedTime(LocalDateTime.now());

        // persist
        this.testEntityManager.persist(review);
    }

    @Test
    public void testFindAll() {
        List<Review> actual = reviewRepository.findAll();
        assertThat(actual).isNotNull();
        assertEquals(1, actual.size());
    }

    @Test
    public void testFindById() {
        Review actual = reviewRepository.findById(1).get();
        assertThat(actual).isNotNull();
        assertEquals(5, actual.getReviewStar());
    }

    @Test
    public void testSaveReview() {
        // update review
        Review review = reviewRepository.findById(1).get();
        review.setReviewStar(4);

        // save review
        Review actual = reviewRepository.save(review);

        // assert
        assertEquals(review.getReviewStar(), actual.getReviewStar());
    }

    @Test
    public void testDeleteReview() {
        // get review
        Review review = reviewRepository.findById(1).get();
        // delete review
        reviewRepository.delete(review);
        // assert
        List<Review> actual = reviewRepository.findAll();
        assertEquals(0, actual.size());
    }
}
