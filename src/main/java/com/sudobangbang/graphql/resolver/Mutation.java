package com.sudobangbang.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import com.sudobangbang.graphql.model.Link;
import com.sudobangbang.graphql.repository.LinkRepo;

public class Mutation implements GraphQLRootResolver {

    private final LinkRepo linkRepo;

    public Mutation(LinkRepo linkRepo) {
        this.linkRepo = linkRepo;
    }

    public Link createLink(String url, String description){
        Link newLink = new Link(url, description);
        linkRepo.saveLink(newLink);
        return newLink;
    }
}
