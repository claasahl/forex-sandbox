package org.github.claasahl.forex;

import static org.github.claasahl.forex.util.Helpers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import org.junit.*;

public class RateTest {
	private Rate.Builder builder;

	@Before
	public void before() {
		builder = new Rate.Builder()
				.setSymbol(GBPUSD)
				.setDateTime(midnight())
				.setBid(6)
				.setAsk(8);
	}
	
	@Test
	public void shouldComeBefore() {
		Rate rateA = builder.setDateTime(midnight().plusHours(1)).build();
		Rate rateB = builder.setDateTime(midnight().plusHours(12)).build();
		assertThat(rateA.compareTo(rateB), lessThan(0));
	}
	
	@Test
	public void shouldBeSame() {
		Rate rateA = builder.setDateTime(midnight().plusHours(4)).build();
		Rate rateB = builder.setDateTime(midnight().plusHours(4)).build();
		assertThat(rateA.compareTo(rateB), is(0));
	}
	
	@Test
	public void shouldComeAfter() {
		Rate rateA = builder.setDateTime(midnight().plusHours(12)).build();
		Rate rateB = builder.setDateTime(midnight().plusHours(1)).build();
		assertThat(rateA.compareTo(rateB), greaterThan(0));
	}
	
	@Test
	public void shouldDetermineSpread() {
		Rate rate = builder.build();
		assertThat(rate.getSpread(), is(-2d));
	}

	@Test
	public void implementationOfEqualsShouldHandleDifferentSymbol() {
		Rate rateA = builder.build();
		Rate rateB = builder.setSymbol(EURUSD).build();
		assertThat(rateA.equals(rateB), is(false));
	}

	@Test
	public void implementationOfEqualsShouldHandleDifferentDateTime() {
		Rate rateA = builder.build();
		Rate rateB = builder.setDateTime(midnightMinus(1, D1)).build();
		assertThat(rateA.equals(rateB), is(false));
	}

	@Test
	public void implementationOfEqualsShouldHandleDifferentBid() {
		Rate rateA = builder.build();
		Rate rateB = builder.setBid(5).build();
		assertThat(rateA.equals(rateB), is(false));
	}

	@Test
	public void implementationOfEqualsShouldHandleDifferentAsk() {
		Rate rateA = builder.build();
		Rate rateB = builder.setAsk(9).build();
		assertThat(rateA.equals(rateB), is(false));
	}

	@Test
	public void implementationOfEqualsShouldBeReflexive() {
		Rate rate = builder.build();
		assertThat(rate.equals(rate), is(true));
	}

	@Test
	public void implementationOfEqualsShouldBeSymmetric() {
		Rate rateA = builder.build();
		Rate rateB = builder.build();
		assertThat(rateA.equals(rateB), is(true));
		assertThat(rateB.equals(rateA), is(true));
	}

	@Test
	public void implementationOfEqualsShouldBeTransitive() {
		Rate rateA = builder.build();
		Rate rateB = builder.build();
		Rate rateC = builder.build();
		assertThat(rateA.equals(rateB), is(true));
		assertThat(rateB.equals(rateC), is(true));
		assertThat(rateA.equals(rateC), is(true));
	}

	@Test
	public void implementationOfHashCodeShouldHandleDifferentSymbol() {
		int hashCode1 = builder.build().hashCode();
		int hashCode2 = builder.setSymbol(EURUSD).build().hashCode();
		assertThat(hashCode1, not(is(hashCode2)));
	}

	@Test
	public void implementationOfHashCodeShouldHandleDifferentDateTime() {
		int hashCode1 = builder.build().hashCode();
		int hashCode2 = builder.setDateTime(midnightMinus(1, D1)).build().hashCode();
		assertThat(hashCode1, not(is(hashCode2)));
	}

	@Test
	public void implementationOfHashCodeShouldHandleDifferentBid() {
		int hashCode1 = builder.build().hashCode();
		int hashCode2 = builder.setBid(5).build().hashCode();
		assertThat(hashCode1, not(is(hashCode2)));
	}

	@Test
	public void implementationOfHashCodeShouldHandleDifferentAsk() {
		int hashCode1 = builder.build().hashCode();
		int hashCode2 = builder.setAsk(9).build().hashCode();
		assertThat(hashCode1, not(is(hashCode2)));
	}

	@Test
	public void implementationOfHashCodeShouldBeReproducible() {
		Rate rate = builder.build();
		int hashCode1 = rate.hashCode();
		int hashCode2 = rate.hashCode();
		assertThat(hashCode1, is(hashCode2));
	}

	@Test
	public void implementationOfHashCodeShouldBeConsistent() {
		int hashCode1 = builder.build().hashCode();
		int hashCode2 = builder.build().hashCode();
		assertThat(hashCode1, is(hashCode2));
	}
}
