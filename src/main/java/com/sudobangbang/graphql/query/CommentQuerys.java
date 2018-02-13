package com.sudobangbang.graphql.query;

import com.sudobangbang.graphql.model.comment.Comment;
import com.sudobangbang.graphql.repository.CommentRepo;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;

import java.util.List;

public class CommentQuerys {

    private final CommentRepo commentRepo;

    public CommentQuerys(CommentRepo commentRepo) {
        this.commentRepo = commentRepo;
    }

    @GraphQLQuery(description = "Get a specific comment by its ID")
    public Comment getComment(@GraphQLArgument(name = "id") String id){
        return commentRepo.findById(id);
    }
}
