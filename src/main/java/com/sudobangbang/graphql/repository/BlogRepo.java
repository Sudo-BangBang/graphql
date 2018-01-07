package com.sudobangbang.graphql.repository;

import com.sudobangbang.graphql.model.Blog;

import java.util.List;

public interface BlogRepo {
    List<Blog> getAllBlogs();
    Blog findById(String blogId);
    Blog saveBlog(Blog blog);
}
