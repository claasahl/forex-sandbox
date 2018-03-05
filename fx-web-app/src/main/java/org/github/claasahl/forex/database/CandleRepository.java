package org.github.claasahl.forex.database;

import java.time.Duration;
import java.util.*;
import org.github.claasahl.forex.model.*;

public class CandleRepository {
	private final List<Candle> candles;

	public CandleRepository() {
		candles = new ArrayList<>();
		candles.add(new InternalCandle.Builder().setSymbol("EURGBP").setDuration(Duration.ofMinutes(1)).setOpen(2).setHigh(4).setClose(3).setLow(1).build());
		candles.add(new InternalCandle.Builder().setSymbol("EURUSD").setDuration(Duration.ofMinutes(5)).build());
	}

	public List<Candle> getCandles(CandleFilter filter) {
		return candles;
	}
}
