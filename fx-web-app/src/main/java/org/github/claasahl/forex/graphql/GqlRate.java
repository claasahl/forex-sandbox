package org.github.claasahl.forex.graphql;

import java.time.OffsetDateTime;
import org.github.claasahl.forex.broker.*;

/**
 * The class {@link GqlRate} is exposed through GraphQL. It wraps internal
 * objects and (re-)shapes them as described in the GraphQL schema. This class
 * also adds an extra layer of abstraction which makes it more difficult to leak
 * (i.e. unintentionally expose) details about the wrapped internal objects.
 * 
 * @author Claas Ahlrichs
 *
 */
class GqlRate {
	private final GqlRateFilter filter;
	private final Rate rate;

	protected GqlRate(GqlRateFilter filter, Rate rate) {
		this.filter = filter;
		this.rate = rate;
	}

	/**
	 * Returns this rate's broker (ID).
	 * 
	 * @return this rate's broker (ID)
	 */
	protected String getBrokerId() {
		return filter.getBrokerId();
	}

	/**
	 * Returns this rate's symbol.
	 * 
	 * @see RateFilter#getSymbol()
	 * @return this rate's symbol
	 */
	protected String getSymbol() {
		return filter.getFilter().getSymbol();
	}

	/**
	 * Returns this rate's date and time.
	 * 
	 * @see Rate#getDateTime()
	 * @return this rate's date and time
	 */
	protected OffsetDateTime getDateTime() {
		return rate.getDateTime();
	}

	/**
	 * Returns this rate's bid price.
	 * 
	 * @see Rate#getBid()
	 * @return this rate's bid price
	 */
	protected double getBid() {
		return rate.getBid();
	}

	/**
	 * Returns this rate's ask price.
	 * 
	 * @see Rate#getAsk()
	 * @return this rate's ask price
	 */
	protected double getAsk() {
		return rate.getAsk();
	}

	/**
	 * Returns this rate's spread.
	 * 
	 * @see Rate#getSpread()
	 * @return this rate's spread
	 */
	protected double getSpread() {
		return rate.getSpread();
	}
}
