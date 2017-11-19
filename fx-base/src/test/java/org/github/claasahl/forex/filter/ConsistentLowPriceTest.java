package org.github.claasahl.forex.filter;

import static org.github.claasahl.forex.util.Helpers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import org.github.claasahl.forex.*;
import org.junit.*;

public class ConsistentLowPriceTest {
	private Candle.Builder builder;

	@Before
	public void before() {
		builder = new Candle.Builder()
				.setSymbol(EURUSD)
				.setDuration(M30)
				.setDateTime(midnight());
	}

	@Test
	public void shouldRejectIfOpenIsLowestPrice() {
		ConsistentLowPrice filter = new ConsistentLowPrice();
		Candle candle = builder
				.setOpen(1)
				.setHigh(4)
				.setLow(3)
				.setClose(2)
				.build();
		assertThat(filter.test(candle), is(false));
	}

	@Test
	public void shouldRejectIfHighIsLowestPrice() {
		ConsistentLowPrice filter = new ConsistentLowPrice();
		Candle candle = builder
				.setOpen(3)
				.setHigh(1)
				.setLow(4)
				.setClose(2)
				.build();
		assertThat(filter.test(candle), is(false));
	}

	@Test
	public void shouldRetainIfLowIsLowestPrice() {
		ConsistentLowPrice filter = new ConsistentLowPrice();
		Candle candle = builder
				.setOpen(3)
				.setHigh(4)
				.setLow(1)
				.setClose(2)
				.build();
		assertThat(filter.test(candle), is(true));
	}

	@Test
	public void shouldRejectIfCloseIsLowestPrice() {
		ConsistentLowPrice filter = new ConsistentLowPrice();
		Candle candle = builder
				.setOpen(3)
				.setHigh(4)
				.setLow(2)
				.setClose(1)
				.build();
		assertThat(filter.test(candle), is(false));
	}
}
