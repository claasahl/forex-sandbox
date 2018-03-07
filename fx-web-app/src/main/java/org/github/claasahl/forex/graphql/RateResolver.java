package org.github.claasahl.forex.graphql;

import org.github.claasahl.forex.database.BrokerRepository;
import org.github.claasahl.forex.model.Broker;
import graphql.schema.DataFetchingEnvironment;

class RateResolver {
	private final BrokerRepository brokerRepository;
	
	public RateResolver(BrokerRepository brokerRepository) {
		this.brokerRepository = brokerRepository;
	}
	
	public Broker getBroker(DataFetchingEnvironment environment) {
		return brokerRepository.getBrokerForId(0);
	}
}
