package org.github.claasahl.forex.quality;

import static org.github.claasahl.forex.util.Helpers.*;
import org.github.claasahl.forex.Candle;
import org.junit.Test;
import io.reactivex.Observable;

public class CandleDateTimeTooEarlyIssueTest {
	@Test
	public void shouldHaveNoIssues() throws InterruptedException {
		Observable<Candle> candles = fiftyCandles(EURUSD, M15, midnight());
		CandleDateTimeTooEarlyIssue.detect(candles, midnight())
				.test()
				.await()
				.assertNoValues();
	}

	@Test
	public void shouldHaveOneIssue() throws InterruptedException {
		Observable<Candle> candles = fiftyCandles(EURUSD, M15, midnightMinus(1, M15));
		CandleDateTimeTooEarlyIssue.detect(candles, midnight())
				.test()
				.await()
				.assertValueCount(1);
	}

	@Test
	public void shouldHaveTenIssues() throws InterruptedException {
		Observable<Candle> candles = fiftyCandles(EURUSD, M15, midnightMinus(10, M15));
		CandleDateTimeTooEarlyIssue.detect(candles, midnight())
				.test()
				.await()
				.assertValueCount(10);
	}
}
