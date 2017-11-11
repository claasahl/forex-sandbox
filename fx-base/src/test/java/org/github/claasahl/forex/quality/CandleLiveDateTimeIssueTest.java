package org.github.claasahl.forex.quality;

import static org.github.claasahl.forex.util.Helpers.*;
import org.github.claasahl.forex.Candle;
import org.junit.Test;
import io.reactivex.Observable;

public class CandleLiveDateTimeIssueTest {
	@Test
	public void shouldHaveNoIssues() throws InterruptedException {
		Observable<Candle> candles = fiftyCandles(EURUSD, M1, nowMinus(25, M1));
		CandleLiveDateTimeIssue.detect(candles, M30)
				.test()
				.await()
				.assertNoValues();
	}

	@Test
	public void shouldHaveOneIssue() throws InterruptedException {
		Observable<Candle> candles = fiftyCandles(EURUSD, M1, nowMinus(30, M1));
		CandleLiveDateTimeIssue.detect(candles, M30)
				.test()
				.await()
				.assertValueCount(1);
	}

	@Test
	public void shouldHaveTwentyIssues() throws InterruptedException {
		Observable<Candle> candles = fiftyCandles(EURUSD, M1, nowMinus(24, M1));
		CandleLiveDateTimeIssue.detect(candles, M15)
				.test()
				.await()
				.assertValueCount(20);
	}
}
