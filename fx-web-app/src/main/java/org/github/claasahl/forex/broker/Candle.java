package org.github.claasahl.forex.broker;

import java.time.*;
import java.util.Objects;

/**
 * The interface {@link Candle}. It is intended to represent a candlestick. The
 * candlestick is defined by its primary properties (i.e. symbol, opening time,
 * duration, opening price, high price, low price and closing price).
 *
 * @author Claas Ahlrichs
 *
 */
public final class Candle implements Comparable<Candle>{
	private final OffsetDateTime dateTime;
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
	Candle(Builder builder) {
		dateTime = builder.dateTime;
		open = builder.open;
		high = builder.high;
		low = builder.low;
		close = builder.close;
		volume = builder.volume;
	}

	/**
	 * Returns the date and time of this candle. The instantaneous point on the
	 * time-line at which this candle was opened. Not to be confused with the
	 * instantaneous point at which opening price was observed. This candle's
	 * opening price may have been observed before this point.
	 * <p/>
	 * The opening time and duration form an artificial boundary and represent the
	 * lifetime of this candle. Price changes during this time window are summarized
	 * in this candle.
	 * 
	 * @return the date and time
	 */
	public OffsetDateTime getDateTime() {
		return dateTime;
	}

	/**
	 * Returns the opening price of this candle. It represents the price that was
	 * valid at the opening time. This value is free to move within the upper and
	 * lower boundaries (i.e. high price and low price, respectively).
	 * </p>
	 * It is important to note that the opening price may not always be observed at
	 * the opening time of this candle. The opening price either represents the last
	 * price of a previous candle or the first price change within the lifetime of
	 * this candle (i.e. between its opening and closing time). As such, the opening
	 * price may be observed before, exactly at the opening time or after the
	 * opening time. In any case, it is considered to represent the price that was
	 * valid at the opening time (even though it may not have been observed exactly
	 * at the opening time).
	 *
	 * @return the opening price
	 */
	public double getOpen() {
		return open;
	}

	/**
	 * Returns the high price of this candle. It represents the highest price that
	 * was observed during the lifetime of this candle (i.e. between its opening and
	 * closing time). The value also represents the upper boundary of this candle.
	 * Naturally, opening price, low price and closing price must all be less or
	 * equal.
	 *
	 * @return the high price
	 */
	public double getHigh() {
		return high;
	}

	/**
	 * Returns the low price of this candle. It represents the lowest price that was
	 * observed during the lifetime of this candle (i.e. between its opening and
	 * closing time). The value also represents the lower boundary of this candle.
	 * Naturally, opening price, high price and closing price must all be greater or
	 * equal.
	 *
	 * @return the low price
	 */
	public double getLow() {
		return low;
	}

	/**
	 * Returns the closing price of this candle. It represents the price that was
	 * valid at the closing time. This value is free to move within the upper and
	 * lower boundaries (i.e. high price and low price, respectively).
	 * </p>
	 * It is important to note that the closing price may not always be observed at
	 * the closing time of this candle. The closing price represents the last price
	 * change within the lifetime of this candle (i.e. between its opening and
	 * closing time). As such, the closing price may be observed before or exactly
	 * at the closing time, but never after the closing time. In any case, it is
	 * considered to represent the price that was valid at the closing time (even
	 * though it may not have been observed exactly at the closing time).
	 * 
	 * @return the closing price
	 */
	public double getClose() {
		return close;
	}

	/**
	 * Returns the volume of this candle.
	 * 
	 * @return the volume
	 */
	public long getVolume() {
		return volume;
	}

	/**
	 * Returns a {@link Builder} that has been initialized with this candle.
	 * 
	 * @return a {@link Builder}
	 */
	public Builder toBuilder() {
		return new Builder()
				.setDateTime(dateTime)
				.setOpen(open)
				.setHigh(high)
				.setLow(low)
				.setClose(close)
				.setVolume(volume);
	}
	
	@Override
	public int compareTo(Candle o) {
		return dateTime.compareTo(o.dateTime);
	}

	@Override
	public int hashCode() {
		return Objects.hash(dateTime, open, high, low, close, volume);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof Candle))
			return false;
		Candle other = (Candle) obj;
		return Objects.equals(dateTime, other.dateTime)
				&& open == other.open
				&& high == other.high
				&& low == other.low
				&& close == other.close
				&& volume == other.volume;
	}

	@Override
	public String toString() {
		return "Candle [dateTime=" + dateTime + ", open=" + open + ", high=" + high + ", low=" + low + ", close=" + close + ", volume=" + volume + "]";
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
	public static final class Builder {
		private OffsetDateTime dateTime;
		private double open;
		private double high;
		private double low;
		private double close;
		private long volume;

		/**
		 * Creates a {@link Builder} that has been initialized with default values.
		 * <ul>
		 * <li>dateTime = {@link OffsetDateTime#now()}</li>
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
			return new Candle(this);
		}
	}
}
