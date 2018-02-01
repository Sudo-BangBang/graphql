package com.sudobangbang.graphql.model.comment;

import java.util.List;

public class CommentList {

    private final Integer count;
    private final List<Comment> comments;

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
