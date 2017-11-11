package org.github.claasahl.forex.quality;

import static org.github.claasahl.forex.util.Helpers.*;
import org.github.claasahl.forex.Rate;
import org.junit.Test;
import io.reactivex.Observable;

public class RateLiveDateTimeIssueTest {
	@Test
	public void shouldHaveNoIssues() throws InterruptedException {
		Observable<Rate> rates = fiftyRates(EURUSD, M1, nowMinus(25, M1));
		RateLiveDateTimeIssue.detect(rates, M30)
				.test()
				.await()
				.assertNoValues();
	}

	@Test
	public void shouldHaveOneIssue() throws InterruptedException {
		Observable<Rate> rates = fiftyRates(EURUSD, M1, nowMinus(30, M1));
		RateLiveDateTimeIssue.detect(rates, M30)
				.test()
				.await()
				.assertValueCount(1);
	}

	@Test
	public void shouldHaveTwentyIssues() throws InterruptedException {
		Observable<Rate> rates = fiftyRates(EURUSD, M1, nowMinus(25, M1));
		RateLiveDateTimeIssue.detect(rates, M15)
				.test()
				.await()
				.assertValueCount(20);
	}
}
