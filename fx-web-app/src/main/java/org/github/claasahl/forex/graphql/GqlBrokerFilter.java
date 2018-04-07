package org.github.claasahl.forex.graphql;

import java.util.Map;
import org.github.claasahl.forex.database.BrokerInstanceFilter;

class GqlBrokerFilter {
	private final BrokerInstanceFilter filter;

	protected GqlBrokerFilter(BrokerInstanceFilter filter) {
		this.filter = filter;
	}

	protected BrokerInstanceFilter getFilter() {
		return filter;
	}

	protected static GqlBrokerFilter fromMap(Map<String, Object> map) {
		if (map == null)
			return null;
		String brokerId = (String) map.get("brokerId");
		BrokerInstanceFilter filter = new BrokerInstanceFilter(brokerId);
		return new GqlBrokerFilter(filter);
	}
}
