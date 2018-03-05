package org.github.claasahl.forex.database;

import java.time.OffsetDateTime;
import java.util.Objects;
import org.github.claasahl.forex.model.Rate;

class InternalRate implements Rate {
	private final String symbol;
	private final OffsetDateTime dateTime;
	private final double bid;
	private final double ask;

	/**
	 * Creates a {@link Rate} with the specified builder.
	 * 
	 * @param builder
	 *            the builder with which the rate is constructed
	 */
	InternalRate(Builder builder) {
		symbol = builder.getSymbol();
		dateTime = builder.getDateTime();
		bid = builder.getBid();
		ask = builder.getAsk();
	}
	
	@Override
	public String getSymbol() {
		return symbol;
	}

	@Override
	public OffsetDateTime getDateTime() {
		return dateTime;
	}

	@Override
	public double getBid() {
		return bid;
	}

	@Override
	public double getAsk() {
		return ask;
	}

	@Override
	public int hashCode() {
		return Objects.hash(symbol, dateTime, bid, ask);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof Rate))
			return false;
		Rate other = (Rate) obj;
		return Objects.equals(symbol, other.getSymbol())
				&& Objects.equals(dateTime, other.getDateTime())
				&& bid == other.getBid()
				&& ask == other.getAsk();
	}
	
	@Override
	public String toString() {
		return "Rate [symbol=" + symbol + ", dateTime=" + dateTime + ", bid=" + bid + ", ask=" + ask + "]";
	}
	
	/**
	 * The interface <code>Rate.Builder</code>. It represents ...
	 * 
	 * This interface part of the <i>builder</i> design pattern. It provides
	 * builder-functions for implementations of the {@link Rate} interface.
	 *
	 * @author Claas Ahlrichs
	 *
	 */
	public static final class Builder implements Rate {
		private String symbol;
		private OffsetDateTime dateTime;
		private double bid;
		private double ask;

		/**
		 * Creates a {@link Builder} that has been initialized with default values.
		 * <ul>
		 * <li>symbol = null</li>
		 * <li>dateTime = {@link OffsetDateTime#now()}</li>
		 * <li>bid = 0.0</li>
		 * <li>ask = 0.0</li>
		 * <ul>
		 */
		public Builder() {
			dateTime = OffsetDateTime.now();
		}
		
		/**
		 * Sets the symbol of the rate.
		 * 
		 * @param symbol
		 *            the symbol
		 * @return this builder
		 * @see Rate#getSymbol()
		 */
		public Builder setSymbol(String symbol) {
			this.symbol = symbol;
			return this;
		}

		@Override
		public String getSymbol() {
			return symbol;
		}

		/**
		 * Sets the date and time of the rate.
		 * 
		 * @param dateTime
		 *            the date and time
		 * @return this builder
		 * @see Rate#getDateTime()
		 */
		public Builder setDateTime(OffsetDateTime dateTime) {
			this.dateTime = dateTime;
			return this;
		}

		@Override
		public OffsetDateTime getDateTime() {
			return dateTime;
		}

		/**
		 * Sets the bid price of the rate.
		 * 
		 * @param price
		 *            the bid price
		 * @return this builder
		 * @see Rate#getBid()
		 */
		public Builder setBid(double price) {
			this.bid = price;
			return this;
		}

		@Override
		public double getBid() {
			return bid;
		}

		/**
		 * Sets the ask price of the rate.
		 * 
		 * @param price
		 *            the ask price
		 * @return this builder
		 * @see Rate#getAsk()
		 */
		public Builder setAsk(double price) {
			this.ask = price;
			return this;
		}

		@Override
		public double getAsk() {
			return ask;
		}

		/**
		 * Builds the rate with the specified parameters.
		 * 
		 * TODO this should also ensure that the constructed rate is valid
		 * 
		 * @return the rate with the specified parameters
		 */
		public Rate build() {
			return new InternalRate(this);
		}
	}
}
