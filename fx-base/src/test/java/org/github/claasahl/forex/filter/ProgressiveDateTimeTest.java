package org.github.claasahl.forex.filter;

import static org.github.claasahl.forex.util.Helpers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import java.time.OffsetDateTime;
import java.util.function.Function;
import org.junit.Test;

public class ProgressiveDateTimeTest {
	@Test
	public void shouldRejectDistantPast() {
		ProgressiveDateTime<OffsetDateTime> filter = new ProgressiveDateTime<>(Function.identity());
		assertThat(filter.test(now(), nowMinus(5, M1)), is(false));
	}

	@Test
	public void shouldRejectPast() {
		ProgressiveDateTime<OffsetDateTime> filter = new ProgressiveDateTime<>(Function.identity());
		assertThat(filter.test(now(), nowMinus(4, M1)), is(false));
	}

	@Test
	public void shouldRejectPresent() {
		ProgressiveDateTime<OffsetDateTime> filter = new ProgressiveDateTime<>(Function.identity());
		assertThat(filter.test(now(), now()), is(false));
	}

	@Test
	public void shouldRetainFuture() {
		ProgressiveDateTime<OffsetDateTime> filter = new ProgressiveDateTime<>(Function.identity());
		assertThat(filter.test(now(), nowPlus(4, M1)), is(true));
	}

	@Test
	public void shouldRetainDistantFuture() {
		ProgressiveDateTime<OffsetDateTime> filter = new ProgressiveDateTime<>(Function.identity());
		assertThat(filter.test(now(), nowPlus(5, M1)), is(true));
	}
}
