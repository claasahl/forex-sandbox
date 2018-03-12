package org.github.claasahl.forex.graphql;

import java.util.Map;
import org.github.claasahl.forex.broker.RateFilter;

class GqlRateFilter {
	private final String brokerId;
	private final RateFilter filter;

	protected GqlRateFilter(String brokerId, RateFilter filter) {
		this.brokerId = brokerId;
		this.filter = filter;
	}

	protected String getBrokerId() {
		return brokerId;
	}

	protected RateFilter getFilter() {
		return filter;
	}

	protected static GqlRateFilter fromMap(Map<String, Object> map) {
		if (map == null)
			return null;
		String brokerId = (String) map.get("brokerId");
		String symbol = (String) map.get("symbol");
		RateFilter filter = new RateFilter(symbol);
		return new GqlRateFilter(brokerId, filter);
	}
}
