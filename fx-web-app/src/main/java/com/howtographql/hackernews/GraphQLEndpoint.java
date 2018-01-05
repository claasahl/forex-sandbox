package com.howtographql.hackernews;

import javax.servlet.annotation.WebServlet;
import com.coxautodev.graphql.tools.SchemaParser;
import graphql.schema.GraphQLSchema;
import graphql.servlet.SimpleGraphQLServlet;

@WebServlet(urlPatterns = "/graphql")
public class GraphQLEndpoint extends SimpleGraphQLServlet {
	private static final long serialVersionUID = 7727407048958240998L;
	private static final LinkRepository linkRepository;
	private static final UserRepository userRepository;

	static {
		linkRepository = new LinkRepository();
		userRepository = new UserRepository();
	}

	public GraphQLEndpoint() {
		super(buildSchema());
	}

	private static GraphQLSchema buildSchema() {
		return SchemaParser.newParser()
				.file("schema.graphqls")
				.resolvers(new Query(linkRepository), new Mutation(linkRepository, userRepository))
				.build()
				.makeExecutableSchema();
	}
}
