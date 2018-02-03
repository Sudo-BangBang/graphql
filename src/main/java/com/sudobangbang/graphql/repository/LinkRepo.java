package com.sudobangbang.graphql.repository;

import com.sudobangbang.graphql.model.Link;
import com.sudobangbang.graphql.model.filter.LinkFilter;

import java.util.List;

public interface LinkRepo {
    List<Link> getAllLinks(LinkFilter filter, int skip, int first);

    Link findById(String id);

    Link saveLink(Link link);
}
