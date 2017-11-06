package org.github.claasahl.forex.filter;

import java.util.function.Predicate;
import org.github.claasahl.forex.Candle;

public class ConsistentLowPrice implements Predicate<Candle> {
	@Override
	public boolean test(Candle t) {
		double low = t.getLow();
		return low <= t.getOpen() && low <= t.getHigh() && low <= t.getClose();
	}
}
