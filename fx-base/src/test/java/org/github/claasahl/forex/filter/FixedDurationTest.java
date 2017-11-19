package org.github.claasahl.forex.filter;

import static org.github.claasahl.forex.util.Helpers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import java.time.Duration;
import java.util.function.Function;
import org.junit.Test;

public class FixedDurationTest {
	@Test
	public void shouldRetain() {
		FixedDuration<Duration> filter = new FixedDuration<>(M5, Function.identity());
		assertThat(filter.test(M5), is(true));
	}

	@Test
	public void shouldReject() {
		FixedDuration<Duration> filter = new FixedDuration<>(M5, Function.identity());
		assertThat(filter.test(H1), is(false));
	}
}
