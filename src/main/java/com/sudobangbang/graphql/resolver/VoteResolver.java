package com.sudobangbang.graphql.resolver;

import com.sudobangbang.graphql.model.CreatedByUser;
import com.sudobangbang.graphql.model.Link;
import com.sudobangbang.graphql.model.User;
import com.sudobangbang.graphql.model.vote.HasVotes;
import com.sudobangbang.graphql.model.vote.Vote;
import com.sudobangbang.graphql.model.vote.VoteList;
import com.sudobangbang.graphql.repository.LinkRepo;
import com.sudobangbang.graphql.repository.UserRepo;
import com.sudobangbang.graphql.repository.VoteRepo;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;

import java.util.List;

public class VoteResolver {
    private final LinkRepo linkRepo;
    private final UserRepo userRepo;
    private final VoteRepo voteRepo;

    public VoteResolver(LinkRepo linkRepo, UserRepo userRepo, VoteRepo voteRepo) {
        this.linkRepo = linkRepo;
        this.userRepo = userRepo;
        this.voteRepo = voteRepo;
    }

    @GraphQLQuery
    public User user(@GraphQLContext CreatedByUser createdByUser){
        return userRepo.findById(createdByUser.getUserId());
    }

    //TODO it would be real nice to be able to return a votes subject object
//    @GraphQLQuery
//    public Link link(@GraphQLContext Vote vote){
//        return linkRepo.findById(vote.getSubjectId());
//    }

    @GraphQLQuery
    public VoteList voteList(@GraphQLContext HasVotes subject){
        List<Vote> votes = voteRepo.findBySubjectId(subject.getId());
        return new VoteList(votes.size(), votes);
    }
}
