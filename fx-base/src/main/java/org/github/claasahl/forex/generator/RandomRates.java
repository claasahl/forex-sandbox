package org.github.claasahl.forex.generator;

import java.time.OffsetDateTime;
import java.util.Random;
import java.util.function.Function;
import org.github.claasahl.forex.*;

/**
 * The class/interface <code>RandomRates</code>. It represents a generator of
 * {@link Rate}s with random prices.
 *
 * @author Claas Ahlrichs
 *
 */
public class RandomRates implements Function<OffsetDateTime, Rate> {
	private final Rate.Builder builder;
	private final Random random;

	/**
	 * Creates a generator of rates for the specified symbol.
	 *
	 * @param symbol
	 *            symbol of generated rates
	 */
	public RandomRates(String symbol) {
		this.builder = new Rate.Builder().setSymbol(symbol);
		this.random = new Random();
	}

	/**
	 * Creates a generator of rates for the specified symbol.
	 *
	 * @param symbol
	 *            symbol of generated rates
	 * @param seed
	 *            initial seed for random prices
	 */
	public RandomRates(String symbol, long seed) {
		this.builder = new Rate.Builder().setSymbol(symbol);
		this.random = new Random(seed);
	}

	@Override
	public Rate apply(OffsetDateTime dateTime) {
		double tmp1 = random.nextDouble();
		double tmp2 = random.nextDouble();
		
		double ask = Math.max(tmp1, tmp2);
		double bid = Math.min(tmp1, tmp2);
		return this.builder.setDateTime(dateTime).setAsk(ask).setBid(bid).build();
	}
}