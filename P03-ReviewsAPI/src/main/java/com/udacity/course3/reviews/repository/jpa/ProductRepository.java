package com.udacity.course3.reviews.repository.jpa;

import com.udacity.course3.reviews.model.jpa.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
