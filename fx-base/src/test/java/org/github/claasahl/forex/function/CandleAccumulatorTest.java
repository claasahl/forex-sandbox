package org.github.claasahl.forex.function;

import static org.github.claasahl.forex.util.Helpers.*;
import java.time.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.github.claasahl.forex.Candle;
import org.github.claasahl.forex.util.CandleEmitter;
import org.junit.Test;
import io.reactivex.Observable;

public class CandleAccumulatorTest {
	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionInCaseOfInvalidDuration() {
		new CandleAccumulator(Duration.ZERO);
	}

	@Test
	public void shouldBeEmpty() throws InterruptedException {
		Observable.<Candle>empty()
				.compose(new CandleAccumulator(M5))
				.test()
				.await()
				.assertNoValues()
				.assertComplete();
	}

	@Test
	public void symbolShouldBeEurUsd() throws InterruptedException {
		twelveCandles(EURUSD, M1, midnight())
				.compose(new CandleAccumulator(M5))
				.map(Candle::getSymbol)
				.test()
				.await()
				.assertValues(EURUSD, EURUSD, EURUSD)
				.assertComplete();
	}

	@Test
	public void durationShouldBeM5() throws InterruptedException {
		twelveCandles(EURUSD, M1, midnight())
				.compose(new CandleAccumulator(M5))
				.map(Candle::getDuration)
				.test()
				.await()
				.assertValues(M5, M5, M5)
				.assertComplete();
	}

	@Test
	public void dateTimeShouldCorrespondToMidnight() throws InterruptedException {
		twelveCandles(EURUSD, M1, midnight())
				.compose(new CandleAccumulator(M5))
				.map(Candle::getDateTime)
				.test()
				.await()
				.assertValues(midnight(), midnightPlus(1, M5), midnightPlus(2, M5))
				.assertComplete();
	}

	@Test
	public void openPriceShouldCorrespondToFirstPrice() throws InterruptedException {
		twelveCandles(EURUSD, M1, midnight())
				.compose(new CandleAccumulator(M5))
				.map(Candle::getOpen)
				.test()
				.await()
				.assertValues(155d, 178d, 182d)
				.assertComplete();
	}

	@Test
	public void highPriceShouldCorrespondToHighestPrice() throws InterruptedException {
		twelveCandles(EURUSD, M1, midnight())
				.compose(new CandleAccumulator(M5))
				.map(Candle::getHigh)
				.test()
				.await()
				.assertValues(209d, 198d, 195d)
				.assertComplete();
	}

	@Test
	public void lowPriceShouldCorrespondToLowestPrice() throws InterruptedException {
		twelveCandles(EURUSD, M1, midnight())
				.compose(new CandleAccumulator(M5))
				.map(Candle::getLow)
				.test()
				.await()
				.assertValues(155d, 160d, 180d)
				.assertComplete();
	}

	@Test
	public void closePriceShouldCorrespondToLastPrice() throws InterruptedException {
		twelveCandles(EURUSD, M1, midnight())
				.compose(new CandleAccumulator(M5))
				.map(Candle::getClose)
				.test()
				.await()
				.assertValues(180d, 178d, 183d)
				.assertComplete();
	}

	@Test
	public void shouldForwardInputCandles() throws InterruptedException {
		List<Candle> candles = twelveCandles(EURUSD, M1, midnight()).toList().blockingGet();
		twelveCandles(EURUSD, M1, midnight())
				.compose(new CandleAccumulator(M1))
				.test()
				.await()
				.assertValueSequence(candles)
				.assertComplete();
	}
	
	@Test
	public void shouldReturnCandlesWithUtcOffset() throws InterruptedException {
		twelveCandles(EURUSD, M1, midnight().withOffsetSameInstant(ZoneOffset.ofHours(-5)))
				.compose(new CandleAccumulator(M5))
				.map(Candle::getDateTime)
				.test()
				.await()
				.assertValues(midnight(), midnightPlus(1, M5), midnightPlus(2, M5))
				.assertComplete();
	}
	
	@Test
	public void zoneOffsetShouldNotAffectClosePrices() throws InterruptedException {
		twelveCandles(EURUSD, M1, midnight().withOffsetSameInstant(ZoneOffset.ofHours(-9)))
				.compose(new CandleAccumulator(M5))
				.map(Candle::getClose)
				.test()
				.await()
				.assertValues(180d, 178d, 183d)
				.assertComplete();
	}

	@Test
	public void changingSymbolShouldResultInError() throws InterruptedException {
		twelveCandles(EURUSD, M1, midnight())
				.concatWith(oneCandle(GBPUSD, M1, midnightPlus(12, M1)))
				.compose(new CandleAccumulator(M5))
				.test()
				.await()
				.assertError(Throwable.class);
	}

	@Test
	public void changingDurationShouldResultInError() throws InterruptedException {
		twelveCandles(EURUSD, M1, midnight())
				.concatWith(oneCandle(GBPUSD, M5, midnightPlus(12, M1)))
				.compose(new CandleAccumulator(M5))
				.test()
				.await()
				.assertError(Throwable.class);
	}

	@Test
	public void gapShouldBeVisibleInCandles() throws InterruptedException {
		twoCandles(EURUSD, M1, midnight())
				.concatWith(twoCandles(EURUSD, M1, midnightPlus(20, M1)))
				.compose(new CandleAccumulator(M5))
				.map(Candle::getDateTime)
				.test()
				.await()
				.assertValues(midnight(), midnightPlus(4, M5))
				.assertComplete();
	}

	@Test
	public void shouldEmitCandleAsSoonAsPossible() throws InterruptedException {
		twoCandles(EURUSD, M1, midnight())
				.concatWith(twoCandles(EURUSD, M1, midnightPlus(3, M1)))
				.concatWith(Observable.never())
				.compose(new CandleAccumulator(M5))
				.test()
				.awaitDone(500, TimeUnit.MILLISECONDS)
				.assertValueCount(1)
				.assertTimeout();
	}

	private static Observable<Candle> twoCandles(String symbol, Duration duration, OffsetDateTime from) {
		return twelveCandles(symbol, duration, from).take(2);
	}

	private static Observable<Candle> twelveCandles(String symbol, Duration duration, OffsetDateTime from) {
		return Observable.<Candle>create(emitter -> {
			long millis = duration.toMillis();
			CandleEmitter candleEmitter = new CandleEmitter(emitter, symbol, duration, from);
			candleEmitter.onNext(0, 155, 191, 155, 187);
			candleEmitter.onNext(millis, 194, 197, 190, 190);
			candleEmitter.onNext(millis, 209, 209, 177, 179);
			candleEmitter.onNext(millis, 182, 198, 179, 179);
			candleEmitter.onNext(millis, 177, 197, 177, 180);
			candleEmitter.onNext(millis, 178, 198, 178, 178);
			candleEmitter.onNext(millis, 188, 188, 188, 188);
			candleEmitter.onNext(millis, 160, 175, 160, 170);
			candleEmitter.onNext(millis, 171, 174, 170, 172);
			candleEmitter.onNext(millis, 172, 180, 172, 178);
			candleEmitter.onNext(2 * millis, 182, 190, 181, 189);
			candleEmitter.onNext(millis, 189, 195, 180, 183);
			candleEmitter.onComplete();
		});
	}
}
