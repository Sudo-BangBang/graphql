package com.sudobangbang.graphql.repository;

import com.sudobangbang.graphql.model.Link;

import java.util.List;

public interface LinkRepo {
    List<Link> getAllLinks();

    Link findById(String id);

    void saveLink(Link link);
}
