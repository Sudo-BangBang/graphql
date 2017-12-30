package com.sudobangbang.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import com.sudobangbang.graphql.model.Link;
import com.sudobangbang.graphql.model.LinkFilter;
import com.sudobangbang.graphql.repository.LinkRepo;

import java.util.List;

public class Query implements GraphQLRootResolver {
    private final LinkRepo linkRepo;

    public Query(LinkRepo linkRepo) {
        this.linkRepo = linkRepo;
    }

    public List<Link> allLinks(LinkFilter filter, Number skip, Number first) {
        return linkRepo.getAllLinks(filter, skip.intValue(), first.intValue());
    }

}
