package org.github.claasahl.forex.database;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import org.github.claasahl.forex.model.Broker;

public class BrokerRepository {
	private final List<Broker> brokers;

	public BrokerRepository() {
		AtomicInteger ids = new AtomicInteger();
		brokers = new ArrayList<>();
		brokers.add(new InternalBroker(ids.getAndIncrement(), "Spotware"));
	}

	public List<Broker> getBrokers() {
		return brokers;
	}
}
