package com.sudobangbang.graphql.query;

import com.sudobangbang.graphql.model.Post;
import com.sudobangbang.graphql.model.Sort;
import com.sudobangbang.graphql.model.filter.PostFilter;
import com.sudobangbang.graphql.repository.PostRepo;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;

import java.util.List;

public class PostQuerys {

    private final PostRepo postRepo;

    public PostQuerys(PostRepo postRepo) {
        this.postRepo = postRepo;
    }

    @GraphQLQuery(description = "Get all posts with filters, pagination and sorting")
    public List<Post> allPosts(@GraphQLArgument(name = "filter", description = "Get posts to a certain blog ID") PostFilter filter,
                               @GraphQLArgument(name = "sort", description = "Set the field to sort on and the direction") Sort sort,
                               @GraphQLArgument(name = "skip", defaultValue = "0") Number skip,
                               @GraphQLArgument(name = "first", defaultValue = "0") Number first) {
        return postRepo.getAllPosts(filter, sort, skip.intValue(), first.intValue());
    }

    @GraphQLQuery(description = "Gets a specific post by its ID")
    public Post getPost(@GraphQLArgument(name = "postId") String postId){
        return postRepo.findById(postId);
    }
}
