package org.github.claasahl.forex;

public enum OrderType {

	/**
	 * A market order is an order to buy or sell at the best available price.
	 */
	MARKET,

	/**
	 * A limit entry is an order placed to either buy below the market or sell
	 * above the market at a certain price.
	 */
	LIMIT,

	/**
	 * A stop entry order is an order placed to buy above the market or sell
	 * below the market at a certain price.
	 */
	STOP;

}
