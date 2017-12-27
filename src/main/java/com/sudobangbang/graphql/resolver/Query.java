package com.sudobangbang.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import com.sudobangbang.graphql.model.Link;
import com.sudobangbang.graphql.repository.LinkRepository;

import java.util.List;

public class Query implements GraphQLRootResolver {
    private final LinkRepository linkRepository;

    public Query(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public List<Link> allLinks() {
        return linkRepository.getAllLinks();
    }
}
