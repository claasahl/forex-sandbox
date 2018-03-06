package org.github.claasahl.forex.graphql;

import java.time.Duration;
import java.util.Map;
import org.github.claasahl.forex.broker.CandleFilter;

class GqlCandleFilter {
	private final String brokerId;
	private final CandleFilter filter;

	public GqlCandleFilter(String brokerId, CandleFilter filter) {
		this.brokerId = brokerId;
		this.filter = filter;
	}

	public String getBrokerId() {
		return brokerId;
	}

	public CandleFilter getFilter() {
		return filter;
	}

	public static GqlCandleFilter fromMap(Map<String, Object> map) {
		if (map == null)
			return null;
		String brokerId = (String) map.get("brokerId");
		String symbol = (String) map.get("symbol");
		Duration duration = (Duration) map.get("duration");
		CandleFilter filter = new CandleFilter(symbol, duration);
		return new GqlCandleFilter(brokerId, filter);
	}
}
