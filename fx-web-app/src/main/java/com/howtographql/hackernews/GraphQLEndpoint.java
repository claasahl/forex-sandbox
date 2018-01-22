package com.howtographql.hackernews;

import java.util.Optional;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import graphql.servlet.*;

@WebServlet(urlPatterns = "/graphql")
public class GraphQLEndpoint extends SimpleGraphQLServlet {
	// TODO add support for subscriptions?
	// TODO https://github.com/graphql-java/graphql-java-subscription-example
	// TODO consider error handling (i.e. sanitizing of exceptions)
	private static final long serialVersionUID = 7727407048958240998L;

	public GraphQLEndpoint() {
		super(GraphQLLocalSchema.buildSchema(), GraphQLLocalSchema.executionStrategy());
	}

	@Override
	protected GraphQLContext createContext(Optional<HttpServletRequest> request,
			Optional<HttpServletResponse> response) {
		User user = request
				.map(req -> req.getHeader("Authorization"))
				.filter(id -> !id.isEmpty())
				.map(id -> id.replace("Bearer ", ""))
				.map(GraphQLLocalSchema.userRepository::findByToken)
				.orElse(null);
		return new AuthContext(user, request, response);
	}
}
