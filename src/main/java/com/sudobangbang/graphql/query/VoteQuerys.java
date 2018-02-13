package com.sudobangbang.graphql.query;

import com.sudobangbang.graphql.endpoint.AuthContext;
import com.sudobangbang.graphql.model.vote.Vote;
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

}
