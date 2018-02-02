package com.sudobangbang.graphql.mutation;

import com.sudobangbang.graphql.endpoint.AuthContext;
import com.sudobangbang.graphql.model.comment.Comment;
import com.sudobangbang.graphql.repository.CommentRepo;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLNonNull;
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
            @GraphQLArgument(name = "subjectId") String subjectId,
            @GraphQLArgument(name = "text") String text,
            @GraphQLRootContext AuthContext context) {
        Comment newComment = new Comment(context.getUser().getId(), subjectId, text, Instant.now().atZone(ZoneOffset.UTC), 0);
        return commentRepo.saveComment(newComment);
    }

    @GraphQLMutation
    public Comment updateComment(
            @GraphQLArgument(name = "id") @GraphQLNonNull String id,
            @GraphQLArgument(name = "text") String text,
            @GraphQLArgument(name = "voteTotal") Integer voteTotal) {
        Comment comment = new Comment(id, null, null, text, null, voteTotal);
        return commentRepo.updateComment(comment);
    }

}
