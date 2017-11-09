package org.github.claasahl.forex.generator;

import static org.github.claasahl.forex.util.Helpers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import java.time.*;
import org.junit.Test;

public class ProgressiveDateTimeTest {
	@Test
	public void shouldStartWithToday() {
		ProgressiveDateTime supplier = new ProgressiveDateTime(midnight(), H1);
		assertThat(supplier.get(), is(midnight()));
	}

	@Test
	public void shouldIncrementByOneHour() {
		ProgressiveDateTime supplier = new ProgressiveDateTime(midnight(), H1);
		supplier.get();
		OffsetDateTime dateTime = supplier.get();
		assertThat(dateTime, is(midnight().plus(Duration.ofHours(1))));
	}

	@Test
	public void shouldIncrementByFourHours() {
		ProgressiveDateTime supplier = new ProgressiveDateTime(midnight(), H1);
		supplier.get();
		supplier.get();
		supplier.get();
		supplier.get();
		OffsetDateTime dateTime = supplier.get();
		assertThat(dateTime, is(midnight().plus(Duration.ofHours(4))));
	}
}
