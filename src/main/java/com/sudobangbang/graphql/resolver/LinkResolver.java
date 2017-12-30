package com.sudobangbang.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.sudobangbang.graphql.model.Link;
import com.sudobangbang.graphql.model.User;
import com.sudobangbang.graphql.repository.UserRepo;

public class LinkResolver implements GraphQLResolver<Link> {

    private final UserRepo userRepo;

    public LinkResolver(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User postedBy(Link link) {
        if (link.getUserId() == null) {
            return null;
        }
        return userRepo.findById(link.getUserId());
    }
}