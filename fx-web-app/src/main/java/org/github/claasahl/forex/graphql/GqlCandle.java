package org.github.claasahl.forex.graphql;

import java.time.*;
import org.github.claasahl.forex.broker.*;

/**
 * The class {@link GqlCandle} is exposed through GraphQL. It wraps internal
 * objects and (re-)shapes them as described in the GraphQL schema. This class
 * also adds an extra layer of abstraction which makes it more difficult to leak
 * (i.e. unintentionally expose) details about the wrapped internal objects.
 * 
 * @author Claas Ahlrichs
 *
 */
class GqlCandle {
	private final GqlCandleFilter filter;
	private final Candle candle;

	protected GqlCandle(GqlCandleFilter filter, Candle candle) {
		this.filter = filter;
		this.candle = candle;
	}

	/**
	 * Returns this candle's broker (ID).
	 * 
	 * @return this candle's broker (ID)
	 */
	protected String getBrokerId() {
		return filter.getBrokerId();
	}

	/**
	 * Returns this candle's symbol.
	 * 
	 * @see CandleFilter#getSymbol()
	 * @return this candle's symbol
	 */
	protected String getSymbol() {
		return filter.getFilter().getSymbol();
	}

	/**
	 * Returns this candle's duration.
	 * 
	 * @see CandleFilter#getDuration()
	 * @return this candle's duration
	 */
	protected Duration getDuration() {
		return filter.getFilter().getDuration();
	}

	/**
	 * Returns this candle's date and time.
	 * 
	 * @see Candle#getDateTime()
	 * @return this candle's date and time
	 */
	protected OffsetDateTime getDateTime() {
		return candle.getDateTime();
	}

	/**
	 * Returns this candle's opening price
	 * 
	 * @see Candle#getOpen()
	 * @return this candle's opening price
	 */
	protected double getOpen() {
		return candle.getOpen();
	}

	/**
	 * Returns this candle's highest price.
	 * 
	 * @see Candle#getHigh()
	 * @return this candle's highest price
	 */
	protected double getHigh() {
		return candle.getHigh();
	}

	/**
	 * Returns this candle's lowest price.
	 * 
	 * @see Candle#getLow()
	 * @return this candle's lowest price
	 */
	protected double getLow() {
		return candle.getLow();
	}

	/**
	 * Returns this candle's closing price.
	 * 
	 * @see Candle#getClose()
	 * @return this candle's closing price
	 */
	protected double getClose() {
		return candle.getClose();
	}

	/**
	 * Returns this candle's volume.
	 * 
	 * @see Candle#getVolume()
	 * @return this candle's volume
	 */
	protected long getVolume() {
		return candle.getVolume();
	}
}
