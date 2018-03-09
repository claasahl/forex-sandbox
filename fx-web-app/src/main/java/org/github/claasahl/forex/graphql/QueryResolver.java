package org.github.claasahl.forex.graphql;

import java.util.*;
import org.github.claasahl.forex.broker.DummyBroker;
import org.github.claasahl.forex.database.BrokerInstanceRepository;
import graphql.schema.DataFetchingEnvironment;

class QueryResolver {
	private final DummyBroker dummyBroker = new DummyBroker();
	private final BrokerInstanceRepository brokerInstanceRepository;

	protected QueryResolver(BrokerInstanceRepository brokerInstanceRepository) {
		this.brokerInstanceRepository = brokerInstanceRepository;
	}

	protected Collection<GqlBroker> getBrokers(DataFetchingEnvironment environment) {
		Map<String, Object> filterMap = environment.getArgument("filter");
		GqlBrokerFilter filter = GqlBrokerFilter.fromMap(filterMap);
		return brokerInstanceRepository.getBrokerInstances(filter.getFilter()).map(GqlBroker::new).toList()
				.blockingGet();
	}

	protected GqlBroker getBroker(DataFetchingEnvironment environment) {
		String id = environment.getArgument("id");
		return brokerInstanceRepository.getBrokerInstanceForId(id).map(GqlBroker::new).blockingGet();
	}

	protected Collection<GqlCandle> getCandles(DataFetchingEnvironment environment) {
		Map<String, Object> filterMap = environment.getArgument("filter");
		GqlCandleFilter filter = GqlCandleFilter.fromMap(filterMap);
		return dummyBroker.candles(filter.getFilter()).map(candle -> new GqlCandle(filter, candle)).toList()
				.blockingGet();
	}

	protected Collection<GqlRate> getRates(DataFetchingEnvironment environment) {
		Map<String, Object> filterMap = environment.getArgument("filter");
		GqlRateFilter filter = GqlRateFilter.fromMap(filterMap);
		return dummyBroker.rates(filter.getFilter()).map(rate -> new GqlRate(filter, rate)).toList().blockingGet();
	}
}
