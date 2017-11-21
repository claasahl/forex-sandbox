package org.github.claasahl.forex.data;

import java.util.ServiceLoader;
import org.github.claasahl.forex.data.spi.CandleSeries;
import io.reactivex.Observable;

public class CandlesService {
	private final ServiceLoader<CandleSeries> loader = ServiceLoader.load(CandleSeries.class);
	
	public Observable<String> getProviders() {
		return Observable.fromIterable(loader).map(provider -> provider.getClass().getSimpleName());
	}
}
