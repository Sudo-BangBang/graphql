package com.sudobangbang.graphql.mutation;

import com.sudobangbang.graphql.endpoint.AuthContext;
import com.sudobangbang.graphql.model.*;
import com.sudobangbang.graphql.model.vote.Vote;
import com.sudobangbang.graphql.repository.LinkRepo;
import com.sudobangbang.graphql.repository.VoteRepo;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLRootContext;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class LinkMutations {

    private final LinkRepo linkRepo;
    private final VoteRepo voteRepo;

    public LinkMutations(LinkRepo linkRepo, VoteRepo voteRepo) {
        this.linkRepo = linkRepo;
        this.voteRepo = voteRepo;
    }

    //Using GraphQLArgument can be avoided by adding "-parameters" as a compiler arg
    @GraphQLMutation
    public Link createLink(
            @GraphQLArgument(name = "url", description = "The url of the link") String url,
            @GraphQLArgument(name = "description") String description,
            @GraphQLRootContext AuthContext context) {
        Link newLink = new Link(url, description, context.getUser().getId());
        return linkRepo.saveLink(newLink);
    }

    @GraphQLMutation
    public Vote createVote(
            @GraphQLArgument(name = "linkId") String linkId,
            @GraphQLArgument(name = "userId") String userId) {
        ZonedDateTime now = Instant.now().atZone(ZoneOffset.UTC);
        return voteRepo.saveVote(new Vote(now, userId, linkId));
    }
}
