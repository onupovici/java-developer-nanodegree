package com.udacity.course3.reviews.repository;

import com.udacity.course3.reviews.model.jpa.Comment;
import com.udacity.course3.reviews.repository.jpa.CommentRepository;
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
public class CommentRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private CommentRepository commentRepository;

    @Before
    public void setup() {
        // given comment
        Comment comment = new Comment();
        comment.setCommentText("Testing comment");
        comment.setCommentUser("user1");
        comment.setCommentCreatedTime(LocalDateTime.now());

        // persist
        this.testEntityManager.persist(comment);
    }

    @Test
    public void testFindAll() {
        List<Comment> actual = commentRepository.findAll();
        assertThat(actual).isNotNull();
        assertEquals(1, actual.size());
    }

    @Test
    public void testFindById() {
        Comment actual = commentRepository.findById(1).get();
        assertThat(actual).isNotNull();
        assertEquals("Testing comment", actual.getCommentText());
    }

    @Test
    public void testSaveComment() {
        // update comment
        Comment comment = commentRepository.findById(1).get();
        comment.setCommentText("New test comment");

        // save comment
        Comment actual = commentRepository.save(comment);

        // assert
        assertEquals(comment.getCommentText(), actual.getCommentText());
    }

    @Test
    public void testDeleteComment() {
        // get comment
        Comment comment = commentRepository.findById(1).get();
        // delete comment
        commentRepository.delete(comment);
        // assert
        List<Comment> actual = commentRepository.findAll();
        assertEquals(0, actual.size());
    }
}
