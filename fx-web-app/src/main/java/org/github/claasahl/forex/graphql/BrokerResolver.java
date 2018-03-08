package org.github.claasahl.forex.graphql;

import java.util.Collection;
import org.github.claasahl.forex.database.SymbolRepository;
import graphql.schema.DataFetchingEnvironment;

class BrokerResolver {
	private final SymbolRepository symbolRepository;

	public BrokerResolver(SymbolRepository symbolRepository) {
		this.symbolRepository = symbolRepository;
	}

	Collection<GqlSymbol> getSymbols(DataFetchingEnvironment environment) {
		GqlBroker broker = environment.getSource();
		return symbolRepository.getSymbolsForBrokerId(broker.getId()).map(GqlSymbol::new).toList().blockingGet();
	}
}
