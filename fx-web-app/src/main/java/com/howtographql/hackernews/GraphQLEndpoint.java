package com.howtographql.hackernews;

import java.util.Optional;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.coxautodev.graphql.tools.SchemaParser;
import graphql.schema.GraphQLSchema;
import graphql.servlet.*;

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
	
	@Override
	protected GraphQLContext createContext(Optional<HttpServletRequest> request, Optional<HttpServletResponse> response) {
	    User user = request
	        .map(req -> req.getHeader("Authorization"))
	        .filter(id -> !id.isEmpty())
	        .map(id -> id.replace("Bearer ", ""))
	        .map(userRepository::findByToken)
	        .orElse(null);
	    return new AuthContext(user, request, response);
	}

	private static GraphQLSchema buildSchema() {
		return SchemaParser.newParser()
				.file("schema.graphqls")
				.resolvers(
						new Query(linkRepository),
						new Mutation(linkRepository, userRepository),
						new SigninResolver(),
						new LinkResolver(userRepository))
				.build()
				.makeExecutableSchema();
	}
}
