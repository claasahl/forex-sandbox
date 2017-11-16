package org.github.claasahl.forex;

import java.time.*;

/**
 * The class/interface <code>StopOrder</code>. It represents ...
 * <p>
 * With a Stop Order, once the market price reaches the entry price you set, a
 * Market Order will be automatically created and it will be filled at the best
 * available price.
 * 
 * @author Claas Ahlrichs
 *
 */
public final class StopOrder extends BaseOrder{

	// required
	private final double entryPrice;

	// optional
	private final OffsetDateTime expiry;

	public StopOrder() {
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
