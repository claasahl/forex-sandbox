package org.github.claasahl.forex.quality;

import static org.github.claasahl.forex.util.Helpers.*;
import org.github.claasahl.forex.Candle;
import org.junit.Test;
import io.reactivex.Observable;

public class CandleDurationIssueTest {
	@Test
	public void shouldHaveNoIssues() throws InterruptedException {
		Observable<Candle> candles = fiftyCandles(EURUSD, M15, midnight());
		CandleDurationIssue.detect(candles, M15)
				.test()
				.await()
				.assertNoValues();
	}

	@Test
	public void shouldHaveOneIssue() throws InterruptedException {
		Observable<Candle> candles = fiftyCandles(EURUSD, M15, midnight())
				.concatWith(oneCandle(EURUSD, M1, midnightPlus(50, M15)));
		CandleDurationIssue.detect(candles, M15)
				.test()
				.await()
				.assertValueCount(1);
	}

	@Test
	public void shouldHaveTenIssues() throws InterruptedException {
		Observable<Candle> candles = fiftyCandles(EURUSD, M15, midnight())
				.concatWith(tenCandles(EURUSD, M1, midnightPlus(50, M15)));
		CandleDurationIssue.detect(candles, M15)
				.test()
				.await()
				.assertValueCount(10);
	}
}
