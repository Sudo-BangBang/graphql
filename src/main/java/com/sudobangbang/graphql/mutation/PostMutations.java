package com.sudobangbang.graphql.mutation;

import com.sudobangbang.graphql.endpoint.AuthContext;
import com.sudobangbang.graphql.model.Link;
import com.sudobangbang.graphql.model.Post;
import com.sudobangbang.graphql.repository.LinkRepo;
import com.sudobangbang.graphql.repository.PostRepo;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.GraphQLRootContext;

import java.time.Instant;
import java.time.ZoneOffset;

public class PostMutations {
    private final PostRepo postRepo;
    private final LinkRepo linkRepo;

    public PostMutations(PostRepo postRepo, LinkRepo linkRepo) {
        this.postRepo = postRepo;
        this.linkRepo = linkRepo;
    }

    @GraphQLMutation
    public Post createPost(
            @GraphQLArgument(name = "blogId") String blogId,
            @GraphQLArgument(name = "linkId") String linkId) {
        Post newPost = new Post(blogId, linkId, Instant.now().atZone(ZoneOffset.UTC), 0);
        return postRepo.savePost(newPost);
    }


    @GraphQLMutation
    public Post createPostAndLink(
            @GraphQLArgument(name = "blogId") String blogId,
            @GraphQLArgument(name = "description") String description,
            @GraphQLArgument(name = "url") String url,
            @GraphQLRootContext AuthContext context) {

        Link newLink = linkRepo.saveLink(new Link(url, description, context.getUser().getId()));

        Post newPost = new Post(blogId, newLink.getId(), Instant.now().atZone(ZoneOffset.UTC), 0);
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
