package org.github.claasahl.forex.graphql;

import org.github.claasahl.forex.database.BrokerRepository;
import org.github.claasahl.forex.model.*;
import graphql.schema.DataFetchingEnvironment;

class SymbolResolver {
	private final BrokerRepository brokerRepository;
	
	public SymbolResolver(BrokerRepository brokerRepository) {
		this.brokerRepository = brokerRepository;
	}

	public Broker getBroker(DataFetchingEnvironment environment) {
		Symbol symbol = environment.getSource();
		return brokerRepository.getBrokerForId(symbol.getBrokerId());
	}
}
