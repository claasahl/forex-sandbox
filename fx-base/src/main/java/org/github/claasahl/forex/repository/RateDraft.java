package org.github.claasahl.forex.repository;

import java.time.*;
import javax.persistence.*;
import org.github.claasahl.forex.*;
import org.github.claasahl.forex.Candle.Builder;

/**
 * The interface <code>Builder</code>. It represents ...
 * 
 * This interface is part of the <i>builder</i> design pattern. It provides
 * builder-functions for implementations of the {@link Candle} interface.
 *
 * @author Claas Ahlrichs
 *
 */
@Entity
class RateDraft {
	@Id
	private long id;
	@ManyToOne(cascade=CascadeType.ALL)
	private SymbolDraft symbol;
	@Basic
	private OffsetDateTime dateTime;
	@Basic
	private double bid;
	@Basic
	private double ask;

	/**
	 * Creates a {@link Builder} that has been initialized with default values.
	 * <ul>
	 * <li>symbol = EURUSD</li>
	 * <li>dateTime = {@link OffsetDateTime#now()}</li>
	 * <li>duration = 1 hour</li>
	 * <li>open = 0.0</li>
	 * <li>high = 0.0</li>
	 * <li>low = 0.0</li>
	 * <li>close = 0.0</li>
	 * <li>volume = 0</li>
	 * <ul>
	 */
	public RateDraft() {
		symbol = new SymbolDraft();
		dateTime = OffsetDateTime.now();
	}
	
	public RateDraft(Rate rate, SymbolDraft symbolDraft) {
		symbol = symbolDraft;
		dateTime = rate.getDateTime();
		bid = rate.getBid();
		ask = rate.getAsk();
	}

	/**
	 * Builds the candle with the specified parameters.
	 * 
	 * @return the candle with the specified parameters
	 */
	public Rate build() {
		return new Rate.Builder()
				.setSymbol(symbol.getName())
				.setDateTime(dateTime)
				.setBid(bid)
				.setAsk(ask)
				.build();
	}
}
