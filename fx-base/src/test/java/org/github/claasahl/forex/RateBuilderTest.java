package org.github.claasahl.forex;

import static org.github.claasahl.forex.util.Helpers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import java.time.*;
import org.junit.*;

public class RateBuilderTest {
	private Rate defaultRate;
	private Rate copiedRate;

	@Before
	public void before() {
		defaultRate = new Rate.Builder().build();
		Rate customRate = new Rate.Builder()
				.setSymbol(GBPUSD)
				.setDateTime(midnight())
				.setBid(6)
				.setAsk(8)
				.build();
		copiedRate = customRate.toBuilder().build();
	}

	@Test
	public void defaultValueForSymbol() {
		assertThat(defaultRate.getSymbol(), is(EURUSD));
	}

	@Test
	public void defaultValueForDateTime() {
		Duration duration = Duration.between(OffsetDateTime.now(), defaultRate.getDateTime());
		assertThat(duration, lessThanOrEqualTo(Duration.ofSeconds(1)));
	}

	@Test
	public void defaultValueForBid() {
		assertThat(defaultRate.getBid(), is(0.0));
	}

	@Test
	public void defaultValueForAsk() {
		assertThat(defaultRate.getAsk(), is(0.0));
	}

	@Test
	public void symbolShouldBeCopied() {
		assertThat(copiedRate.getSymbol(), is(GBPUSD));
	}

	@Test
	public void dateTimeShouldBeCopied() {
		assertThat(copiedRate.getDateTime(), is(midnight()));
	}

	@Test
	public void bidShouldBeCopied() {
		assertThat(copiedRate.getBid(), is(6.0));
	}

	@Test
	public void askShouldBeCopied() {
		assertThat(copiedRate.getAsk(), is(8.0));
	}
}
