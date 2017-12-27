package com.sudobangbang.graphql.repository;

import com.sudobangbang.graphql.model.Link;

import java.util.ArrayList;
import java.util.List;

public class LinkRepoInMemory implements LinkRepo {

    private final List<Link> links;

    public LinkRepoInMemory() {
        links = new ArrayList<>();
        //add some links to start off with
        links.add(new Link("http://howtographql.com", "Your favorite GraphQL page"));
        links.add(new Link("http://graphql.org/learn/", "The official docks"));
    }

    @Override
    public List<Link> getAllLinks() {
        return links;
    }

    @Override
    public void saveLink(Link link) {
        links.add(link);
    }
}
