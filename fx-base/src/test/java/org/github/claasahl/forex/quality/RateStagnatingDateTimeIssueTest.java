package org.github.claasahl.forex.quality;

import static org.github.claasahl.forex.util.Helpers.*;
import org.github.claasahl.forex.Rate;
import org.junit.Test;
import io.reactivex.Observable;

public class RateStagnatingDateTimeIssueTest {
	@Test
	public void shouldHaveNoIssues() throws InterruptedException {
		Observable<Rate> rates = fiftyRates(EURUSD, M15, midnight());
		RateStagnatingDateTimeIssue.detect(rates)
				.test()
				.await()
				.assertNoValues();
	}

	@Test
	public void shouldHaveOneIssue() throws InterruptedException {
		Observable<Rate> rates = fiftyRates(EURUSD, M15, midnight())
				.concatWith(oneRate(EURUSD, M15, midnightPlus(49, M15)));
		RateStagnatingDateTimeIssue.detect(rates)
				.test()
				.await()
				.assertValueCount(1);
	}

	@Test
	public void shouldHaveThreeIssues() throws InterruptedException {
		Observable<Rate> rates = tenRates(EURUSD, M15, midnight())
				.concatWith(tenRates(EURUSD, M15, midnightPlus(9, M15)))
				.concatWith(tenRates(EURUSD, M15, midnightPlus(18, M15)))
				.concatWith(tenRates(EURUSD, M15, midnightPlus(27, M15)));
		RateStagnatingDateTimeIssue.detect(rates)
				.test()
				.await()
				.assertValueCount(3);
	}
}
