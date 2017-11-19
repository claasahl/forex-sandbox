package org.github.claasahl.forex.generator;

import static org.github.claasahl.forex.util.Helpers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import java.util.*;
import org.github.claasahl.forex.Candle;
import org.junit.Test;

public class RandomCandlesTest {
	@Test
	public void shouldHaveSpecifiedSymbol() {
		RandomCandles generator = new RandomCandles(GBPUSD, M5, 1);
		Candle candle = generator.apply(midnight());
		assertThat(candle.getSymbol(), is(GBPUSD));
	}

	@Test
	public void shouldHaveSpecifiedDuration() {
		RandomCandles generator = new RandomCandles(GBPUSD, M5, 1);
		Candle candle = generator.apply(midnight());
		assertThat(candle.getDuration(), is(M5));
	}

	@Test
	public void shouldHaveSpecifiedDateTime() {
		RandomCandles generator = new RandomCandles(GBPUSD, M5, 1);
		Candle candle = generator.apply(midnight());
		assertThat(candle.getDateTime(), is(midnight()));
	}

	@Test
	public void shouldHaveDifferentPrices() {
		RandomCandles generator = new RandomCandles(GBPUSD, M5, 1);
		Candle candle = generator.apply(midnight());
		Set<Double> prices = new HashSet<>();
		prices.add(candle.getOpen());
		prices.add(candle.getHigh());
		prices.add(candle.getLow());
		prices.add(candle.getClose());
		assertThat(prices.size(), is(4));
	}
	
	@Test
	public void shouldHaveValidHighPrice() {
		RandomCandles generator = new RandomCandles(GBPUSD, M5, 1);
		Candle candle = generator.apply(midnight());
		assertThat(candle.getHigh(), greaterThanOrEqualTo(candle.getLow()));
		assertThat(candle.getHigh(), greaterThanOrEqualTo(candle.getOpen()));
		assertThat(candle.getHigh(), greaterThanOrEqualTo(candle.getClose()));
	}
	
	@Test
	public void shouldHaveValidLowPrice() {
		RandomCandles generator = new RandomCandles(GBPUSD, M5, 1);
		Candle candle = generator.apply(midnight());
		assertThat(candle.getLow(), lessThanOrEqualTo(candle.getOpen()));
		assertThat(candle.getLow(), lessThanOrEqualTo(candle.getClose()));
		assertThat(candle.getLow(), lessThanOrEqualTo(candle.getHigh()));
	}
}
