package com.sudobangbang.graphql.model;

import java.util.List;

public class CommentList {

    private final Integer count;
    private List<Comment> comments;

    public CommentList(Integer count, List<Comment> comments) {
        this.count = count;
        this.comments = comments;
    }

    public Integer getCount() {
        return count;
    }

    public List<Comment> getComments() {
        return comments;
    }
}
