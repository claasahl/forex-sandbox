package org.github.claasahl.forex.util;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import org.github.claasahl.forex.*;
import io.reactivex.ObservableEmitter;

public class RateEmitter extends DecoratedEmitter<Rate> {
	private final Rate.Builder builder;
	private OffsetDateTime dateTime;

	public RateEmitter(ObservableEmitter<Rate> emitter, String symbol, OffsetDateTime dateTime) {
		super(emitter);
		this.dateTime = dateTime;
		this.builder = new Rate.Builder().setSymbol(symbol);
	}

	public void onNext(long millis, double bid, double ask) {
		dateTime = dateTime.plus(millis, ChronoUnit.MILLIS);
		Rate rate = builder.setDateTime(dateTime)
				.setBid(bid)
				.setAsk(ask)
				.build();
		onNext(rate);
	}
}
