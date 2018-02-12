package org.github.claasahl.forex.graphql;

import java.util.*;
import org.github.claasahl.forex.database.SymbolRepository;
import org.github.claasahl.forex.model.*;
import graphql.schema.DataFetchingEnvironment;

class BrokerResolver {
	private final SymbolRepository symbolRepository;

	public BrokerResolver(SymbolRepository symbolRepository) {
		this.symbolRepository = symbolRepository;
	}

	List<Symbol> getSymbols(DataFetchingEnvironment environment) {
		Broker broker = environment.getSource();
		return symbolRepository.getSymbolsForBroker(broker);
	}
}
