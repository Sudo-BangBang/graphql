package com.sudobangbang.graphql.resolver;

import com.sudobangbang.graphql.model.Blog;
import com.sudobangbang.graphql.model.Post;
import com.sudobangbang.graphql.model.Sort;
import com.sudobangbang.graphql.repository.PostRepo;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;

import java.util.List;

public class BlogResolver {

    private final PostRepo postRepo;

    public BlogResolver(PostRepo postRepo) {
        this.postRepo = postRepo;
    }

    @GraphQLQuery(description = "Get the posts for this blog, sortable and pageable")
    public List<Post> posts(@GraphQLContext Blog blog,
                            @GraphQLArgument(name = "sort", description = "Set the field to sort on and the direction") Sort sort,
                            @GraphQLArgument(name = "skip", defaultValue = "0") Number skip,
                            @GraphQLArgument(name = "first", defaultValue = "0") Number first){
        return postRepo.findByBlogId(blog.getId(), sort, skip.intValue(), first.intValue());
    }
}
