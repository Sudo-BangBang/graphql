package com.sudobangbang.graphql.query;

import com.sudobangbang.graphql.model.Blog;
import com.sudobangbang.graphql.repository.BlogRepo;
import io.leangen.graphql.annotations.GraphQLQuery;

import java.util.List;

public class BlogQuerys {

    private final BlogRepo blogRepo;

    public BlogQuerys(BlogRepo blogRepo) {
        this.blogRepo = blogRepo;
    }

    @GraphQLQuery
    public List<Blog> allBlogs() {
        return blogRepo.getAllBlogs();
    }

}
