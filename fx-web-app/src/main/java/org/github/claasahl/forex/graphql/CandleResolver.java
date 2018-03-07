package org.github.claasahl.forex.graphql;

import org.github.claasahl.forex.database.BrokerRepository;
import org.github.claasahl.forex.model.Broker;
import graphql.schema.DataFetchingEnvironment;

public class CandleResolver {
	private final BrokerRepository brokerRepository;

	public CandleResolver(BrokerRepository brokerRepository) {
		this.brokerRepository = brokerRepository;
	}

	public Broker getBroker(DataFetchingEnvironment environment) {
		return brokerRepository.getBrokerForId(0);
	}
}
