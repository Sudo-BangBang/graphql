package com.sudobangbang.graphql.repository;

import com.sudobangbang.graphql.model.Link;
import com.sudobangbang.graphql.model.LinkFilter;

import java.util.List;

public interface LinkRepo {
    List<Link> getAllLinks(LinkFilter filter, int skip, int first);

    Link findById(String id);

    void saveLink(Link link);
}
