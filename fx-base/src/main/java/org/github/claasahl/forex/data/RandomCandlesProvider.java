package org.github.claasahl.forex.data;

import java.time.*;
import org.github.claasahl.forex.Candle;
import org.github.claasahl.forex.data.spi.CandleSeries;
import io.reactivex.Observable;

public class RandomCandlesProvider implements CandleSeries {
	@Override
	public Observable<Candle> candles(String symbol, Duration duration) {
		// TODO implement
		return Observable.empty();
	}

	@Override
	public Observable<Candle> candles(String symbol, Duration duration, OffsetDateTime from, OffsetDateTime to) {
		// TODO implement
		return Observable.empty();
	}
}