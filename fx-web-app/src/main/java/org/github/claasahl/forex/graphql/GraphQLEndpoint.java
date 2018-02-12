package org.github.claasahl.forex.graphql;

import java.io.*;
import graphql.schema.*;
import graphql.schema.idl.*;
import graphql.schema.idl.TypeRuntimeWiring.Builder;
import graphql.servlet.*;

public class GraphQLEndpoint extends SimpleGraphQLServlet {
	private static final long serialVersionUID = -407893796327589811L;

	public GraphQLEndpoint() {
		super(buildSchema());
	}
	
	private static GraphQLSchema buildSchema() {
		final String schemaFile = "forex.graphqls";
		TypeDefinitionRegistry typeDefinition = typeDefinition(schemaFile);
		RuntimeWiring wiring = wiring();
		return new SchemaGenerator().makeExecutableSchema(typeDefinition, wiring);
	}
	
	private static TypeDefinitionRegistry typeDefinition(String schemaFile) {
		ClassLoader classLoader = GraphQLEndpoint.class.getClassLoader();		
		InputStream schemaStream = classLoader.getResourceAsStream(schemaFile);
		Reader schemaReader = new InputStreamReader(schemaStream);
		return new SchemaParser().parse(schemaReader);
	}
	
	private static RuntimeWiring wiring() {
		return RuntimeWiring.newRuntimeWiring()
			.scalar(Scalars.dateTime)
			.type("Query", GraphQLEndpoint::wiringForQuery)
			.type("Broker", GraphQLEndpoint::wiringForBroker)
			.build();
	}
	
	private static Builder wiringForQuery(Builder builder) {
		QueryResolver query = new QueryResolver();
		return builder.dataFetcher("brokers", query::getBrokers);
	}
	
	private static Builder wiringForBroker(Builder builder) {
		BrokerResolver broker = new BrokerResolver();
		return builder.dataFetcher("symbols", broker::getSymbols);
	}
}
