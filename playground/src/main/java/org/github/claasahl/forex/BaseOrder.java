package org.github.claasahl.forex;

/**
 * The class/interface <code>BaseOrder</code>. It represents ...
 *
 * @author Claas Ahlrichs
 *
 */
class BaseOrder {

	// required
	private final String symbol;
	private final OrderSide side;
	private final long volume;
	
	// optional
	private final double takeProfit;
	private final double stopLoss;
	private final boolean trailingStop;
	private final String comment;

	public BaseOrder() {
		this.symbol = "EURUSD";
		this.side = OrderSide.BUY;
		this.volume = 100000;
		this.takeProfit = Double.NaN;
		this.stopLoss = Double.NaN;
		this.trailingStop = false;
		this.comment = null;
	}

	public final String getSymbol() {
		return symbol;
	}

	public final OrderSide getSide() {
		return side;
	}

	public final long getVolume() {
		return volume;
	}

	/**
	 * Place a Limit Order to secure potential profits. Your Position will be
	 * automatically closed at a profit if the price reaches the Take Profit
	 * rate. Take Profit orders must be placed below the entry rate for Sell
	 * Orders, or above the entry rate for Buy Orders.
	 * <p>
	 * The amount of pips away from the entry price of your position that the
	 * Stop Loss or Take Profit will be placed at, after your order is filled.
	 * 
	 * @return
	 */
	public final double getTakeProfit() {
		return takeProfit;
	}

	/**
	 * Place a Stop Order to limit potential losses. Your Position will be
	 * automatically closed if the price reaches the Stop Loss rate. Stop Loss
	 * orders must be placed above the entry rate for Sell positions, or below
	 * the entry rate for Buy positions.
	 * <p>
	 * The amount of pips away from the entry price of your position that the
	 * Stop Loss or Take Profit will be placed at, after your order is filled.
	 * 
	 * @return
	 */
	public final double getStopLoss() {
		return stopLoss;
	}

	/**
	 * Select to convert your stop loss to a trailing stop loss. The trailing
	 * stop loss will remain fixed a set number of pips from the current spot
	 * price. Trailing stops are not modified when the spot price moves against
	 * the direction of the position. Trailing stop loss is changed every pip.
	 * 
	 * @return
	 */
	public final boolean isTrailingStop() {
		return trailingStop;
	}

	public String getComment() {
		return comment;
	}

}