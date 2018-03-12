package org.github.claasahl.forex.graphql;

import java.time.*;
import org.github.claasahl.forex.broker.Candle;

class GqlCandle {
	private final GqlCandleFilter filter;
	private final Candle candle;

	protected GqlCandle(GqlCandleFilter filter, Candle candle) {
		this.filter = filter;
		this.candle = candle;
	}

	protected GqlCandleFilter getFilter() {
		return filter;
	}

	protected Candle getCandle() {
		return candle;
	}

	protected String getBrokerId() {
		return filter.getBrokerId();
	}
	
	protected String getSymbol() {
		return filter.getFilter().getSymbol();
	}
	
	protected Duration getDuration() {
		return filter.getFilter().getDuration();
	}

	protected OffsetDateTime getDateTime() {
		return candle.getDateTime();
	}

	protected double getOpen() {
		return candle.getOpen();
	}

	protected double getHigh() {
		return candle.getHigh();
	}

	protected double getLow() {
		return candle.getLow();
	}

	protected double getClose() {
		return candle.getClose();
	}

	protected long getVolume() {
		return candle.getVolume();
	}
}
