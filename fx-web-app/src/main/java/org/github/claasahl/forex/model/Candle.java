package org.github.claasahl.forex.model;

import java.time.*;

/**
 * The interface {@link Candle}. It is intended to represent a candlestick. The
 * candlestick is defined by its primary properties (i.e. symbol, opening time,
 * duration, opening price, high price, low price and closing price).
 *
 * @author Claas Ahlrichs
 *
 */
public interface Candle extends Comparable<Candle> {
	int getBrokerId();
	
	/**
	 * Returns the symbol of this candle. It represents the name of the product or
	 * financial instrument for which the prices were observed.
	 * 
	 * @return the symbol
	 */
	String getSymbol();

	/**
	 * Returns the date and time of this candle. The instantaneous point on the
	 * time-line at which this candle was opened. Not to be confused with the
	 * instantaneous point at which opening price was observed. This candle's
	 * opening price may have been observed before this point.
	 * <p/>
	 * The opening time and duration form an artificial boundary and represent the
	 * lifetime of this candle. Price changes during this time window are summarized
	 * in this candle.
	 * 
	 * @return the date and time
	 */
	OffsetDateTime getDateTime();

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
	Duration getDuration();

	/**
	 * Returns the opening price of this candle. It represents the price that was
	 * valid at the opening time. This value is free to move within the upper and
	 * lower boundaries (i.e. high price and low price, respectively).
	 * </p>
	 * It is important to note that the opening price may not always be observed at
	 * the opening time of this candle. The opening price either represents the last
	 * price of a previous candle or the first price change within the lifetime of
	 * this candle (i.e. between its opening and closing time). As such, the opening
	 * price may be observed before, exactly at the opening time or after the
	 * opening time. In any case, it is considered to represent the price that was
	 * valid at the opening time (even though it may not have been observed exactly
	 * at the opening time).
	 *
	 * @return the opening price
	 */
	double getOpen();

	/**
	 * Returns the high price of this candle. It represents the highest price that
	 * was observed during the lifetime of this candle (i.e. between its opening and
	 * closing time). The value also represents the upper boundary of this candle.
	 * Naturally, opening price, low price and closing price must all be less or
	 * equal.
	 *
	 * @return the high price
	 */
	double getHigh();

	/**
	 * Returns the low price of this candle. It represents the lowest price that was
	 * observed during the lifetime of this candle (i.e. between its opening and
	 * closing time). The value also represents the lower boundary of this candle.
	 * Naturally, opening price, high price and closing price must all be greater or
	 * equal.
	 *
	 * @return the low price
	 */
	double getLow();

	/**
	 * Returns the closing price of this candle. It represents the price that was
	 * valid at the closing time. This value is free to move within the upper and
	 * lower boundaries (i.e. high price and low price, respectively).
	 * </p>
	 * It is important to note that the closing price may not always be observed at
	 * the closing time of this candle. The closing price represents the last price
	 * change within the lifetime of this candle (i.e. between its opening and
	 * closing time). As such, the closing price may be observed before or exactly
	 * at the closing time, but never after the closing time. In any case, it is
	 * considered to represent the price that was valid at the closing time (even
	 * though it may not have been observed exactly at the closing time).
	 * 
	 * @return the closing price
	 */
	double getClose();

	/**
	 * Returns the volume of this candle.
	 * 
	 * @return the volume
	 */
	long getVolume();
	
	@Override
	default int compareTo(Candle o) {
		return getDateTime().compareTo(o.getDateTime());
	}
}
