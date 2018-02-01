package com.sudobangbang.graphql.model.comment;

import com.sudobangbang.graphql.model.CreatedByUser;
import com.sudobangbang.graphql.model.vote.HasVotes;

import java.time.ZonedDateTime;

public class Comment implements CreatedByUser, HasComments, HasVotes {
    private final String id;
    private final String userId;
    private final String subjectId;
    private final String text;
    private final ZonedDateTime createdAt;
    private final Integer voteTotal;

    public Comment(String userId, String subjectId, String text, ZonedDateTime createdAt, Integer voteTotal) {
        this(null, userId, subjectId, text, createdAt, voteTotal);
    }

    public Comment(String id, String userId, String subjectId, String text, ZonedDateTime createdAt, Integer voteTotal) {
        this.id = id;
        this.userId = userId;
        this.subjectId = subjectId;
        this.text = text;
        this.createdAt = createdAt;
        this.voteTotal = voteTotal;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public String getText() {
        return text;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public Integer getVoteTotal() {
        return voteTotal;
    }
}
