package org.github.claasahl.forex.graphql;

import org.github.claasahl.forex.database.BrokerRepository;
import org.github.claasahl.forex.model.*;
import graphql.schema.DataFetchingEnvironment;

class RateResolver {
	private final BrokerRepository brokerRepository;
	
	public RateResolver(BrokerRepository brokerRepository) {
		this.brokerRepository = brokerRepository;
	}
	
	public Broker getBroker(DataFetchingEnvironment environment) {
		Rate rate = environment.getSource();
		return brokerRepository.getBrokerForId(rate.getBrokerId());
	}
	
	public double getSpread(DataFetchingEnvironment environment) {
		Rate rate = environment.getSource();
		return rate.getSpread();
	}
}
