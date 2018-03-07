package org.github.claasahl.forex.graphql;

import java.util.*;
import org.github.claasahl.forex.broker.DummyBroker;
import org.github.claasahl.forex.database.*;
import org.github.claasahl.forex.model.*;
import graphql.schema.DataFetchingEnvironment;

class QueryResolver {
	private final DummyBroker dummyBroker = new DummyBroker();
	private final BrokerRepository brokerRepository;

	public QueryResolver(BrokerRepository brokerRepository) {
		this.brokerRepository = brokerRepository;
	}

	public Collection<Broker> getBrokers(DataFetchingEnvironment environment) {
		Map<String, Object> filterMap = environment.getArgument("filter");
		BrokerFilter filter = BrokerFilter.fromMap(filterMap);
		return brokerRepository.getBrokers(filter);
	}

	public Broker getBroker(DataFetchingEnvironment environment) {
		String id = environment.getArgument("id");
		int brokerId = Integer.valueOf(id);
		return brokerRepository.getBrokerForId(brokerId);
	}

	public Collection<GqlCandle> getCandles(DataFetchingEnvironment environment) {
		Map<String, Object> filterMap = environment.getArgument("filter");
		GqlCandleFilter filter = GqlCandleFilter.fromMap(filterMap);
		return dummyBroker.candles(filter.getFilter()).map(candle -> new GqlCandle(filter, candle)).toList()
				.blockingGet();
	}

	public Collection<GqlRate> getRates(DataFetchingEnvironment environment) {
		Map<String, Object> filterMap = environment.getArgument("filter");
		GqlRateFilter filter = GqlRateFilter.fromMap(filterMap);
		return dummyBroker.rates(filter.getFilter()).map(rate -> new GqlRate(filter, rate)).toList().blockingGet();
	}
}
