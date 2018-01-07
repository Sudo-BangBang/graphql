package com.sudobangbang.graphql.mutation;

import com.sudobangbang.graphql.model.Blog;
import com.sudobangbang.graphql.repository.BlogRepo;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLRootContext;

public class BlogMutations {

    private final BlogRepo blogRepo;

    public BlogMutations(BlogRepo blogRepo) {
        this.blogRepo = blogRepo;
    }

    @GraphQLMutation
    public Blog createBlog(
            @GraphQLArgument(name = "name") String name,
            @GraphQLArgument(name = "description") String description) {
        Blog newBlog= new Blog(name, description);
        return blogRepo.saveBlog(newBlog);
    }
}
