package org.github.claasahl.forex.graphql;

import java.util.Map;
import org.github.claasahl.forex.database.BrokerRepository;
import org.github.claasahl.forex.model.*;
import graphql.schema.DataFetchingEnvironment;

public class CandleResolver {
	private final BrokerRepository brokerRepository;

	public CandleResolver(BrokerRepository brokerRepository) {
		this.brokerRepository = brokerRepository;
	}

	public Broker getBroker(DataFetchingEnvironment environment) {
		Map<String, Object> filterMap = environment.getArgument("filter");
		CandleFilter filter = CandleFilter.fromMap(filterMap);
		return brokerRepository.getBrokerForId(filter.getBrokerId());
	}
}
