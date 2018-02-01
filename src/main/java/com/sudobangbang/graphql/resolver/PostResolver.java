package com.sudobangbang.graphql.resolver;

import com.sudobangbang.graphql.model.*;
import com.sudobangbang.graphql.model.comment.Comment;
import com.sudobangbang.graphql.model.comment.CommentList;
import com.sudobangbang.graphql.model.comment.HasComments;
import com.sudobangbang.graphql.repository.BlogRepo;
import com.sudobangbang.graphql.repository.CommentRepo;
import com.sudobangbang.graphql.repository.LinkRepo;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;

import java.util.List;

public class PostResolver {
    private final CommentRepo commentRepo;
    private final LinkRepo linkRepo;
    private final BlogRepo blogRepo;

    public PostResolver(CommentRepo commentRepo, LinkRepo linkRepo, BlogRepo blogRepo) {
        this.commentRepo = commentRepo;
        this.linkRepo = linkRepo;
        this.blogRepo = blogRepo;
    }

    @GraphQLQuery
    public Link postLink(@GraphQLContext Post post){
        return linkRepo.findById(post.getLinkId());
    }

    @GraphQLQuery
    public CommentList commentList(@GraphQLContext HasComments subject){
        List<Comment> comments = commentRepo.findBySubjectId(subject.getId());
        return new CommentList(comments.size(), comments);
    }

    @GraphQLQuery
    public Blog blog(@GraphQLContext Post post){
        return blogRepo.findById(post.getBlogId());
    }
}
