package com.sudobangbang.graphql.resolver;

import com.sudobangbang.graphql.model.Link;
import com.sudobangbang.graphql.model.User;
import com.sudobangbang.graphql.model.Vote;
import com.sudobangbang.graphql.repository.LinkRepo;
import com.sudobangbang.graphql.repository.UserRepo;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;

public class VoteResolver {
    private final LinkRepo linkRepo;
    private final UserRepo userRepo;

    public VoteResolver(LinkRepo linkRepo, UserRepo userRepo) {
        this.linkRepo = linkRepo;
        this.userRepo = userRepo;
    }

    @GraphQLQuery
    public User user(@GraphQLContext Vote vote){
        return userRepo.findById(vote.getUserId());
    }

    @GraphQLQuery
    public Link link(@GraphQLContext Vote vote){
        return linkRepo.findById(vote.getLinkId());
    }
}
