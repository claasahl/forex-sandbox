package org.github.claasahl.forex.database;

import java.util.*;
import org.github.claasahl.forex.model.Candle;

public class CandleRepository {
	private final List<Candle> candles;

	public CandleRepository() {
		candles = new ArrayList<>();
	}

	public List<Candle> getCandles() {
		return candles;
	}
}