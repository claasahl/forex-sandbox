package com.howtographql.hackernews;

import java.io.InputStreamReader;
import graphql.execution.*;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.*;
import graphql.servlet.*;

public class GraphQLLocalSchema {
	private static final LinkRepository linkRepository;
	public static final UserRepository userRepository;
	private static final VoteRepository voteRepository;

	static {
		linkRepository = new LinkRepository();
		userRepository = new UserRepository();
		voteRepository = new VoteRepository();
	}
	
	public static GraphQLSchema buildSchema() {
		final String schemaFile = "schema.graphqls";
		final ClassLoader classLoader = GraphQLEndpoint.class.getClassLoader();
		TypeDefinitionRegistry typeRegistry = new SchemaParser()
				.parse(new InputStreamReader(classLoader.getResourceAsStream(schemaFile)));
		RuntimeWiring wiring = RuntimeWiring.newRuntimeWiring()
				.scalar(Scalars.dateTime)
				.type("Query", typeWiring -> { 
					Query query = new Query(linkRepository);
					typeWiring.dataFetcher("allLinks", query::allLinks)
							.dataFetcher("oneLink", query::oneLink);
					return typeWiring;
				})
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
	
	public static ExecutionStrategyProvider executionStrategy() {
		return new DefaultExecutionStrategyProvider(new AsyncExecutionStrategy(), new AsyncExecutionStrategy(), new SubscriptionExecutionStrategy());
	}
}
