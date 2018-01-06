package com.sudobangbang.graphql.resolver;

import com.sudobangbang.graphql.model.Link;
import com.sudobangbang.graphql.model.User;
import com.sudobangbang.graphql.repository.UserRepo;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;

public class LinkResolver {

    private final UserRepo userRepo;

    public LinkResolver(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GraphQLQuery
    public User postedBy(@GraphQLContext Link link) {
        if (link.getUserId() == null) {
            return null;
        }
        return userRepo.findById(link.getUserId());
    }
}