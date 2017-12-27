package com.sudobangbang.graphql.repository;

import com.sudobangbang.graphql.model.Link;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LinkRepoInMemory implements LinkRepo {

    private final List<Link> links;

    public LinkRepoInMemory() {
        links = new ArrayList<>();
        //add some links to start off with
        links.add(new Link("1", "http://howtographql.com", "Your favorite GraphQL page"));
        links.add(new Link("2", "http://graphql.org/learn/", "The official docks"));
    }

    @Override
    public Link findById(String id) {
        Optional<Link> link = links.stream().filter(l -> l.getId().equals(id)).findFirst();
        //TODO make the whole interface optional and do it all properly
        if(link.isPresent()){
            return link.get();
        }else{
            return null;
        }
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
