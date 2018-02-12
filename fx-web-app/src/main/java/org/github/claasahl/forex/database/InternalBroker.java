package org.github.claasahl.forex.database;

import org.github.claasahl.forex.model.Broker;

class InternalBroker implements Broker {
	private final int id;
	private final String name;

	public InternalBroker(int id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}
}
