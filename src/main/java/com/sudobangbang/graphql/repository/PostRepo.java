package com.sudobangbang.graphql.repository;

import com.sudobangbang.graphql.model.Post;
import com.sudobangbang.graphql.model.filter.PostFilter;

import java.util.List;

public interface PostRepo {
    List<Post> getAllPosts(PostFilter filter, int skip, int first);
    List<Post> findByBlogId(String blogId);
    List<Post> findByLinkId(String linkId);
    Post findById(String postId);
    Post savePost(Post post);
    Post updatePost(Post post);
}
