package com.sudobangbang.graphql.mutation;

import com.sudobangbang.graphql.model.Post;
import com.sudobangbang.graphql.repository.PostRepo;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLNonNull;

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
        Post newPost= new Post(blogId, linkId, Instant.now().atZone(ZoneOffset.UTC), 0);
        return postRepo.savePost(newPost);
    }


    @GraphQLMutation
    public Post updatePost(
            @GraphQLArgument(name = "id") @GraphQLNonNull String id,
            @GraphQLArgument(name = "blogId") String blogId,
            @GraphQLArgument(name = "linkId") String linkId,
            @GraphQLArgument(name = "voteTotal") Integer voteTotal) {
        Post post = new Post(id, blogId, linkId, null, voteTotal);
        return postRepo.updatePost(post);
    }
}
