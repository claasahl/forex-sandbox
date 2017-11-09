package org.github.claasahl.forex.filter;

import static org.github.claasahl.forex.util.Helpers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import org.github.claasahl.forex.*;
import org.junit.*;

public class ConsistentHighPriceTest {
	private Candle.Builder builder;

	@Before
	public void before() {
		builder = new Candle.Builder()
				.setSymbol(EURUSD)
				.setDuration(M30)
				.setDateTime(midnight());
	}

	@Test
	public void shouldRejectIfOpenIsHighestPrice() {
		ConsistentHighPrice filter = new ConsistentHighPrice();
		Candle candle = builder
				.setOpen(4)
				.setHigh(3)
				.setLow(1)
				.setClose(2)
				.build();
		assertThat(filter.test(candle), is(false));
	}

	@Test
	public void shouldRetainIfHighIsHighestPrice() {
		ConsistentHighPrice filter = new ConsistentHighPrice();
		Candle candle = builder
				.setOpen(3)
				.setHigh(4)
				.setLow(1)
				.setClose(2)
				.build();
		assertThat(filter.test(candle), is(true));
	}

	@Test
	public void shouldRejectIfLowIsHighestPrice() {
		ConsistentHighPrice filter = new ConsistentHighPrice();
		Candle candle = builder
				.setOpen(3)
				.setHigh(1)
				.setLow(4)
				.setClose(2)
				.build();
		assertThat(filter.test(candle), is(false));
	}

	@Test
	public void shouldRejectIfCloseIsHighestPrice() {
		ConsistentHighPrice filter = new ConsistentHighPrice();
		Candle candle = builder
				.setOpen(3)
				.setHigh(2)
				.setLow(1)
				.setClose(4)
				.build();
		assertThat(filter.test(candle), is(false));
	}
}
