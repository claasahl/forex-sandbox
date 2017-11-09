package org.github.claasahl.forex.generator;

import static org.github.claasahl.forex.util.Helpers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import org.github.claasahl.forex.Rate;
import org.junit.Test;

public class RandomRatesTest {
	@Test
	public void shouldHaveSpecifiedSymbol() {
		RandomRates generator = new RandomRates(GBPUSD, 1);
		Rate rate = generator.apply(midnight());
		assertThat(rate.getSymbol(), is(GBPUSD));
	}

	@Test
	public void shouldHaveSpecifiedDateTime() {
		RandomRates generator = new RandomRates(GBPUSD, 1);
		Rate rate = generator.apply(midnight());
		assertThat(rate.getDateTime(), is(midnight()));
	}

	@Test
	public void shouldHaveValidAskBidPrices() {
		RandomRates generator = new RandomRates(GBPUSD, 1);
		Rate rate = generator.apply(midnight());
		assertThat(rate.getAsk(), greaterThanOrEqualTo(rate.getBid()));
	}
}
