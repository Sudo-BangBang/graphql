package com.sudobangbang.graphql.repository;

import com.sudobangbang.graphql.model.comment.Comment;

import java.util.List;

public interface CommentRepo {
    List<Comment> findBySubjectId(String subjectId, Integer skip, Integer first);
    List<Comment> findByUserId(String userId);
    Comment findById(String commentId);
    Comment saveComment(Comment comment);
    Comment updateComment(Comment comment);
}
