package com.sudobangbang.graphql.repository;

import com.sudobangbang.graphql.model.Post;
import com.sudobangbang.graphql.model.Sort;
import com.sudobangbang.graphql.model.filter.PostFilter;

import java.util.List;

public interface PostRepo {
    List<Post> getAllPosts(PostFilter filter, Sort sort, int skip, int first);
    List<Post> findByBlogId(String blogId, Sort sort, int skip, int first);
    List<Post> findByLinkId(String linkId);
    Post findById(String postId);
    Post savePost(Post post);
    Post updatePost(Post post);
}
