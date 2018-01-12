package com.sudobangbang.graphql.query;

import com.sudobangbang.graphql.model.Comment;
import com.sudobangbang.graphql.repository.CommentRepo;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;

import java.util.List;

public class CommentQuerys {

    private final CommentRepo commentRepo;

    public CommentQuerys(CommentRepo commentRepo) {
        this.commentRepo = commentRepo;
    }

    @GraphQLQuery
    public Comment getComment(@GraphQLArgument(name = "id") String id){
        return commentRepo.findById(id);
    }

    @GraphQLQuery
    public List<Comment> getPostComments(@GraphQLArgument(name = "postId") String postId){
        return commentRepo.findByPostId(postId);
    }

    @GraphQLQuery
    public List<Comment> getUsersComments(@GraphQLArgument(name = "userId") String userId){
        return commentRepo.findByUserId(userId);
    }
}
