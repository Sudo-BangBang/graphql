package com.sudobangbang.graphql.model.vote;

import java.time.ZonedDateTime;

public class Vote {

    private final String id;
    private final ZonedDateTime createdAt;
    private final String  userId;
    private final String subjectId;

    public Vote(ZonedDateTime createdAt, String userId, String subjectId){
        this(null, createdAt, userId, subjectId);
    }

    public Vote(String id, ZonedDateTime createdAt, String userId, String subjectId) {
        this.id = id;
        this.createdAt = createdAt;
        this.userId = userId;
        this.subjectId = subjectId;
    }

    public String getId() {
        return id;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public String getUserId() {
        return userId;
    }

    public String getSubjectId() {
        return subjectId;
    }
}
