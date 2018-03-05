package org.github.claasahl.forex.graphql;

import java.util.Map;
import org.github.claasahl.forex.database.BrokerRepository;
import org.github.claasahl.forex.model.*;
import graphql.schema.DataFetchingEnvironment;

class RateResolver {
	private final BrokerRepository brokerRepository;
	
	public RateResolver(BrokerRepository brokerRepository) {
		this.brokerRepository = brokerRepository;
	}
	
	public Broker getBroker(DataFetchingEnvironment environment) {
		Map<String, Object> filterMap = environment.getArgument("filter");
		RateFilter filter = RateFilter.fromMap(filterMap);
		return brokerRepository.getBrokerForId(filter.getBrokerId());
	}
	
	public double getSpread(DataFetchingEnvironment environment) {
		Rate rate = environment.getSource();
		return rate.getSpread();
	}
}
