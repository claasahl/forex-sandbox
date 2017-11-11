package org.github.claasahl.forex.quality;

import static org.github.claasahl.forex.util.Helpers.*;
import org.github.claasahl.forex.Candle;
import org.junit.Test;
import io.reactivex.Observable;

public class CandleSymbolIssueTest {
	@Test
	public void shouldHaveNoIssues() throws InterruptedException {
		Observable<Candle> candles = fiftyCandles(EURUSD, M15, midnight());
		CandleSymbolIssue.detect(candles, EURUSD)
				.test()
				.await()
				.assertNoValues();
	}

	@Test
	public void shouldHaveOneIssue() throws InterruptedException {
		Observable<Candle> candles = fiftyCandles(EURUSD, M15, midnight())
				.concatWith(oneCandle(GBPUSD, M15, midnightPlus(50, M15)));
		CandleSymbolIssue.detect(candles, EURUSD)
				.test()
				.await()
				.assertValueCount(1);
	}

	@Test
	public void shouldHaveTenIssues() throws InterruptedException {
		Observable<Candle> candles = fiftyCandles(EURUSD, M15, midnight())
				.concatWith(tenCandles(GBPUSD, M15, midnightPlus(50, M15)));
		CandleSymbolIssue.detect(candles, EURUSD)
				.test()
				.await()
				.assertValueCount(10);
	}
}
