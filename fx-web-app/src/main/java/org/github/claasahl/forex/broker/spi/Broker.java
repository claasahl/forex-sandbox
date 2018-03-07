package org.github.claasahl.forex.broker.spi;

import org.github.claasahl.forex.broker.*;
import io.reactivex.Observable;

public interface Broker {
	Observable<Candle> candles(CandleFilter filter);
	
	Observable<Rate> rates(RateFilter filter);
}
