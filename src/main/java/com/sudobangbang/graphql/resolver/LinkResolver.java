package com.sudobangbang.graphql.resolver;

import com.sudobangbang.graphql.model.Link;
import com.sudobangbang.graphql.model.User;
import com.sudobangbang.graphql.model.Vote;
import com.sudobangbang.graphql.repository.UserRepo;
import com.sudobangbang.graphql.repository.VoteRepo;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;

import java.util.ArrayList;
import java.util.List;

public class LinkResolver {

    private final UserRepo userRepo;
    private final VoteRepo voteRepo;

    public LinkResolver(UserRepo userRepo, VoteRepo voteRepo) {
        this.userRepo = userRepo;
        this.voteRepo = voteRepo;
    }

    @GraphQLQuery
    public User postedBy(@GraphQLContext Link link) {
        if (link.getUserId() == null) {
            return null;
        }
        return userRepo.findById(link.getUserId());
    }

    @GraphQLQuery
    public List<Vote> votes(@GraphQLContext Link link) {
        if (link.getUserId() == null) {
            return new ArrayList<>();
        }
        return voteRepo.findByLinkId(link.getId());
    }
}