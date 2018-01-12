package com.sudobangbang.graphql.mutation;

import com.sudobangbang.graphql.endpoint.AuthContext;
import com.sudobangbang.graphql.model.Comment;
import com.sudobangbang.graphql.repository.CommentRepo;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLRootContext;

import java.time.Instant;
import java.time.ZoneOffset;

public class CommentMutations {

    private final CommentRepo commentRepo;

    public CommentMutations(CommentRepo commentRepo) {
        this.commentRepo = commentRepo;
    }

    @GraphQLMutation
    public Comment createComment(
            @GraphQLArgument(name = "postId") String postId,
            @GraphQLArgument(name = "text") String text,
            @GraphQLRootContext AuthContext context) {
        Comment newComment= new Comment(context.getUser().getId(), postId, text, Instant.now().atZone(ZoneOffset.UTC));
        return commentRepo.saveComment(newComment);
    }
}
