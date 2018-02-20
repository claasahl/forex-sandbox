package org.github.claasahl.forex.model;

import java.time.OffsetDateTime;

/**
 * The interface {@link Rate}. It is intended to represent an exchange rate. The
 * rate is defined by its primary properties (i.e. symbol, time, bid price and
 * ask price).
 *
 * @author Claas Ahlrichs
 *
 */
public interface Rate extends Comparable<Rate> {
	int getSymbolId();

	/**
	 * Returns the date and time of this rate. The instantaneous point on the
	 * time-line at which this rate was observed.
	 * 
	 * @return the date and time
	 */
	OffsetDateTime getDateTime();

	/**
	 * Returns the bid price of this rate. It represents the price at which the
	 * market is prepared to buy a product (i.e. the price at which a trader can
	 * sell a product).
	 *
	 * @return the bid price
	 */
	double getBid();

	/**
	 * Returns the ask price of this rate. It represents the price at which the
	 * market is prepared to sell a product (i.e. the price at which a trader can
	 * buy a product). The ask price is also known as the offer.
	 *
	 * @return the ask price
	 */
	double getAsk();

	/**
	 * Returns the spread this rate. It represents the difference between the bid
	 * price and the ask price.
	 *
	 * @return the spread
	 */
	default double getSpread() {
		return getBid() - getAsk();
	}

	@Override
	default int compareTo(Rate o) {
		return getDateTime().compareTo(o.getDateTime());
	}
}
