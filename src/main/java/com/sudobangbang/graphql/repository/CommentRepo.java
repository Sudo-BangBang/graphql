package com.sudobangbang.graphql.repository;

import com.sudobangbang.graphql.model.Comment;

import java.util.List;

public interface CommentRepo {
    List<Comment> findBySubjectId(String subjectId);
    List<Comment> findByUserId(String userId);
    Comment findById(String commentId);
    Comment saveComment(Comment comment);
}
