package com.sudobangbang.graphql.model;

public class Blog {
    private final String id;
    private final String name;
    private final String description;

    public Blog(String name, String description) {
        this(null, name, description);
    }

    public Blog(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
