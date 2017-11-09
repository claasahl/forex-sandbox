package org.github.claasahl.forex.generator;

import static org.github.claasahl.forex.util.Helpers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import java.time.*;
import org.junit.Test;

public class ProgressiveRandomizedDateTimeTest {
	@Test
	public void shouldStartWithToday() {
		ProgressiveRandomizedDateTime supplier = new ProgressiveRandomizedDateTime(midnight(), H1, 1);
		assertThat(supplier.get(), is(midnight()));
	}

	@Test
	public void shouldIncrementByAbout44M() {
		ProgressiveRandomizedDateTime supplier = new ProgressiveRandomizedDateTime(midnight(), H1, 1);
		supplier.get();
		OffsetDateTime dateTime = supplier.get();
		assertThat(dateTime, is(midnight().plus(Duration.ofMillis(2631161))));
	}

	@Test
	public void shouldIncrementByAbout1H41M() {
		ProgressiveRandomizedDateTime supplier = new ProgressiveRandomizedDateTime(midnight(), H1, 1);
		supplier.get();
		supplier.get();
		supplier.get();
		supplier.get();
		OffsetDateTime dateTime = supplier.get();
		assertThat(dateTime, is(midnight().plus(Duration.ofMillis(6053005))));
	}
}
