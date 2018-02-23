package org.github.claasahl.forex.graphql;

import java.io.*;
import org.github.claasahl.forex.database.*;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.*;
import graphql.schema.idl.TypeRuntimeWiring.Builder;
import graphql.servlet.SimpleGraphQLServlet;

public class GraphQLEndpoint extends SimpleGraphQLServlet {
	private static final long serialVersionUID = -407893796327589811L;
	private static final BrokerRepository BROKER_REPOSITORY = new BrokerRepository();
	private static final SymbolRepository SYMBOL_REPOSITORY = new SymbolRepository();
	private static final CandleRepository CANDLE_REPOSITORY = new CandleRepository();
	private static final RateRepository RATE_REPOSITORY = new RateRepository();

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
				.scalar(Scalars.duration)
				.type("Query", GraphQLEndpoint::wiringForQuery)
				.type("Broker", GraphQLEndpoint::wiringForBroker)
				.type("Symbol", GraphQLEndpoint::wiringForSymbol)
				.type("Candle", GraphQLEndpoint::wiringForCandle)
				.type("Rate", GraphQLEndpoint::wiringForRate)
				.build();
	}

	private static Builder wiringForQuery(Builder builder) {
		QueryResolver queryResolver = new QueryResolver(BROKER_REPOSITORY, CANDLE_REPOSITORY, RATE_REPOSITORY);
		return builder
				.dataFetcher("brokers", queryResolver::getBrokers)
				.dataFetcher("candles", queryResolver::getCandles)
				.dataFetcher("rates", queryResolver::getRates);
	}

	private static Builder wiringForBroker(Builder builder) {
		BrokerResolver brokerResolver = new BrokerResolver(SYMBOL_REPOSITORY);
		return builder.dataFetcher("symbols", brokerResolver::getSymbols);
	}

	private static Builder wiringForSymbol(Builder builder) {
		SymbolResolver symbolResolver = new SymbolResolver(BROKER_REPOSITORY);
		return builder.dataFetcher("broker", symbolResolver::getBroker);
	}

	private static Builder wiringForCandle(Builder builder) {
		CandleResolver candleResolver = new CandleResolver(BROKER_REPOSITORY);
		return builder.dataFetcher("broker", candleResolver::getBroker);
	}

	private static Builder wiringForRate(Builder builder) {
		RateResolver rateResolver = new RateResolver(BROKER_REPOSITORY);
		return builder
				.dataFetcher("broker", rateResolver::getBroker)
				.dataFetcher("spread", rateResolver::getSpread);
	}
}
