package org.github.claasahl.forex.quality;

import static org.github.claasahl.forex.util.Helpers.*;
import org.github.claasahl.forex.Candle;
import org.junit.Test;
import io.reactivex.Observable;

public class CandleStagnatingDateTimeIssueTest {
	@Test
	public void shouldHaveNoIssues() throws InterruptedException {
		Observable<Candle> candles = fiftyCandles(EURUSD, M15, midnight());
		CandleStagnatingDateTimeIssue.detect(candles)
				.test()
				.await()
				.assertNoValues();
	}

	@Test
	public void shouldHaveOneIssue() throws InterruptedException {
		Observable<Candle> candles = fiftyCandles(EURUSD, M15, midnight())
				.concatWith(oneCandle(EURUSD, M15, midnightPlus(49, M15)));
		CandleStagnatingDateTimeIssue.detect(candles)
				.test()
				.await()
				.assertValueCount(1);
	}

	@Test
	public void shouldHaveThreeIssues() throws InterruptedException {
		Observable<Candle> candles = tenCandles(EURUSD, M15, midnight())
				.concatWith(tenCandles(EURUSD, M15, midnightPlus(9, M15)))
				.concatWith(tenCandles(EURUSD, M15, midnightPlus(18, M15)))
				.concatWith(tenCandles(EURUSD, M15, midnightPlus(27, M15)));
		CandleStagnatingDateTimeIssue.detect(candles)
				.test()
				.await()
				.assertValueCount(3);
	}
}
