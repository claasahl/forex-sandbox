package org.github.claasahl.forex.graphql;

import java.util.*;
import org.github.claasahl.forex.database.*;
import org.github.claasahl.forex.model.*;
import graphql.schema.DataFetchingEnvironment;

class QueryResolver {
	private final BrokerRepository brokerRepository;
	private final CandleRepository candleRepository;
	private final RateRepository rateRepository;

	public QueryResolver(BrokerRepository brokerRepository, CandleRepository candleRepository,
			RateRepository rateRepository) {
		this.brokerRepository = brokerRepository;
		this.candleRepository = candleRepository;
		this.rateRepository = rateRepository;
	}

	public Collection<Broker> getBrokers(DataFetchingEnvironment environment) {
		Map<String, Object> filterMap = environment.getArgument("filter");
		BrokerFilter filter = BrokerFilter.fromMap(filterMap);
		return brokerRepository.getBrokers(filter);
	}

	public Collection<Candle> getCandles(DataFetchingEnvironment environment) {
		// TODO implement CandleFilter
		return candleRepository.getCandles();
	}

	public Collection<Rate> getRates(DataFetchingEnvironment environment) {
		// TODO implement RateFilter
		return rateRepository.getRates();
	}
}
