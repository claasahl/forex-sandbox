package org.github.claasahl.forex.graphql;

import org.github.claasahl.forex.database.BrokerInstanceRepository;
import graphql.schema.DataFetchingEnvironment;

class CandleResolver {
	private final BrokerInstanceRepository brokerInstanceRepository;

	protected CandleResolver(BrokerInstanceRepository brokerInstanceRepository) {
		this.brokerInstanceRepository = brokerInstanceRepository;
	}

	protected GqlBroker getBroker(DataFetchingEnvironment environment) {
		GqlCandle candle = environment.getSource();
		return brokerInstanceRepository.getBrokerInstanceForId(candle.getBrokerId()).map(GqlBroker::new).blockingGet();
	}
}
