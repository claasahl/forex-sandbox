package org.github.claasahl.forex;

import java.time.*;

public interface Order {
	
	// required
	public String getInstrument();
	
	// required
	public double getUnits();
	
	// required
	public OrderSide getSide();
	
	// required
	public OrderType getType();
	
	// optional
	// The stop loss price.
	public double getStopLoss();
	
	// optional
	// The trailing stop distance in pips. 
	public double getTrailingStop();
	
	// optional
	// The take profit price.
	public double getTakeProfit();

	// required for limit, stop
	// The price where the order is set to trigger at.
	public double getPrice();
	
	// required limit, stop
	// The order expiration time.
	public OffsetDateTime getExpiry();
	
	// optional for market, stop
	// The minimum / maximum execution price.
	public double getBoundary();
}
