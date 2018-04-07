package org.github.claasahl.forex.broker;

public class RateFilter {
	private final String symbol;

	public RateFilter(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * Returns the symbol of this rate. It represents the name of the product or
	 * financial instrument for which the prices were observed.
	 * 
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}
}
