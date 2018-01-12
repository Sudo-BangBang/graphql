package com.sudobangbang.graphql.resolver;

import com.sudobangbang.graphql.model.Blog;
import com.sudobangbang.graphql.model.Comment;
import com.sudobangbang.graphql.model.Link;
import com.sudobangbang.graphql.model.Post;
import com.sudobangbang.graphql.repository.CommentRepo;
import com.sudobangbang.graphql.repository.LinkRepo;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;

import java.util.List;

public class PostResolver {
    private final CommentRepo commentRepo;
    private final LinkRepo linkRepo;

    public PostResolver(CommentRepo commentRepo, LinkRepo linkRepo) {
        this.commentRepo = commentRepo;
        this.linkRepo = linkRepo;
    }

    @GraphQLQuery
    public Link postLink(@GraphQLContext Post post){
        return linkRepo.findById(post.getLinkId());
    }

    @GraphQLQuery
    public List<Comment> comments(@GraphQLContext Post post){
        return commentRepo.findByPostId(post.getId());
    }
}
