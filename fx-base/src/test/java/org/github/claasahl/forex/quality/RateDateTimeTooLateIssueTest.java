package org.github.claasahl.forex.quality;

import static org.github.claasahl.forex.util.Helpers.*;
import org.github.claasahl.forex.Rate;
import org.junit.Test;
import io.reactivex.Observable;

public class RateDateTimeTooLateIssueTest {
	@Test
	public void shouldHaveNoIssues() throws InterruptedException {
		Observable<Rate> rates = fiftyRates(EURUSD, M1, midnight());
		RateDateTimeTooLateIssue.detect(rates, midnightPlus(50, M1))
				.test()
				.await()
				.assertNoValues();
	}

	@Test
	public void shouldHaveOneIssue() throws InterruptedException {
		Observable<Rate> rates = fiftyRates(EURUSD, M1, midnight());
		RateDateTimeTooLateIssue.detect(rates, midnightPlus(49, M1))
				.test()
				.await()
				.assertValueCount(1);
	}

	@Test
	public void shouldHaveTenIssues() throws InterruptedException {
		Observable<Rate> rates = fiftyRates(EURUSD, M1, midnight());
		RateDateTimeTooLateIssue.detect(rates, midnightPlus(40, M1))
				.test()
				.await()
				.assertValueCount(10);
	}
}
