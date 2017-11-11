package org.github.claasahl.forex.quality;

import static org.github.claasahl.forex.util.Helpers.*;
import org.github.claasahl.forex.Rate;
import org.junit.Test;
import io.reactivex.Observable;

public class RateDateTimeTooEarlyIssueTest {
	@Test
	public void shouldHaveNoIssues() throws InterruptedException {
		Observable<Rate> rates = fiftyRates(EURUSD, M15, midnight());
		RateDateTimeTooEarlyIssue.detect(rates, midnight())
				.test()
				.await()
				.assertNoValues();
	}

	@Test
	public void shouldHaveOneIssue() throws InterruptedException {
		Observable<Rate> rates = fiftyRates(EURUSD, M15, midnightMinus(1, M15));
		RateDateTimeTooEarlyIssue.detect(rates, midnight())
				.test()
				.await()
				.assertValueCount(1);
	}

	@Test
	public void shouldHaveTenIssues() throws InterruptedException {
		Observable<Rate> rates = fiftyRates(EURUSD, M15, midnightMinus(10, M15));
		RateDateTimeTooEarlyIssue.detect(rates, midnight())
				.test()
				.await()
				.assertValueCount(10);
	}
}
