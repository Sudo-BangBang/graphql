package com.sudobangbang.graphql.endpoint;

import com.coxautodev.graphql.tools.SchemaParser;
import javax.servlet.annotation.WebServlet;

import com.sudobangbang.graphql.repository.LinkRepository;
import com.sudobangbang.graphql.resolver.Query;
import graphql.schema.GraphQLSchema;
import graphql.servlet.SimpleGraphQLServlet;


@WebServlet(urlPatterns = "/graphql")
public class GraphQLEndpoint extends SimpleGraphQLServlet {

    public GraphQLEndpoint() {
        super(buildSchema());
    }

    private static GraphQLSchema buildSchema(){
        LinkRepository linkRepository = new LinkRepository();
        return SchemaParser.newParser()
                .file("schema.graphqls") //parse the schema file created earlier
                .resolvers(new Query(linkRepository))
                .build()
                .makeExecutableSchema();
    }
}

