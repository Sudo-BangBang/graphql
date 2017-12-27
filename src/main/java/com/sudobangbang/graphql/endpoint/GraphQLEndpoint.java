package com.sudobangbang.graphql.endpoint;

import com.coxautodev.graphql.tools.SchemaParser;
import javax.servlet.annotation.WebServlet;

import com.sudobangbang.graphql.repository.LinkRepo;
import com.sudobangbang.graphql.repository.LinkRepoInMemory;
import com.sudobangbang.graphql.resolver.Query;
import graphql.schema.GraphQLSchema;
import graphql.servlet.SimpleGraphQLServlet;


@WebServlet(urlPatterns = "/graphql")
public class GraphQLEndpoint extends SimpleGraphQLServlet {

    public GraphQLEndpoint() {
        super(buildSchema());
    }

    private static GraphQLSchema buildSchema(){
        LinkRepo linkRepo = new LinkRepoInMemory();
        return SchemaParser.newParser()
                .file("schema.graphqls") //parse the schema file created earlier
                .resolvers(new Query(linkRepo))
                .build()
                .makeExecutableSchema();
    }
}

