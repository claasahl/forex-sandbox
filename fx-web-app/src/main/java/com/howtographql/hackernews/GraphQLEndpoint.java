package com.howtographql.hackernews;

import javax.servlet.annotation.WebServlet;
import com.coxautodev.graphql.tools.SchemaParser;
import graphql.schema.GraphQLSchema;
import graphql.servlet.SimpleGraphQLServlet;

@WebServlet(urlPatterns = "/graphql")
public class GraphQLEndpoint extends SimpleGraphQLServlet {

    public GraphQLEndpoint() {
        super(buildSchema(new LinkRepository()));
    }

    private static GraphQLSchema buildSchema(LinkRepository linkRepository) {
        return SchemaParser.newParser()
            .file("schema.graphqls")
            .resolvers(new Query(linkRepository), new Mutation(linkRepository))
            .build()
            .makeExecutableSchema();
    }
}
