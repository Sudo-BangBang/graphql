package com.sudobangbang.graphql.model;

import com.sudobangbang.graphql.model.comment.HasComments;
import com.sudobangbang.graphql.model.vote.HasVotes;

import java.time.ZonedDateTime;

public class Post implements HasComments, HasVotes {
    private final String id;
    private final String blogId;
    private final String linkId;
    private final ZonedDateTime createdAt;
    private final Integer voteTotal;

    public Post(String blogId, String linkId, ZonedDateTime createdAt, Integer voteTotal) {
        this(null, blogId, linkId, createdAt, voteTotal);
    }

    public Post(String id, String blogId, String linkId, ZonedDateTime createdAt, Integer voteTotal) {
        this.id = id;
        this.blogId = blogId;
        this.linkId = linkId;
        this.createdAt = createdAt;
        this.voteTotal = voteTotal;
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

    @Override
    public Integer getVoteTotal() {
        return voteTotal;
    }
}
