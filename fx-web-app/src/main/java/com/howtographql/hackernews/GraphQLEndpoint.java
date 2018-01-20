package com.howtographql.hackernews;

import java.io.InputStreamReader;
import java.util.Optional;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import graphql.execution.*;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.*;
import graphql.servlet.*;

@WebServlet(urlPatterns = "/graphql")
public class GraphQLEndpoint extends SimpleGraphQLServlet {
	// TODO add support for subscriptions?
	// TODO https://github.com/graphql-java/graphql-java-subscription-example
	// TODO consider error handling (i.e. sanitizing of exceptions)
	// TODO use avoid using graphql-java-tools?
	// TODO https://github.com/graphql-java/graphql-java-http-example
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
		super(buildSchema(), executionStrategy());
	}

	@Override
	protected GraphQLContext createContext(Optional<HttpServletRequest> request,
			Optional<HttpServletResponse> response) {
		User user = request
				.map(req -> req.getHeader("Authorization"))
				.filter(id -> !id.isEmpty())
				.map(id -> id.replace("Bearer ", ""))
				.map(userRepository::findByToken)
				.orElse(null);
		return new AuthContext(user, request, response);
	}
	
	private static GraphQLSchema buildSchema() {
		final String schemaFile = "schema.graphqls";
		final ClassLoader classLoader = GraphQLEndpoint.class.getClassLoader();
		TypeDefinitionRegistry typeRegistry = new SchemaParser()
				.parse(new InputStreamReader(classLoader.getResourceAsStream(schemaFile)));
		RuntimeWiring wiring = RuntimeWiring.newRuntimeWiring()
				.scalar(Scalars.dateTime)
				.type("Query", typeWiring -> typeWiring.dataFetcher("allLinks", new Query(linkRepository)::allLinks))
				.type("Mutation", typeWiring -> {
					Mutation mutation = new Mutation(linkRepository, userRepository, voteRepository);
					typeWiring.dataFetcher("createUser", mutation::createUser)
							.dataFetcher("createLink", mutation::createLink)
							.dataFetcher("signinUser", mutation::signinUser)
							.dataFetcher("createVote", mutation::createVote);
					return typeWiring;
				})
				.type("Subscription",
						typeWiring -> typeWiring.dataFetcher("linkCreated", new Subscription()::linkCreated))
				.type("SigninPayload", typeWiring -> typeWiring.dataFetcher("user", new SigninResolver()::user))
				.type("Link",
						typeWiring -> typeWiring.dataFetcher("postedBy", new LinkResolver(userRepository)::postedBy))
				.type("Vote", typeWiring -> {
					VoteResolver resolver = new VoteResolver(linkRepository, userRepository);
					typeWiring.dataFetcher("link", resolver::link)
							.dataFetcher("user", resolver::user);
					return typeWiring;
				})
				.build();
		return new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);
	}
	
	private static ExecutionStrategyProvider executionStrategy() {
		return new DefaultExecutionStrategyProvider(new AsyncExecutionStrategy(), new AsyncExecutionStrategy(), new SubscriptionExecutionStrategy());
	}
}
