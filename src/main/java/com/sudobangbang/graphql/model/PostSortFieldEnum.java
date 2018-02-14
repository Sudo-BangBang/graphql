package com.sudobangbang.graphql.model;

public enum PostSortFieldEnum {
    VOTE_TOTAL("voteTotal"),
    CREATED_AT("createdAt");

    private String fieldString;

    PostSortFieldEnum(String fieldString) {
        this.fieldString = fieldString;
    }

    public String getFieldString() {
        return fieldString;
    }
}
