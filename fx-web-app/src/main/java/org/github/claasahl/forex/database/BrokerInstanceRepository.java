package org.github.claasahl.forex.database;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import io.reactivex.Observable;
import io.reactivex.Single;

public class BrokerInstanceRepository {
	private final Map<String, BrokerInstance> brokerInstances;

	public BrokerInstanceRepository() {
		AtomicInteger ids = new AtomicInteger();
		List<BrokerInstance> brokers = new ArrayList<>();
		brokers.add(new BrokerInstance.Builder().setId(ids.getAndIncrement() + "").setName("Custom Spotware")
				.setProviderName("unknown").build());
		brokers.add(new BrokerInstance.Builder().setId(ids.getAndIncrement() + "").setName("Custom Random Data")
				.setProviderName("also unknown").build());
		this.brokerInstances = brokers.stream().collect(Collectors.toMap(BrokerInstance::getId, Function.identity()));
	}

	public Observable<BrokerInstance> getBrokerInstances(BrokerInstanceFilter filter) {
		if (filter.getBrokerId() != null) {
			return Observable.fromIterable(brokerInstances.values())
					.filter(broker -> broker.getId().equals(filter.getBrokerId()));
		}
		return Observable.fromIterable(brokerInstances.values());
	}

	public Single<BrokerInstance> getBrokerInstanceForId(String brokerId) {
		return Single.just(brokerInstances.get(brokerId));
	}
}
