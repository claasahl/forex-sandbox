package org.github.claasahl.forex.graphql;

import org.github.claasahl.forex.database.BrokerInstance;

class GqlBroker {
	private final BrokerInstance brokerInstance;

	public GqlBroker(BrokerInstance brokerInstance) {
		this.brokerInstance = brokerInstance;
	}

	public BrokerInstance getBrokerInstance() {
		return brokerInstance;
	}

	protected String getId() {
		return brokerInstance.getId();
	}

	protected String getName() {
		return brokerInstance.getName();
	}
}
