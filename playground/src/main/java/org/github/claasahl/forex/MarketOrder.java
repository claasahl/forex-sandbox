package org.github.claasahl.forex;

/**
 * The class/interface <code>MarketOrder</code>. It represents ...
 *
 * @author Claas Ahlrichs
 *
 */
public final class MarketOrder extends BaseOrder {

	// optional
	private final double marketRange;

	public MarketOrder() {
		this.marketRange = Double.NaN;
	}

	/**
	 * Select the maximum deviation from the price you see in the platform at
	 * which your order can be filled. A deviation of 0 will attempt to fill
	 * your order as a Limit Order.
	 * 
	 * @return
	 */
	public double getMarketRange() {
		return marketRange;
	}

}
