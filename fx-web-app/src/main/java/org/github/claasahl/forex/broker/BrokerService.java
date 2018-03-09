package org.github.claasahl.forex.broker;

import java.util.*;
import org.github.claasahl.forex.broker.spi.*;
import io.reactivex.*;
import io.reactivex.Observable;

public class BrokerService {
	private final ServiceLoader<BrokerProvider> serviceLoader = ServiceLoader.load(BrokerProvider.class);

	public Observable<BrokerProvider> getProviders() {
		return Observable.fromIterable(serviceLoader);
	}
	
	public Single<Broker> getBroker(String providerName, Properties configuration) {
		return getProviders()
				.filter(provider -> providerName.equals(provider.getName()))
				.filter(provider -> provider.isSupported(configuration))
				.firstOrError()
				.map(provider -> provider.instantiate(configuration));
	}
}
