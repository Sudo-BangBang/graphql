package com.sudobangbang.graphql.endpoint;

import com.coxautodev.graphql.tools.SchemaParser;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.sudobangbang.graphql.model.User;
import com.sudobangbang.graphql.repository.LinkRepo;
import com.sudobangbang.graphql.repository.LinkRepoMongo;
import com.sudobangbang.graphql.repository.UserRepo;
import com.sudobangbang.graphql.repository.UserRepoMongo;
import com.sudobangbang.graphql.resolver.LinkResolver;
import com.sudobangbang.graphql.resolver.Mutation;
import com.sudobangbang.graphql.resolver.Query;
import com.sudobangbang.graphql.resolver.SigninResolver;
import graphql.schema.GraphQLSchema;
import graphql.servlet.GraphQLContext;
import graphql.servlet.SimpleGraphQLServlet;

import java.util.Optional;


@WebServlet(urlPatterns = "/graphql")
public class GraphQLEndpoint extends SimpleGraphQLServlet {

    private static final LinkRepo linkRepo;
    private static final UserRepo userRepo;

    static {
        //TODO I should probably fix the db name, but I don't want to lose all my data
        //If I do change it, remember to update the hardcoded auth token in index.html
        MongoDatabase mongo = new MongoClient().getDatabase("gaphql_tutorial");
        linkRepo = new LinkRepoMongo(mongo.getCollection("links"));
        userRepo = new UserRepoMongo(mongo.getCollection("users"));
    }

    public GraphQLEndpoint() {
        super(buildSchema());
    }

    private static GraphQLSchema buildSchema(){
        return SchemaParser.newParser()
            .file("schema.graphqls") //parse the schema file created earlier
            .resolvers(
                new Query(linkRepo),
                new Mutation(linkRepo, userRepo),
                new SigninResolver(),
                new LinkResolver(userRepo))
            .build()
            .makeExecutableSchema();
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
}

