package com.udacity.course3.reviews.repository.jpa;

import com.udacity.course3.reviews.model.jpa.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
