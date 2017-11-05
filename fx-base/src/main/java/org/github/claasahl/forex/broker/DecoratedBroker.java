package org.github.claasahl.forex.broker;

import org.github.claasahl.forex.Broker;

public abstract class DecoratedBroker implements Broker {
	private final Broker broker;

	public DecoratedBroker(Broker broker) {
		this.broker = broker;
	}
	
	protected Broker getBroker() {
		return broker;
	}
}
