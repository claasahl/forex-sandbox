package org.github.claasahl.forex.graphql;

import java.time.OffsetDateTime;
import org.github.claasahl.forex.broker.Rate;

class GqlRate {
	private final GqlRateFilter filter;
	private final Rate rate;

	public GqlRate(GqlRateFilter filter, Rate rate) {
		this.filter = filter;
		this.rate = rate;
	}

	public GqlRateFilter getFilter() {
		return filter;
	}

	public Rate getRate() {
		return rate;
	}

	protected String getBrokerId() {
		return filter.getBrokerId();
	}

	protected String getSymbol() {
		return filter.getFilter().getSymbol();
	}

	protected OffsetDateTime getDateTime() {
		return rate.getDateTime();
	}

	protected double getBid() {
		return rate.getBid();
	}

	protected double getAsk() {
		return rate.getAsk();
	}
	
	protected double getSpread() {
		return rate.getSpread();
	}
}
