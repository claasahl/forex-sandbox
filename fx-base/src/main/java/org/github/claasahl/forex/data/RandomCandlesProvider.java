package org.github.claasahl.forex.data;

import java.time.*;
import org.github.claasahl.forex.Candle;
import org.github.claasahl.forex.data.spi.CandleSeries;
import org.github.claasahl.forex.generator.*;
import io.reactivex.Observable;

public class RandomCandlesProvider implements CandleSeries {
	@Override
	public Observable<Candle> candles(String symbol, Duration duration) {
		final RandomCandles producer = new RandomCandles(symbol, duration);
		final OffsetDateTime from = OffsetDateTime.now();
		return DateTimeHelper.live(duration, from).map(producer::apply);
	}

	@Override
	public Observable<Candle> candles(String symbol, Duration duration, OffsetDateTime from, OffsetDateTime to) {
		final RandomCandles producer = new RandomCandles(symbol, duration);
		return DateTimeHelper.historic(duration, from, to).map(producer::apply);
	}
}