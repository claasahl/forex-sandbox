package org.github.claasahl.forex.filter;

import static org.github.claasahl.forex.util.Helpers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import java.util.function.Function;
import org.junit.Test;

public class FixedSymbolTest {
	@Test
	public void shouldRetain() {
		FixedSymbol<String> filter = new FixedSymbol<>(EURUSD, Function.identity());
		assertThat(filter.test(EURUSD), is(true));
	}

	@Test
	public void shouldReject() {
		FixedSymbol<String> filter = new FixedSymbol<>(EURUSD, Function.identity());
		assertThat(filter.test(GBPUSD), is(false));
	}
}
