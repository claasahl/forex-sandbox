package org.github.claasahl.forex;

import static org.github.claasahl.forex.util.Helpers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import org.junit.*;

public class CandleTest {
	private Candle.Builder builder;

	@Before
	public void before() {
		builder = new Candle.Builder()
				.setSymbol(GBPUSD)
				.setDuration(M15)
				.setDateTime(midnight())
				.setOpen(6)
				.setHigh(8)
				.setLow(4)
				.setClose(5);
	}
	
	@Test
	public void shouldComeBefore() {
		Candle candleA = builder.setDateTime(midnight().plusHours(1)).build();
		Candle candleB = builder.setDateTime(midnight().plusHours(12)).build();
		assertThat(candleA.compareTo(candleB), lessThan(0));
	}
	
	@Test
	public void shouldBeSame() {
		Candle candleA = builder.setDateTime(midnight().plusHours(4)).build();
		Candle candleB = builder.setDateTime(midnight().plusHours(4)).build();
		assertThat(candleA.compareTo(candleB), is(0));
	}
	
	@Test
	public void shouldComeAfter() {
		Candle candleA = builder.setDateTime(midnight().plusHours(12)).build();
		Candle candleB = builder.setDateTime(midnight().plusHours(1)).build();
		assertThat(candleA.compareTo(candleB), greaterThan(0));
	}

	@Test
	public void implementationOfEqualsShouldHandleDifferentSymbol() {
		Candle candleA = builder.build();
		Candle candleB = builder.setSymbol(EURUSD).build();
		assertThat(candleA.equals(candleB), is(false));
	}

	@Test
	public void implementationOfEqualsShouldHandleDifferentDuration() {
		Candle candleA = builder.build();
		Candle candleB = builder.setDuration(D1).build();
		assertThat(candleA.equals(candleB), is(false));
	}

	@Test
	public void implementationOfEqualsShouldHandleDifferentDateTime() {
		Candle candleA = builder.build();
		Candle candleB = builder.setDateTime(midnightMinus(1, D1)).build();
		assertThat(candleA.equals(candleB), is(false));
	}

	@Test
	public void implementationOfEqualsShouldHandleDifferentOpen() {
		Candle candleA = builder.build();
		Candle candleB = builder.setOpen(5).build();
		assertThat(candleA.equals(candleB), is(false));
	}

	@Test
	public void implementationOfEqualsShouldHandleDifferentHigh() {
		Candle candleA = builder.build();
		Candle candleB = builder.setHigh(9).build();
		assertThat(candleA.equals(candleB), is(false));
	}

	@Test
	public void implementationOfEqualsShouldHandleDifferentLow() {
		Candle candleA = builder.build();
		Candle candleB = builder.setLow(3).build();
		assertThat(candleA.equals(candleB), is(false));
	}

	@Test
	public void implementationOfEqualsShouldHandleDifferentClose() {
		Candle candleA = builder.build();
		Candle candleB = builder.setClose(7).build();
		assertThat(candleA.equals(candleB), is(false));
	}
	
	@Test
	public void implementationOfEqualsShouldHandleDifferentVolume() {
		Candle candleA = builder.build();
		Candle candleB = builder.setVolume(7).build();
		assertThat(candleA.equals(candleB), is(false));
	}

	@Test
	public void implementationOfEqualsShouldBeReflexive() {
		Candle candle = builder.build();
		assertThat(candle.equals(candle), is(true));
	}

	@Test
	public void implementationOfEqualsShouldBeSymmetric() {
		Candle candleA = builder.build();
		Candle candleB = builder.build();
		assertThat(candleA.equals(candleB), is(true));
		assertThat(candleB.equals(candleA), is(true));
	}

	@Test
	public void implementationOfEqualsShouldBeTransitive() {
		Candle candleA = builder.build();
		Candle candleB = builder.build();
		Candle candleC = builder.build();
		assertThat(candleA.equals(candleB), is(true));
		assertThat(candleB.equals(candleC), is(true));
		assertThat(candleA.equals(candleC), is(true));
	}

	@Test
	public void implementationOfHashCodeShouldHandleDifferentSymbol() {
		int hashCode1 = builder.build().hashCode();
		int hashCode2 = builder.setSymbol(EURUSD).build().hashCode();
		assertThat(hashCode1, not(is(hashCode2)));
	}

	@Test
	public void implementationOfHashCodeShouldHandleDifferentDuration() {
		int hashCode1 = builder.build().hashCode();
		int hashCode2 = builder.setDuration(D1).build().hashCode();
		assertThat(hashCode1, not(is(hashCode2)));
	}

	@Test
	public void implementationOfHashCodeShouldHandleDifferentDateTime() {
		int hashCode1 = builder.build().hashCode();
		int hashCode2 = builder.setDateTime(midnightMinus(1, D1)).build().hashCode();
		assertThat(hashCode1, not(is(hashCode2)));
	}

	@Test
	public void implementationOfHashCodeShouldHandleDifferentOpen() {
		int hashCode1 = builder.build().hashCode();
		int hashCode2 = builder.setOpen(5).build().hashCode();
		assertThat(hashCode1, not(is(hashCode2)));
	}

	@Test
	public void implementationOfHashCodeShouldHandleDifferentHigh() {
		int hashCode1 = builder.build().hashCode();
		int hashCode2 = builder.setHigh(9).build().hashCode();
		assertThat(hashCode1, not(is(hashCode2)));
	}

	@Test
	public void implementationOfHashCodeShouldHandleDifferentLow() {
		int hashCode1 = builder.build().hashCode();
		int hashCode2 = builder.setLow(3).build().hashCode();
		assertThat(hashCode1, not(is(hashCode2)));
	}

	@Test
	public void implementationOfHashCodeShouldHandleDifferentClose() {
		int hashCode1 = builder.build().hashCode();
		int hashCode2 = builder.setClose(7).build().hashCode();
		assertThat(hashCode1, not(is(hashCode2)));
	}
	
	@Test
	public void implementationOfHashCodeShouldHandleDifferentVolume() {
		int hashCode1 = builder.build().hashCode();
		int hashCode2 = builder.setVolume(7).build().hashCode();
		assertThat(hashCode1, not(is(hashCode2)));
	}

	@Test
	public void implementationOfHashCodeShouldBeReproducible() {
		Candle candle = builder.build();
		int hashCode1 = candle.hashCode();
		int hashCode2 = candle.hashCode();
		assertThat(hashCode1, is(hashCode2));
	}

	@Test
	public void implementationOfHashCodeShouldBeConsistent() {
		int hashCode1 = builder.build().hashCode();
		int hashCode2 = builder.build().hashCode();
		assertThat(hashCode1, is(hashCode2));
	}
}
