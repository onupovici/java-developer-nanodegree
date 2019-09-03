package com.udacity.course3.reviews.repository;

import com.udacity.course3.reviews.model.jpa.Product;
import com.udacity.course3.reviews.repository.jpa.ProductRepository;
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
public class ProductRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ProductRepository productRepository;

    @Before
    public void setup() {
        // given product
        Product product = new Product();
        product.setProductName("Test Product");
        product.setCreatedTime(LocalDateTime.now());

        // persist
        this.testEntityManager.persist(product);
    }

    @Test
    public void testFindAll() {
        List<Product> actual = productRepository.findAll();
        assertThat(actual).isNotNull();
        assertEquals(1, actual.size());
    }

    @Test
    public void testFindById() {
        Product actual = productRepository.findById(1).get();
        assertThat(actual).isNotNull();
        assertEquals("Test Product", actual.getProductName());
    }

    @Test
    public void testSaveProduct() {
        // update product
        Product product = productRepository.findById(1).get();
        product.setProductName("New test name");

        // save product
        Product actual = productRepository.save(product);

        // assert
        assertEquals(product.getProductName(), actual.getProductName());
    }

    @Test
    public void testDeleteProduct() {
        // get product
        Product product = productRepository.findById(1).get();
        // delete product
        productRepository.delete(product);
        // assert
        List<Product> actual = productRepository.findAll();
        assertEquals(0, actual.size());
    }
}
