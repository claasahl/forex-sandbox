package org.github.claasahl.forex.graphql;

import java.util.*;
import org.github.claasahl.forex.database.BrokerRepository;
import org.github.claasahl.forex.model.Broker;
import graphql.schema.DataFetchingEnvironment;

class QueryResolver {
	private final BrokerRepository brokerRepository;
	
	public QueryResolver(BrokerRepository brokerRepository) {
		this.brokerRepository = brokerRepository;
	}
	
	public List<Broker> getBrokers(DataFetchingEnvironment environment) {
		return brokerRepository.getBrokers();
	}
}
