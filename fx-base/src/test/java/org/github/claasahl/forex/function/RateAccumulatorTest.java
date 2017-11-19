package org.github.claasahl.forex.function;

import static org.github.claasahl.forex.util.Helpers.*;
import java.time.*;
import org.github.claasahl.forex.*;
import org.github.claasahl.forex.util.RateEmitter;
import org.junit.*;
import io.reactivex.Observable;

public class RateAccumulatorTest {
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowExceptionInCaseOfInvalidDuration() {
		new RateAccumulator(Duration.ZERO);
	}

	@Test
	public void shouldBeEmpty() throws InterruptedException {
		Observable.<Rate>empty()
				.compose(new RateAccumulator(M5))
				.test()
				.await()
				.assertNoValues()
				.assertComplete();
	}

	@Test
	public void symbolShouldBeEurUsd() throws InterruptedException {
		twelveRates(EURUSD, M1, midnight())
				.compose(new RateAccumulator(M5))
				.map(Candle::getSymbol)
				.test()
				.await()
				.assertValues(EURUSD, EURUSD, EURUSD)
				.assertComplete();
	}

	@Test
	public void durationShouldBeM5() throws InterruptedException {
		twelveRates(EURUSD, M1, midnight())
				.compose(new RateAccumulator(M5))
				.map(Candle::getDuration)
				.test()
				.await()
				.assertValues(M5, M5, M5)
				.assertComplete();
	}

	@Test
	public void dateTimeShouldCorrespondToMidnight() throws InterruptedException {
		twelveRates(EURUSD, M1, midnight())
				.compose(new RateAccumulator(M5))
				.map(Candle::getDateTime)
				.test()
				.await()
				.assertValues(midnight(), midnightPlus(1, M5), midnightPlus(2, M5))
				.assertComplete();
	}

	@Test
	public void openPriceShouldCorrespondToFirstPrice() throws InterruptedException {
		twelveRates(EURUSD, M1, midnight())
				.compose(new RateAccumulator(M5))
				.map(Candle::getOpen)
				.test()
				.await()
				.assertValues(10.0, 11.25, 11.0)
				.assertComplete();
	}

	@Test
	public void highPriceShouldCorrespondToHighestPrice() throws InterruptedException {
		twelveRates(EURUSD, M1, midnight())
				.compose(new RateAccumulator(M5))
				.map(Candle::getHigh)
				.test()
				.await()
				.assertValues(11.00, 11.75, 11.0)
				.assertComplete();
	}

	@Test
	public void lowPriceShouldCorrespondToLowestPrice() throws InterruptedException {
		twelveRates(EURUSD, M1, midnight())
				.compose(new RateAccumulator(M5))
				.map(Candle::getLow)
				.test()
				.await()
				.assertValues(10.0, 11.25, 10.75)
				.assertComplete();
	}

	@Test
	public void closePriceShouldCorrespondToLastPrice() throws InterruptedException {
		twelveRates(EURUSD, M1, midnight())
				.compose(new RateAccumulator(M5))
				.map(Candle::getClose)
				.test()
				.await()
				.assertValues(11.00, 11.25, 10.75)
				.assertComplete();
	}

	@Test
	public void changingSymbolShouldResultInError() throws InterruptedException {
		twelveRates(EURUSD, M1, midnight())
				.concatWith(oneRate(GBPUSD, M1, midnightPlus(12, M1)))
				.compose(new RateAccumulator(M5))
				.test()
				.await()
				.assertError(Throwable.class);
	}

	@Test
	public void gapShouldBeVisibleInCandles() throws InterruptedException {
		twoRates(EURUSD, M1, midnight())
				.concatWith(twoRates(EURUSD, M1, midnightPlus(20, M1)))
				.compose(new RateAccumulator(M5))
				.map(Candle::getDateTime)
				.test()
				.await()
				.assertValues(midnight(), midnightPlus(4, M5))
				.assertComplete();
	}
	
	@Test
	public void shouldReturnCandlesWithUtcOffset() throws InterruptedException {
		twelveRates(EURUSD, M1, midnight().withOffsetSameInstant(ZoneOffset.ofHours(2)))
				.compose(new RateAccumulator(M5))
				.map(Candle::getDateTime)
				.test()
				.await()
				.assertValues(midnight(), midnightPlus(1, M5), midnightPlus(2, M5))
				.assertComplete();
	}
	
	@Test
	public void zoneOffsetShouldNotAffectClosePrices() throws InterruptedException {
		twelveRates(EURUSD, M1, midnight().withOffsetSameInstant(ZoneOffset.ofHours(-4)))
				.compose(new RateAccumulator(M5))
				.map(Candle::getClose)
				.test()
				.await()
				.assertValues(11.00, 11.25, 10.75)
				.assertComplete();
	}

	private static Observable<Rate> twoRates(String symbol, Duration duration, OffsetDateTime from) {
		return twelveRates(symbol, duration, from).take(2);
	}

	private static Observable<Rate> twelveRates(String symbol, Duration duration, OffsetDateTime from) {
		return Observable.<Rate>create(emitter -> {
			long millis = duration.toMillis();
			RateEmitter rateEmitter = new RateEmitter(emitter, symbol, from);
			rateEmitter.onNext(0, 10.0, Double.NaN);
			rateEmitter.onNext(millis, 10.25, Double.NaN);
			rateEmitter.onNext(millis, 10.5, Double.NaN);
			rateEmitter.onNext(millis, 10.75, Double.NaN);
			rateEmitter.onNext(millis, 11.0, Double.NaN);

			rateEmitter.onNext(millis, 11.25, Double.NaN);
			rateEmitter.onNext(millis, 11.5, Double.NaN);
			rateEmitter.onNext(millis, 11.75, Double.NaN);
			rateEmitter.onNext(millis, 11.5, Double.NaN);
			rateEmitter.onNext(millis, 11.25, Double.NaN);

			rateEmitter.onNext(2 * millis, 11.0, Double.NaN);
			rateEmitter.onNext(millis, 10.75, Double.NaN);
			rateEmitter.onComplete();
		});
	}
}
