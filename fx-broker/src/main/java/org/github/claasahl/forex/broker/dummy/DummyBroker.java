package org.github.claasahl.forex.broker.dummy;

import java.time.OffsetDateTime;
import org.github.claasahl.forex.broker.*;
import io.reactivex.Observable;

public class DummyBroker implements Broker {

	@Override
	public Observable<Candle> candles(CandleFilter filter) {
		Candle candle = new Candle.Builder()
				.setDateTime(OffsetDateTime.now())
				.setOpen(2)
				.setHigh(4)
				.setLow(1)
				.setClose(3)
				.build();
		return Observable.just(candle);
	}

	@Override
	public Observable<Rate> rates(RateFilter filter) {
		Rate rate = new Rate.Builder()
				.setDateTime(OffsetDateTime.now())
				.setBid(0.5)
				.setAsk(0.6)
				.build();
		return Observable.just(rate);
	}

}
