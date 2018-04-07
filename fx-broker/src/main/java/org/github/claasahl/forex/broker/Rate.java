package org.github.claasahl.forex.broker;

import java.time.OffsetDateTime;
import java.util.Objects;

/**
 * The interface {@link Rate}. It is intended to represent an exchange rate. The
 * rate is defined by its primary properties (i.e. symbol, time, bid price and
 * ask price).
 *
 * @author Claas Ahlrichs
 *
 */
public final class Rate implements Comparable<Rate> {
	private final OffsetDateTime dateTime;
	private final double bid;
	private final double ask;

	/**
	 * Creates a {@link Rate} with the specified builder.
	 * 
	 * @param builder
	 *            the builder with which the rate is constructed
	 */
	Rate(Builder builder) {
		dateTime = builder.getDateTime();
		bid = builder.getBid();
		ask = builder.getAsk();
	}

	/**
	 * Returns the date and time of this rate. The instantaneous point on the
	 * time-line at which this rate was observed.
	 * 
	 * @return the date and time
	 */
	public OffsetDateTime getDateTime() {
		return dateTime;
	}

	/**
	 * Returns the bid price of this rate. It represents the price at which the
	 * market is prepared to buy a product (i.e. the price at which a trader can
	 * sell a product).
	 *
	 * @return the bid price
	 */
	public double getBid() {
		return bid;
	}

	/**
	 * Returns the ask price of this rate. It represents the price at which the
	 * market is prepared to sell a product (i.e. the price at which a trader can
	 * buy a product). The ask price is also known as the offer.
	 *
	 * @return the ask price
	 */
	public double getAsk() {
		return ask;
	}

	/**
	 * Returns the spread this rate. It represents the difference between the bid
	 * price and the ask price.
	 *
	 * @return the spread
	 */
	public double getSpread() {
		return bid - ask;
	}

	/**
	 * Returns a {@link Builder} that has been initialized with this rate.
	 * 
	 * @return a {@link Builder}
	 */
	public Builder toBuilder() {
		return new Builder()
				.setDateTime(dateTime)
				.setBid(bid)
				.setAsk(ask);
	}
	

	@Override
	public int compareTo(Rate o) {
		return dateTime.compareTo(o.dateTime);
	}

	@Override
	public int hashCode() {
		return Objects.hash(dateTime, bid, ask);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof Rate))
			return false;
		Rate other = (Rate) obj;
		return Objects.equals(dateTime, other.dateTime)
				&& bid == other.bid
				&& ask == other.ask;
	}
	
	@Override
	public String toString() {
		return "Rate [dateTime=" + dateTime + ", bid=" + bid + ", ask=" + ask + "]";
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
	public static final class Builder {
		private OffsetDateTime dateTime;
		private double bid;
		private double ask;

		/**
		 * Creates a {@link Builder} that has been initialized with default values.
		 * <ul>
		 * <li>dateTime = {@link OffsetDateTime#now()}</li>
		 * <li>bid = 0.0</li>
		 * <li>ask = 0.0</li>
		 * <ul>
		 */
		public Builder() {
			dateTime = OffsetDateTime.now();
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
			return new Rate(this);
		}
	}
}
