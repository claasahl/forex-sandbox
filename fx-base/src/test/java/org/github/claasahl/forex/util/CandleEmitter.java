package org.github.claasahl.forex.util;

import java.time.*;
import java.time.temporal.ChronoUnit;
import org.github.claasahl.forex.*;
import io.reactivex.ObservableEmitter;

public class CandleEmitter extends DecoratedEmitter<Candle> {
	private final Candle.Builder builder;
	private OffsetDateTime dateTime;

	public CandleEmitter(ObservableEmitter<Candle> emitter, String symbol, Duration duration, OffsetDateTime dateTime) {
		super(emitter);
		this.dateTime = dateTime;
		this.builder = new Candle.Builder().setSymbol(symbol).setDuration(duration);
	}

	public void onNext(long millis, double open, double high, double low, double close) {
		dateTime = dateTime.plus(millis, ChronoUnit.MILLIS);
		Candle candle = builder.setDateTime(dateTime)
				.setOpen(open)
				.setHigh(high)
				.setLow(low)
				.setClose(close)
				.build();
		onNext(candle);
	}
}
