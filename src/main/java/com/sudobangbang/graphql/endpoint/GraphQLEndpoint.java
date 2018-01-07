package com.sudobangbang.graphql.endpoint;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.sudobangbang.graphql.model.Blog;
import com.sudobangbang.graphql.model.User;
import com.sudobangbang.graphql.mutation.AuthMutations;
import com.sudobangbang.graphql.mutation.BlogMutations;
import com.sudobangbang.graphql.mutation.LinkMutations;
import com.sudobangbang.graphql.query.BlogQuerys;
import com.sudobangbang.graphql.query.LinkQuerys;
import com.sudobangbang.graphql.query.VoteQuerys;
import com.sudobangbang.graphql.repository.*;
import com.sudobangbang.graphql.resolver.*;
import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.schema.GraphQLSchema;
import graphql.servlet.GraphQLContext;
import graphql.servlet.SimpleGraphQLServlet;
import io.leangen.graphql.GraphQLSchemaGenerator;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@WebServlet(urlPatterns = "/graphql")
public class GraphQLEndpoint extends SimpleGraphQLServlet {

    private static final LinkRepo linkRepo;
    private static final UserRepo userRepo;
    private static final VoteRepo voteRepo;
    private static final BlogRepo blogRepo;
    private static final PostRepo postRepo;
    private static final CommentRepo commentRepo;

    //TODO graphql-java does not currently support subscriptions, update when support is added

    static {
        //TODO I should probably fix the db name, but I don't want to lose all my data
        //If I do change it, remember to update the hardcoded auth token in index.html
        MongoDatabase mongo = new MongoClient().getDatabase("gaphql_tutorial");
        linkRepo = new LinkRepoMongo(mongo.getCollection("links"));
        userRepo = new UserRepoMongo(mongo.getCollection("users"));
        voteRepo = new VoteRepoMongo(mongo.getCollection("votes"));
        blogRepo = new BlogRepoMongo(mongo.getCollection("blogs"));
        postRepo = new PostRepoMongo(mongo.getCollection("posts"));
        commentRepo = new CommentRepoMongo(mongo.getCollection("comments"));

    }

    public GraphQLEndpoint() {
        super(buildSchema());
    }

    private static GraphQLSchema buildSchema() {
        LinkQuerys linkQuerys = new LinkQuerys(linkRepo); //create or inject the service beans
        VoteQuerys voteQuerys = new VoteQuerys(voteRepo);
        BlogQuerys blogQuerys = new BlogQuerys(blogRepo);
        LinkMutations linkMutations = new LinkMutations(linkRepo, voteRepo);
        AuthMutations authMutations = new AuthMutations(userRepo);
        BlogMutations blogMutations = new BlogMutations(blogRepo);
        LinkResolver linkResolver = new LinkResolver(userRepo, voteRepo);
        VoteResolver voteResolver = new VoteResolver(linkRepo, userRepo);

        return new GraphQLSchemaGenerator()
                .withOperationsFromSingletons(
                    linkQuerys, voteQuerys, blogQuerys,
                    linkMutations, authMutations, blogMutations,
                    linkResolver, voteResolver
                ).generate();
    }

    @Override
    protected GraphQLContext createContext(Optional<HttpServletRequest> request, Optional<HttpServletResponse> response) {
        User user = request
                .map(req -> req.getHeader("Authorization"))
                .filter(id -> !id.isEmpty())
                .map(id -> id.replace("Bearer ", ""))
                .map(userRepo::findById)
                .orElse(null);
        return new AuthContext(user, request, response);
    }

    @Override
    protected List<GraphQLError> filterGraphQLErrors(List<GraphQLError> errors) {
        return errors.stream()
                .filter(e -> e instanceof ExceptionWhileDataFetching || super.isClientError(e))
                .map(e -> e instanceof ExceptionWhileDataFetching ? new SanitizedError((ExceptionWhileDataFetching) e) : e)
                .collect(Collectors.toList());
    }

}

