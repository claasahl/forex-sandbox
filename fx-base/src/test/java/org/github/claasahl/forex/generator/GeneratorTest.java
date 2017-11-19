package org.github.claasahl.forex.generator;

import static org.github.claasahl.forex.util.Helpers.*;
import java.time.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import org.junit.Test;
import io.reactivex.Observable;

public class GeneratorTest {
	@Test
	public void shouldStartWithSpecifiedDate() {
		new Generator<>(() -> M30, Function.identity())
				.generate(midnight())
				.take(1)
				.test()
				.awaitDone(500, TimeUnit.MILLISECONDS)
				.assertValue(midnight())
				.assertComplete();
	}

	@Test
	public void shouldIncrementBy3Hours() {
		new Generator<>(() -> M30, dT -> dT.getMinute() == 0, Function.identity())
				.generate(midnight())
				.skip(3)
				.take(1)
				.test()
				.awaitDone(500, TimeUnit.MILLISECONDS)
				.assertValue(midnight().plus(Duration.ofHours(3)))
				.assertComplete();
	}

	@Test
	public void shouldIncrementBy2Hours() {
		new Generator<>(() -> M30, Function.identity())
				.generate(midnight())
				.skip(4)
				.take(1)
				.test()
				.awaitDone(500, TimeUnit.MILLISECONDS)
				.assertValue(midnight().plus(Duration.ofHours(2)))
				.assertComplete();
	}

	@Test
	public void shouldProduceSameSequence() {
		Observable<OffsetDateTime> sequence = new Generator<>(() -> M30, Function.identity())
				.generate(midnight())
				.take(100);
		Observable.zip(sequence, sequence, (x, y) -> x.equals(y))
				.distinct()
				.test()
				.awaitDone(500, TimeUnit.MILLISECONDS)
				.assertValue(true)
				.assertComplete();
	}

	@Test
	public void shouldProduceNoSequence() {
		new Generator<>(() -> M30, dT -> false, Function.identity())
				.generate(midnight())
				.test()
				.awaitDone(500, TimeUnit.MILLISECONDS)
				.assertTimeout();
	}
}
