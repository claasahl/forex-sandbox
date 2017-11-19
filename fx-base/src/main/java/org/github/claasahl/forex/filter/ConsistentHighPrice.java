package org.github.claasahl.forex.filter;

import java.util.function.Predicate;
import org.github.claasahl.forex.Candle;

public class ConsistentHighPrice implements Predicate<Candle> {
	@Override
	public boolean test(Candle t) {
		double high = t.getHigh();
		return high >= t.getOpen() && high >= t.getHigh() && high >= t.getClose();
	}
}
