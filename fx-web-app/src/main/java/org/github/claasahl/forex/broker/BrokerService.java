package org.github.claasahl.forex.broker;

import java.util.ServiceLoader;
import org.github.claasahl.forex.broker.spi.Broker;
import io.reactivex.Observable;

public class BrokerService {
	private final ServiceLoader<Broker> serviceLoader = ServiceLoader.load(Broker.class);
	
	public Observable<Broker> getProviders() {
		return Observable.fromIterable(serviceLoader);
	}
}
