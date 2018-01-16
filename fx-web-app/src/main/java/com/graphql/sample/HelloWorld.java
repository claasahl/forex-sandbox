package com.graphql.sample;

import static graphql.schema.idl.RuntimeWiring.*;
import graphql.*;
import graphql.schema.*;
import graphql.schema.idl.*;

public class HelloWorld {

	public static void main(String[] args) {
		String schema = "type Query{hello: String}";

		SchemaParser schemaParser = new SchemaParser();
		TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(schema);

		RuntimeWiring runtimeWiring = newRuntimeWiring()
				.type("Query", builder -> builder.dataFetcher("hello", new StaticDataFetcher("world")))
				.build();

		SchemaGenerator schemaGenerator = new SchemaGenerator();
		GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);

		GraphQL build = GraphQL.newGraphQL(graphQLSchema).build();
		ExecutionResult executionResult = build.execute("{hello}");

		System.out.println(executionResult.getData().toString());
		// Prints: {hello=world}
	}
}