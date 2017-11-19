package org.github.claasahl.forex.filter;

import static org.github.claasahl.forex.util.Helpers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import java.time.OffsetDateTime;
import java.util.function.Function;
import org.junit.Test;

public class BeforeDateTimeTest {
	@Test
	public void shouldRetainDistantPast() {
		BeforeDateTime<OffsetDateTime> filter = new BeforeDateTime<>(now(), Function.identity());
		assertThat(filter.test(nowMinus(10, M1)), is(true));
	}

	@Test
	public void shouldRetainPast() {
		BeforeDateTime<OffsetDateTime> filter = new BeforeDateTime<>(now(), Function.identity());
		assertThat(filter.test(nowMinus(1, M1)), is(true));
	}

	@Test
	public void shouldRejectPresent() {
		BeforeDateTime<OffsetDateTime> filter = new BeforeDateTime<>(now(), Function.identity());
		assertThat(filter.test(now()), is(false));
	}

	@Test
	public void shouldRejectFuture() {
		BeforeDateTime<OffsetDateTime> filter = new BeforeDateTime<>(now(), Function.identity());
		assertThat(filter.test(nowPlus(1, M1)), is(false));
	}

	@Test
	public void shouldRejectDistantFuture() {
		BeforeDateTime<OffsetDateTime> filter = new BeforeDateTime<>(now(), Function.identity());
		assertThat(filter.test(nowPlus(10, M1)), is(false));
	}
}
