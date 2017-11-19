package org.github.claasahl.forex;

import java.time.*;

/**
 * The class/interface <code>StopLimitOrder</code>. It represents ...
 * <p>
 * Once the market price reaches the Stop Price the order will be triggered like
 * a Limit Order using the Stop Price plus Limit Range for Buy orders or the
 * Stop Price minus Limit Range for Sell orders.
 *
 * @author Claas Ahlrichs
 *
 */
public final class StopLimitOrder extends BaseOrder {

	// required
	private final double entryPrice;
	private final long limitRange;

	// optional
	private final OffsetDateTime expiry;

	public StopLimitOrder() {
		this.entryPrice = 1.7;
		this.limitRange = 0;
		this.expiry = null;
	}

	public double getEntryPrice() {
		return entryPrice;
	}

	public long getLimitRange() {
		return limitRange;
	}

	public OffsetDateTime getExpiry() {
		return expiry;
	}
}
