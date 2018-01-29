package com.sudobangbang.graphql.model;

import java.time.ZonedDateTime;

public class Comment implements CreatedByUser {
    private final String id;
    private final String userId;
    private final String postId;
    private final String text;
    private final ZonedDateTime createdAt;

    public Comment(String userId, String postId, String text, ZonedDateTime createdAt) {
        this(null, userId, postId, text, createdAt);
    }

    public Comment(String id, String userId, String postId, String text, ZonedDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
        this.text = text;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getPostId() {
        return postId;
    }

    public String getText() {
        return text;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}
