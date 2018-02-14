package com.sudobangbang.graphql.model;

public class Sort {

    private PostSortFieldEnum field;
    private Boolean ascending;

    public PostSortFieldEnum getField() {
        return field;
    }

    public void setField(PostSortFieldEnum field) {
        this.field = field;
    }

    public Boolean getAscending() {
        return ascending;
    }

    public void setAscending(Boolean ascending) {
        this.ascending = ascending;
    }
}
