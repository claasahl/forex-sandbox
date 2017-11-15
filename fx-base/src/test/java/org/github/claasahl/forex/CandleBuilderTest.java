package org.github.claasahl.forex;

import static org.github.claasahl.forex.util.Helpers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import java.time.*;
import org.junit.*;

public class CandleBuilderTest {
	private Candle defaultCandle;
	private Candle copiedCandle;

	@Before
	public void before() {
		defaultCandle = new Candle.Builder().build();
		Candle customCandle = new Candle.Builder()
				.setSymbol(GBPUSD)
				.setDuration(M15)
				.setDateTime(midnight())
				.setOpen(6)
				.setHigh(8)
				.setLow(4)
				.setClose(5)
				.setVolume(55)
				.build();
		copiedCandle = customCandle.toBuilder().build();
	}

	@Test
	public void defaultValueForSymbol() {
		assertThat(defaultCandle.getSymbol(), is(EURUSD));
	}

	@Test
	public void defaultValueForDateTime() {
		Duration duration = Duration.between(OffsetDateTime.now(), defaultCandle.getDateTime());
		assertThat(duration, lessThanOrEqualTo(Duration.ofSeconds(1)));
	}

	@Test
	public void defaultValueForDuration() {
		assertThat(defaultCandle.getDuration(), is(Duration.ofHours(1)));
	}

	@Test
	public void defaultValueForOpen() {
		assertThat(defaultCandle.getOpen(), is(0.0));
	}

	@Test
	public void defaultValueForHigh() {
		assertThat(defaultCandle.getHigh(), is(0.0));
	}

	@Test
	public void defaultValueForLow() {
		assertThat(defaultCandle.getLow(), is(0.0));
	}

	@Test
	public void defaultValueForClose() {
		assertThat(defaultCandle.getClose(), is(0.0));
	}
	
	@Test
	public void defaultValueForVolume() {
		assertThat(defaultCandle.getVolume(), is(0l));
	}

	@Test
	public void symbolShouldBeCopied() {
		assertThat(copiedCandle.getSymbol(), is(GBPUSD));
	}

	@Test
	public void dateTimeShouldBeCopied() {
		assertThat(copiedCandle.getDateTime(), is(midnight()));
	}

	@Test
	public void durationShouldBeCopied() {
		assertThat(copiedCandle.getDuration(), is(Duration.ofMinutes(15)));
	}

	@Test
	public void openShouldBeCopied() {
		assertThat(copiedCandle.getOpen(), is(6.0));
	}

	@Test
	public void highShouldBeCopied() {
		assertThat(copiedCandle.getHigh(), is(8.0));
	}

	@Test
	public void lowShouldBeCopied() {
		assertThat(copiedCandle.getLow(), is(4.0));
	}

	@Test
	public void closeShouldBeCopied() {
		assertThat(copiedCandle.getClose(), is(5.0));
	}
	
	@Test
	public void volumeShouldBeCopied() {
		assertThat(copiedCandle.getVolume(), is(55l));
	}
}
