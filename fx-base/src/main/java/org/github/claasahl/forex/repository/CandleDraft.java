package org.github.claasahl.forex.repository;

import java.time.*;
import javax.persistence.*;
import org.github.claasahl.forex.Candle;
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
class CandleDraft {
	@Id
	private long id;
	@ManyToOne(cascade=CascadeType.ALL)
	private SymbolDraft symbol;
	@Basic
	private OffsetDateTime dateTime;
	@Basic
	private double open;
	@Basic
	private double high;
	@Basic
	private double low;
	@Basic
	private double close;
	@Basic
	private long volume;

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
	public CandleDraft() {
		symbol = new SymbolDraft();
		dateTime = OffsetDateTime.now();
	}
	
	public CandleDraft(Candle candle, SymbolDraft symbolDraft) {
		symbol = symbolDraft;
		dateTime = candle.getDateTime();
		open = candle.getOpen();
		high = candle.getHigh();
		low = candle.getLow();
		close = candle.getClose();
		volume = candle.getVolume();
	}

	/**
	 * Builds the candle with the specified parameters.
	 * 
	 * @return the candle with the specified parameters
	 */
	public Candle build() {
		return new Candle.Builder()
				.setSymbol(symbol.getName())
				.setDuration(symbol.getDuration())
				.setDateTime(dateTime)
				.setOpen(open)
				.setHigh(high)
				.setLow(low)
				.setClose(close)
				.setVolume(volume)
				.build();
	}
}
