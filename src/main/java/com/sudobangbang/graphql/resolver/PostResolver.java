package com.sudobangbang.graphql.resolver;

import com.sudobangbang.graphql.model.*;
import com.sudobangbang.graphql.model.comment.Comment;
import com.sudobangbang.graphql.model.comment.CommentList;
import com.sudobangbang.graphql.model.comment.HasComments;
import com.sudobangbang.graphql.repository.BlogRepo;
import com.sudobangbang.graphql.repository.CommentRepo;
import com.sudobangbang.graphql.repository.LinkRepo;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLIgnore;
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
    public Link link(@GraphQLContext Post post){
        return linkRepo.findById(post.getLinkId());
    }

    @GraphQLQuery
    public CommentList commentList(@GraphQLContext HasComments subject,
                                   @GraphQLArgument(name = "skip", defaultValue = "0") Number skip,
                                   @GraphQLArgument(name = "first", defaultValue = "0") Number first) {
        List<Comment> comments = commentRepo.findBySubjectId(subject.getId(), skip.intValue(), first.intValue());
        return new CommentList(comments.size(), comments);
    }

    @GraphQLQuery
    public Blog blog(@GraphQLContext Post post){
        return blogRepo.findById(post.getBlogId());
    }

}
