package org.github.claasahl.forex.function;

import org.junit.Test;
import io.reactivex.Observable;

public class SimpleMovingAverageTest {
	@Test
	public void shouldBeEmpty() throws InterruptedException {
		SimpleMovingAverage sma = new SimpleMovingAverage(1);
		Observable.<Double>empty()
				.compose(sma)
				.test()
				.await()
				.assertComplete()
				.assertNoValues();
	}

	@Test
	public void shouldCalculateMovingAverageForWindowsOfSizeOne() throws InterruptedException {
		SimpleMovingAverage sma = new SimpleMovingAverage(1);
		Observable.just(1.5, 8.3, 0.7)
				.compose(sma)
				.test()
				.await()
				.assertComplete()
				.assertValues(1.5, 8.3, 0.7);
	}

	@Test
	public void shouldCalculateMovingAverageForWindowsOfSizeTwo() throws InterruptedException {
		SimpleMovingAverage sma = new SimpleMovingAverage(2);
		Observable.just(1, 2, 3, 4, 5)
				.compose(sma)
				.test()
				.await()
				.assertComplete()
				.assertValues(1.5, 2.5, 3.5, 4.5);
	}

	@Test
	public void shouldCalculateMovingAverageForWindowsOfSizeThree() throws InterruptedException {
		SimpleMovingAverage sma = new SimpleMovingAverage(3);
		Observable.just(1, 2, 3, 4, 5)
				.compose(sma)
				.test()
				.await()
				.assertComplete()
				.assertValues(2.0, 3.0, 4.0);
	}
}
