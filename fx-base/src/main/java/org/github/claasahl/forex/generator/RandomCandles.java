package org.github.claasahl.forex.generator;

import java.time.*;
import java.util.*;
import java.util.function.Function;
import org.github.claasahl.forex.*;

/**
 * The class/interface <code>RandomRates</code>. It represents a generator of
 * {@link Candle}s with random prices.
 *
 * @author Claas Ahlrichs
 *
 */
public class RandomCandles implements Function<OffsetDateTime, Candle> {
	private final Candle.Builder builder;
	private final Random random;
	private final List<Double> prices;

	/**
	 * Creates a generator of candles for the specified symbol.
	 *
	 * @param symbol
	 *            symbol of generated candles
	 * @param duration
	 *            duration of generated candles
	 */
	public RandomCandles(String symbol, Duration duration) {
		this.builder = new Candle.Builder().setSymbol(symbol).setDuration(duration);
		this.random = new Random();
		this.prices = new ArrayList<>();
	}

	/**
	 * Creates a generator of candles for the specified symbol.
	 *
	 * @param symbol
	 *            symbol of generated candles
	 * @param duration
	 *            duration of generated candles
	 * @param seed
	 *            initial seed for random prices
	 */
	public RandomCandles(String symbol, Duration duration, long seed) {
		this.builder = new Candle.Builder().setSymbol(symbol).setDuration(duration);
		this.random = new Random(seed);
		this.prices = new ArrayList<>();
	}

	@Override
	public Candle apply(OffsetDateTime dateTime) {
		prices.add(random.nextDouble());
		prices.add(random.nextDouble());
		prices.add(random.nextDouble());
		prices.add(random.nextDouble());
		double high = prices.stream().max(Double::compareTo).orElse(Double.NaN);
		prices.remove(high);
		double low = prices.stream().min(Double::compareTo).orElse(Double.NaN);
		prices.remove(low);
		double open = prices.remove(0);
		double close = prices.remove(0);
		return builder.setDateTime(dateTime)
				.setOpen(open)
				.setHigh(high)
				.setLow(low)
				.setClose(close)
				.build();
	}
}