package org.github.claasahl.forex.graphql;

import org.github.claasahl.forex.database.BrokerInstanceRepository;
import graphql.schema.DataFetchingEnvironment;

class RateResolver {
	private final BrokerInstanceRepository brokerInstanceRepository;

	protected RateResolver(BrokerInstanceRepository brokerInstanceRepository) {
		this.brokerInstanceRepository = brokerInstanceRepository;
	}

	protected GqlBroker getBroker(DataFetchingEnvironment environment) {
		GqlRate rate = environment.getSource();
		return brokerInstanceRepository.getBrokerInstanceForId(rate.getBrokerId()).map(GqlBroker::new).blockingGet();
	}
}
