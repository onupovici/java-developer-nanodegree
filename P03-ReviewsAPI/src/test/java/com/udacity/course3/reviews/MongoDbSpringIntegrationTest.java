package com.udacity.course3.reviews;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Guideline used: https://www.baeldung.com/spring-boot-embedded-mongodb
 */
@DataMongoTest
@ExtendWith(SpringExtension.class)
@DirtiesContext
public class MongoDbSpringIntegrationTest {

	@Test
	public void test(@Autowired MongoTemplate mongoTemplate) {
		// given
		DBObject objectToSave = BasicDBObjectBuilder.start()
				.add("reviewId", 1)
				.add("reviewStar", 2)
				.add("reviewText", "Testing review")
				.add("reviewUser", "user1")
				.add("productId", 1)
				.get();

		// when
		mongoTemplate.save(objectToSave, "reviews");

		// then
		assertThat(mongoTemplate.findAll(DBObject.class, "reviews")).extracting("reviewStar")
				.containsOnly(2);
	}
}