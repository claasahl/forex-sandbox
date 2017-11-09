package org.github.claasahl.forex.filter;

import static org.github.claasahl.forex.util.Helpers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import java.time.OffsetDateTime;
import java.util.function.Function;
import org.junit.Test;

public class LiveDateTimeTest {
	@Test
	public void shouldRejectDistantPast() {
		LiveDateTime<OffsetDateTime> filter = new LiveDateTime<>(M5, Function.identity());
		assertThat(filter.test(nowMinus(5, M1)), is(false));
	}

	@Test
	public void shouldRetainPast() {
		LiveDateTime<OffsetDateTime> filter = new LiveDateTime<>(M5, Function.identity());
		assertThat(filter.test(nowMinus(4, M1)), is(true));
	}

	@Test
	public void shouldRetainPresent() {
		LiveDateTime<OffsetDateTime> filter = new LiveDateTime<>(M5, Function.identity());
		assertThat(filter.test(now()), is(true));
	}

	@Test
	public void shouldRetainFuture() {
		LiveDateTime<OffsetDateTime> filter = new LiveDateTime<>(M5, Function.identity());
		assertThat(filter.test(nowPlus(4, M1)), is(true));
	}

	@Test
	public void shouldRejectDistantFuture() {
		LiveDateTime<OffsetDateTime> filter = new LiveDateTime<>(M5, Function.identity());
		assertThat(filter.test(nowPlus(6, M1)), is(false));
	}
}
