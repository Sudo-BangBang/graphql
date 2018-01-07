package com.sudobangbang.graphql.query;

import com.sudobangbang.graphql.endpoint.AuthContext;
import com.sudobangbang.graphql.model.Vote;
import com.sudobangbang.graphql.repository.VoteRepo;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.annotations.GraphQLRootContext;

import java.util.List;

public class VoteQuerys {

    private final VoteRepo voteRepo;

    public VoteQuerys(VoteRepo voteRepo) {
        this.voteRepo = voteRepo;
    }

    @GraphQLQuery
    public List<Vote> getVotes(@GraphQLArgument(name = "userId") String userId, @GraphQLArgument(name = "linkId") String linkId) {
        if (userId != null) {
            return voteRepo.findByUserId(userId);
        } else if (linkId != null) {
            return voteRepo.findByLinkId(linkId);
        }else{
            return voteRepo.getAllVotes();
        }
    }

    @GraphQLQuery
    public List<Vote> getMyVotes(@GraphQLRootContext AuthContext context) {
        return voteRepo.findByUserId(context.getUser().getId());
    }
}
