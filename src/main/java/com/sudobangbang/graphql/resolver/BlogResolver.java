package com.sudobangbang.graphql.resolver;

import com.sudobangbang.graphql.model.Blog;
import com.sudobangbang.graphql.model.Post;
import com.sudobangbang.graphql.repository.PostRepo;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;

import java.util.List;

public class BlogResolver {

    private final PostRepo postRepo;

    public BlogResolver(PostRepo postRepo) {
        this.postRepo = postRepo;
    }

    @GraphQLQuery
    public List<Post> posts(@GraphQLContext Blog blog){
        return postRepo.findByBlogId(blog.getId());
    }
}
