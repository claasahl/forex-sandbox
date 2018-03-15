package org.github.claasahl.forex.broker;

import java.time.Duration;

public class CandleFilter {
	private final String symbol;
	private final Duration duration;

	public CandleFilter(String symbol, Duration duration) {
		this.symbol = symbol;
		this.duration = duration;
	}

	/**
	 * Returns the symbol of this candle. It represents the name of the product or
	 * financial instrument for which the prices were observed.
	 * 
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * Returns the duration of this candle. The amount of time that elapsed between
	 * the opening and closing time of this candle. Not to be confused with the
	 * instantaneous point on the time-line at which closing price was observed.
	 * This candle's closing price may have been observed before this duration has
	 * elapsed.
	 * <p/>
	 * The opening time and duration form an artificial boundary and represent the
	 * lifetime of this candle. Price changes during this time window are summarized
	 * in this candle.
	 * 
	 * @return the duration
	 */
	public Duration getDuration() {
		return duration;
	}
}
