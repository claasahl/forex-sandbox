package org.github.claasahl.forex.graphql;

import java.util.Map;
import org.github.claasahl.forex.database.BrokerInstanceFilter;

class GqlBrokerFilter {
	private final BrokerInstanceFilter filter;

	public GqlBrokerFilter(BrokerInstanceFilter filter) {
		this.filter = filter;
	}

	public BrokerInstanceFilter getFilter() {
		return filter;
	}

	public static GqlBrokerFilter fromMap(Map<String, Object> map) {
		if (map == null)
			return null;
		String brokerId = (String) map.get("brokerId");
		BrokerInstanceFilter filter = new BrokerInstanceFilter(brokerId);
		return new GqlBrokerFilter(filter);
	}
}
