package org.github.claasahl.forex.broker;

import java.time.OffsetDateTime;
import org.github.claasahl.forex.broker.spi.Broker;
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

}
