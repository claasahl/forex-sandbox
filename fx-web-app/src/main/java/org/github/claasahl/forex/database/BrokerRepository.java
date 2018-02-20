package org.github.claasahl.forex.database;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.github.claasahl.forex.model.Broker;

public class BrokerRepository {
	private final Map<Integer, Broker> brokers;

	public BrokerRepository() {
		AtomicInteger ids = new AtomicInteger();
		List<Broker> brokers = new ArrayList<>();
		brokers.add(new InternalBroker(ids.getAndIncrement(), "Spotware"));
		brokers.add(new InternalBroker(ids.getAndIncrement(), "Random Data"));
		this.brokers = brokers.stream().collect(Collectors.toMap(Broker::getId, Function.identity()));
	}

	public Collection<Broker> getBrokers() {
		return brokers.values();
	}

	public Broker getBrokerForId(int brokerId) {
		return brokers.get(brokerId);
	}
}
