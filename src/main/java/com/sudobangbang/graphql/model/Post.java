package com.sudobangbang.graphql.model;

import java.time.ZonedDateTime;

public class Post {
    private final String id;
    private final String blogId;
    private final String linkId;
    private final ZonedDateTime createdAt;

    public Post(String blogId, String linkId, ZonedDateTime createdAt) {
        this(null, blogId, linkId, createdAt);
    }

    public Post(String id, String blogId, String linkId, ZonedDateTime createdAt) {
        this.id = id;
        this.blogId = blogId;
        this.linkId = linkId;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public String getBlogId() {
        return blogId;
    }

    public String getLinkId() {
        return linkId;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}