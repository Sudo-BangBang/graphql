package com.sudobangbang.graphql.endpoint;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.sudobangbang.graphql.model.Blog;
import com.sudobangbang.graphql.model.User;
import com.sudobangbang.graphql.mutation.*;
import com.sudobangbang.graphql.query.*;
import com.sudobangbang.graphql.repository.*;
import com.sudobangbang.graphql.resolver.*;
import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.schema.GraphQLSchema;
import graphql.servlet.GraphQLContext;
import graphql.servlet.SimpleGraphQLServlet;
import io.leangen.graphql.GraphQLSchemaGenerator;

import java.io.IOException;
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
        PostQuerys postQuerys = new PostQuerys(postRepo);
        CommentQuerys commentQuerys = new CommentQuerys(commentRepo);
        LinkMutations linkMutations = new LinkMutations(linkRepo, voteRepo);
        AuthMutations authMutations = new AuthMutations(userRepo);
        BlogMutations blogMutations = new BlogMutations(blogRepo);
        PostMutations postMutations = new PostMutations(postRepo);
        CommentMutations commentMutations = new CommentMutations(commentRepo);
        LinkResolver linkResolver = new LinkResolver(userRepo, voteRepo);
        VoteResolver voteResolver = new VoteResolver(linkRepo, userRepo, voteRepo);
        BlogResolver blogResolver = new BlogResolver(postRepo);
        PostResolver postResolver = new PostResolver(commentRepo, linkRepo, blogRepo);

        return new GraphQLSchemaGenerator()
                .withOperationsFromSingletons(
                    linkQuerys, voteQuerys, blogQuerys, postQuerys, commentQuerys,
                    linkMutations, authMutations, blogMutations, postMutations, commentMutations,
                    linkResolver, voteResolver, blogResolver, postResolver
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


    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        setAccessControlHeaders(req, resp);
        super.doOptions(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        setAccessControlHeaders(req, resp);
        super.doPost(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        setAccessControlHeaders(req, resp);
        super.doGet(req, resp);
    }

    private void setAccessControlHeaders(HttpServletRequest req, HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", req.getHeader("origin"));
        resp.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, TRACE, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "X-Requested-With,Content-Type,Accept,Origin,Authorization");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
    }


}

