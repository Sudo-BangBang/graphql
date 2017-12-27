package com.sudobangbang.graphql.endpoint;

import com.coxautodev.graphql.tools.SchemaParser;
import javax.servlet.annotation.WebServlet;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.sudobangbang.graphql.repository.LinkRepo;
import com.sudobangbang.graphql.repository.LinkRepoMongo;
import com.sudobangbang.graphql.resolver.Mutation;
import com.sudobangbang.graphql.resolver.Query;
import graphql.schema.GraphQLSchema;
import graphql.servlet.SimpleGraphQLServlet;


@WebServlet(urlPatterns = "/graphql")
public class GraphQLEndpoint extends SimpleGraphQLServlet {

    private static final LinkRepo linkRepo;

    static {
        MongoDatabase mongo = new MongoClient().getDatabase("gaphql_tutorial");
        linkRepo = new LinkRepoMongo(mongo.getCollection("links"));
    }

    public GraphQLEndpoint() {
        super(buildSchema());
    }

    private static GraphQLSchema buildSchema(){
        return SchemaParser.newParser()
                .file("schema.graphqls") //parse the schema file created earlier
                .resolvers(new Query(linkRepo), new Mutation(linkRepo))
                .build()
                .makeExecutableSchema();
    }
}

