package com.sudobangbang.graphql.query;

import com.sudobangbang.graphql.model.Post;
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

    @GraphQLQuery
    public List<Post> allPosts(@GraphQLArgument(name = "filter") PostFilter filter,
                               @GraphQLArgument(name = "skip", defaultValue = "0") Number skip,
                               @GraphQLArgument(name = "first", defaultValue = "0") Number first) {
        return postRepo.getAllPosts(filter, skip.intValue(), first.intValue());
    }

    @GraphQLQuery
    public Post getPost(@GraphQLArgument(name = "postId") String postId){
        return postRepo.findById(postId);
    }

    @GraphQLQuery
    public List<Post> getBlogPosts(@GraphQLArgument(name = "blogId") String blogId){
        return postRepo.findByBlogId(blogId);
    }

    @GraphQLQuery
    public List<Post> getLinkPosts(@GraphQLArgument(name = "linkId") String linkId){
        return postRepo.findByLinkId(linkId);
    }
}
