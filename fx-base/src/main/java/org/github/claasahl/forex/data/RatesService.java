package org.github.claasahl.forex.data;

import java.util.ServiceLoader;
import org.github.claasahl.forex.data.spi.RateSeries;
import io.reactivex.Observable;

public class RatesService {
	private final ServiceLoader<RateSeries> loader = ServiceLoader.load(RateSeries.class);

	public Observable<String> getProviders() {
		return Observable.fromIterable(loader).map(provider -> provider.getClass().getSimpleName());
	}
}
