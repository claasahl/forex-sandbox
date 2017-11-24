package org.github.claasahl.forex.data;

import java.time.OffsetDateTime;
import org.github.claasahl.forex.Rate;
import org.github.claasahl.forex.data.spi.RateSeries;
import io.reactivex.Observable;

public class RandomRatesProvider implements RateSeries {
	@Override
	public Observable<Rate> rates(String symbol) {
		// TODO implement
		// TODO should only emit a rate every "few" seconds or so
		return Observable.empty();
	}

	@Override
	public Observable<Rate> rates(String symbol, OffsetDateTime from, OffsetDateTime to) {
		// TODO implement
		return Observable.empty();
	}
}