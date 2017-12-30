package com.sudobangbang.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.sudobangbang.graphql.model.Link;
import com.sudobangbang.graphql.model.User;
import com.sudobangbang.graphql.model.Vote;
import com.sudobangbang.graphql.repository.LinkRepo;
import com.sudobangbang.graphql.repository.UserRepo;

public class VoteResolver implements GraphQLResolver<Vote> {
    private final LinkRepo linkRepo;
    private final UserRepo userRepo;

    public VoteResolver(LinkRepo linkRepo, UserRepo userRepo) {
        this.linkRepo = linkRepo;
        this.userRepo = userRepo;
    }

    public User user(Vote vote){
        return userRepo.findById(vote.getUserId());
    }

    public Link link(Vote vote){
        return linkRepo.findById(vote.getLinkId());
    }
}
