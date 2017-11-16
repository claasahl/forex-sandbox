package org.github.claasahl.forex;

import java.time.*;

/**
 * The class/interface <code>LimitOrder</code>. It represents ...
 * <p>
 * With a Limit Order, the price you set is the maximum or minimum price at
 * which you are willing to buy or sell. The order will be filled at either the
 * exact price you set, or a more favorable price.
 *
 * @author Claas Ahlrichs
 *
 */
public final class LimitOrder extends BaseOrder {

	// required
	private final double entryPrice;

	// optional
	private final OffsetDateTime expiry;

	public LimitOrder() {
		this.entryPrice = 1.7;
		this.expiry = null;
	}

	public double getEntryPrice() {
		return entryPrice;
	}

	public OffsetDateTime getExpiry() {
		return expiry;
	}
}
