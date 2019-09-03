package com.udacity.course3.reviews.model.mongo;

import com.udacity.course3.reviews.model.jpa.Comment;

import java.time.LocalDateTime;

public class CommentMongo {

    private String commentText;

    private String commentUser;

    private LocalDateTime commentCreatedTime;

    public CommentMongo() {}

    public CommentMongo(Comment comment) {
        this.commentText = comment.getCommentText();
        this.commentUser = comment.getCommentUser();
        this.commentCreatedTime = comment.getCommentCreatedTime();
    }

    public CommentMongo(String commentText, String commentUser, LocalDateTime commentCreatedTime) {
        this.commentText = commentText;
        this.commentUser = commentUser;
        this.commentCreatedTime = commentCreatedTime;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getCommentUser() {
        return commentUser;
    }

    public void setCommentUser(String commentUser) {
        this.commentUser = commentUser;
    }

    public LocalDateTime getCommentCreatedTime() {
        return commentCreatedTime;
    }

    public void setCommentCreatedTime(LocalDateTime commentCreatedTime) {
        this.commentCreatedTime = commentCreatedTime;
    }
}
