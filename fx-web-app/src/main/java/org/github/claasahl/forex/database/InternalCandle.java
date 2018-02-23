package org.github.claasahl.forex.database;

import java.time.*;
import java.util.Objects;
import org.github.claasahl.forex.model.Candle;

/**
 * The interface {@link Candle}. It is intended to represent a candlestick. The
 * candlestick is defined by its primary properties (i.e. symbol, opening time,
 * duration, opening price, high price, low price and closing price).
 *
 * @author Claas Ahlrichs
 *
 */
class InternalCandle implements Candle {
	private final int brokerId;
	private final String symbol;
	private final OffsetDateTime dateTime;
	private final Duration duration;
	private final double open;
	private final double high;
	private final double low;
	private final double close;
	private final long volume;

	/**
	 * Creates a {@link Candle} with the specified builder.
	 * 
	 * @param builder
	 *            the builder with which the candle is constructed
	 */
	InternalCandle(Builder builder) {
		brokerId = builder.brokerId;
		symbol = builder.symbol;
		dateTime = builder.dateTime;
		duration = builder.duration;
		open = builder.open;
		high = builder.high;
		low = builder.low;
		close = builder.close;
		volume = builder.volume;
	}
	
	@Override
	public int getBrokerId() {
		return brokerId;
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
	public Duration getDuration() {
		return duration;
	}

	@Override
	public double getOpen() {
		return open;
	}

	@Override
	public double getHigh() {
		return high;
	}

	@Override
	public double getLow() {
		return low;
	}

	@Override
	public double getClose() {
		return close;
	}

	@Override
	public long getVolume() {
		return volume;
	}

	@Override
	public int hashCode() {
		return Objects.hash(brokerId, symbol, dateTime, duration, open, high, low, close, volume);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof Candle))
			return false;
		Candle other = (Candle) obj;
		return brokerId == other.getBrokerId()
				&& Objects.equals(symbol, other.getSymbol())
				&& Objects.equals(dateTime, other.getDateTime())
				&& Objects.equals(duration, other.getDuration())
				&& open == other.getOpen()
				&& high == other.getHigh()
				&& low == other.getLow()
				&& close == other.getClose()
				&& volume == other.getVolume();
	}

	@Override
	public String toString() {
		return "Candle [broker="+ brokerId +", symbol=" + symbol + ", dateTime=" + dateTime + ", duration=" + duration + ", open=" + open
				+ ", high=" + high + ", low=" + low + ", close=" + close + ", volume=" + volume + "]";
	}
	
	/**
	 * The interface <code>Builder</code>. It represents ...
	 * 
	 * This interface is part of the <i>builder</i> design pattern. It provides
	 * builder-functions for implementations of the {@link Candle} interface.
	 *
	 * @author Claas Ahlrichs
	 *
	 */
	public static final class Builder implements Candle {
		private int brokerId;
		private String symbol;
		private OffsetDateTime dateTime;
		private Duration duration;
		private double open;
		private double high;
		private double low;
		private double close;
		private long volume;

		/**
		 * Creates a {@link Builder} that has been initialized with default values.
		 * <ul>
		 * <li>brokerId = 0</li>
		 * <li>symbol = null</li>
		 * <li>dateTime = {@link OffsetDateTime#now()}</li>
		 * <li>duration = null</li>
		 * <li>open = 0.0</li>
		 * <li>high = 0.0</li>
		 * <li>low = 0.0</li>
		 * <li>close = 0.0</li>
		 * <li>volume = 0</li>
		 * <ul>
		 */
		public Builder() {
			dateTime = OffsetDateTime.now();
		}
		
		@Override
		public int getBrokerId() {
			return brokerId;
		}
		
		/**
		 * Sets the broker (ID) of the candle.
		 * 
		 * @param brokerId
		 *            the broker (ID)
		 * @return this builder
		 * @see Candle#getBrokerId()
		 */
		public Builder setBrokerId(int brokerId) {
			this.brokerId = brokerId;
			return this;
		}

		@Override
		public String getSymbol() {
			return symbol;
		}

		/**
		 * Sets the symbol of the candle.
		 * 
		 * @param symbol
		 *            the symbol
		 * @return this builder
		 * @see Candle#getSymbol()
		 */
		public Builder setSymbol(String symbol) {
			this.symbol = symbol;
			return this;
		}

		@Override
		public OffsetDateTime getDateTime() {
			return dateTime;
		}

		/**
		 * Sets the date and time of the candle.
		 * 
		 * @param dateTime
		 *            the date and time
		 * @return this builder
		 * @see Candle#getDateTime()
		 */
		public Builder setDateTime(OffsetDateTime dateTime) {
			this.dateTime = dateTime;
			return this;
		}

		@Override
		public Duration getDuration() {
			return duration;
		}

		/**
		 * Sets the duration of the candle.
		 * 
		 * @param duration
		 *            the duration
		 * @return this builder
		 * @see Candle#getDuration()
		 */
		public Builder setDuration(Duration duration) {
			this.duration = duration;
			return this;
		}

		@Override
		public double getOpen() {
			return open;
		}

		/**
		 * Sets the opening price of the candle.
		 * 
		 * @param price
		 *            the opening price
		 * @return this builder
		 * @see Candle#getOpen()
		 */
		public Builder setOpen(double price) {
			this.open = price;
			return this;
		}

		@Override
		public double getHigh() {
			return high;
		}

		/**
		 * Sets the high price of the candle.
		 * 
		 * @param price
		 *            the high price
		 * @return this builder
		 * @see Candle#getHigh()
		 */
		public Builder setHigh(double price) {
			this.high = price;
			return this;
		}

		@Override
		public double getLow() {
			return low;
		}

		/**
		 * Sets the low price of the candle.
		 * 
		 * @param price
		 *            the low price
		 * @return this builder
		 * @see Candle#getLow()
		 */
		public Builder setLow(double price) {
			this.low = price;
			return this;
		}

		@Override
		public double getClose() {
			return close;
		}

		/**
		 * Sets the closing price of the candle.
		 * 
		 * @param price
		 *            the closing price
		 * @return this builder
		 * @see Candle#getClose()
		 */
		public Builder setClose(double price) {
			this.close = price;
			return this;
		}
		
		@Override
		public long getVolume() {
			return volume;
		}

		/**
		 * Sets the volume of the candle.
		 * 
		 * @param volume
		 *            the volume
		 * @return this builder
		 * @see Candle#getVolume();
		 */
		public Builder setVolume(long volume) {
			this.volume = volume;
			return this;
		}

		/**
		 * Builds the candle with the specified parameters.
		 * 
		 * TODO this should also ensure that the constructed candle is valid
		 * 
		 * @return the candle with the specified parameters
		 */
		public Candle build() {
			return new InternalCandle(this);
		}
	}
}
