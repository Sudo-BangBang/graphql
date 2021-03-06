package com.sudobangbang.graphql.query;

import com.sudobangbang.graphql.model.Link;
import com.sudobangbang.graphql.model.filter.LinkFilter;
import com.sudobangbang.graphql.repository.LinkRepo;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;

import java.util.List;

public class LinkQuerys {
    private final LinkRepo linkRepo;

    public LinkQuerys(LinkRepo linkRepo) {
        this.linkRepo = linkRepo;
    }

    @GraphQLQuery(description = "Get all links with filters and pagination")
    public List<Link> allLinks(@GraphQLArgument(name = "filter") LinkFilter filter,
                               @GraphQLArgument(name = "skip", defaultValue = "0") Number skip,
                               @GraphQLArgument(name = "first", defaultValue = "0") Number first) {
        return linkRepo.getAllLinks(filter, skip.intValue(), first.intValue());
    }

    @GraphQLQuery(description = "Get a specific link by its ID")
    public Link getLink(@GraphQLArgument(name = "id") String id){
        return linkRepo.findById(id);
    }

}
