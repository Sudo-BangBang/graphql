package com.sudobangbang.graphql.model.filter;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PostFilter {

    private String blogId;

    @JsonProperty("blog_id") //the name must match the schema
    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blodId) {
        this.blogId = blodId;
    }
}
