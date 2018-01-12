package com.howtographql.hackernews;

import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.coxautodev.graphql.tools.SchemaParser;
import graphql.*;
import graphql.schema.GraphQLSchema;
import graphql.servlet.*;

@WebServlet(urlPatterns = "/graphql")
public class GraphQLEndpoint extends SimpleGraphQLServlet {
	// TODO added support for subscriptions?
	private static final long serialVersionUID = 7727407048958240998L;
	private static final LinkRepository linkRepository;
	private static final UserRepository userRepository;
	private static final VoteRepository voteRepository;

	static {
		linkRepository = new LinkRepository();
		userRepository = new UserRepository();
		voteRepository = new VoteRepository();
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
	
	@Override
	protected List<GraphQLError> filterGraphQLErrors(List<GraphQLError> errors) {
		return errors.stream()
	            .filter(e -> e instanceof ExceptionWhileDataFetching || super.isClientError(e))
	            .map(e -> e instanceof ExceptionWhileDataFetching ? new SanitizedError((ExceptionWhileDataFetching) e) : e)
	            .collect(Collectors.toList());
	}

	private static GraphQLSchema buildSchema() {
		return SchemaParser.newParser()
				.file("schema.graphqls")
				.resolvers(
						new Query(linkRepository),
						new Mutation(linkRepository, userRepository, voteRepository),
						new Subscription(),
						new SigninResolver(),
						new LinkResolver(userRepository),
						new VoteResolver(linkRepository, userRepository))
				.scalars(Scalars.dateTime)
				.build()
				.makeExecutableSchema();
	}
}
