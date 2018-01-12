package com.sudobangbang.graphql.mutation;

import com.sudobangbang.graphql.model.Post;
import com.sudobangbang.graphql.repository.PostRepo;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;

import java.time.Instant;
import java.time.ZoneOffset;

public class PostMutations {
    private final PostRepo postRepo;

    public PostMutations(PostRepo postRepo) {
        this.postRepo = postRepo;
    }

    @GraphQLMutation
    public Post createPost(
            @GraphQLArgument(name = "blogId") String blogId,
            @GraphQLArgument(name = "linkId") String linkId) {
        Post newPost= new Post(blogId, linkId, Instant.now().atZone(ZoneOffset.UTC));
        return postRepo.savePost(newPost);
    }
}
