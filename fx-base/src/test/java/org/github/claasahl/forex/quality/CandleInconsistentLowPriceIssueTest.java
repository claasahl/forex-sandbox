package org.github.claasahl.forex.quality;

import static org.github.claasahl.forex.util.Helpers.*;
import org.github.claasahl.forex.*;
import org.junit.Test;
import io.reactivex.Observable;

public class CandleInconsistentLowPriceIssueTest {
	@Test
	public void shouldHaveNoIssues() throws InterruptedException {
		Observable<Candle> candles = fiftyCandles(EURUSD, M15, midnight());
		CandleInconsistentLowPriceIssue.detect(candles)
				.test()
				.await()
				.assertNoValues();
	}

	@Test
	public void shouldHaveOneIssue() throws InterruptedException {
		Observable<Candle> candles = oneCandle(EURUSD, M15, midnight())
				.map(c -> c.toBuilder().setLow(c.getHigh()).setHigh(c.getLow()).build());
		CandleInconsistentLowPriceIssue.detect(candles)
				.test()
				.await()
				.assertValueCount(1);
	}

	@Test
	public void shouldHaveTenIssues() throws InterruptedException {
		Observable<Candle> candles = tenCandles(EURUSD, M15, midnight())
				.map(c -> c.toBuilder().setLow(c.getOpen()).setOpen(c.getLow()).build());
		CandleInconsistentLowPriceIssue.detect(candles)
				.test()
				.await()
				.assertValueCount(10);
	}
	
	@Test
	public void shouldHaveFiftyIssues() throws InterruptedException {
		Observable<Candle> candles = fiftyCandles(EURUSD, M15, midnight())
				.map(c -> c.toBuilder().setLow(c.getClose()).setClose(c.getLow()).build());
		CandleInconsistentLowPriceIssue.detect(candles)
				.test()
				.await()
				.assertValueCount(50);
	}
}
