package org.github.claasahl.forex.quality;

import static org.github.claasahl.forex.util.Helpers.*;
import org.github.claasahl.forex.Rate;
import org.junit.Test;
import io.reactivex.Observable;

public class RateSymbolIssueTest {
	@Test
	public void shouldHaveNoIssues() throws InterruptedException {
		Observable<Rate> rates = fiftyRates(EURUSD, M15, midnight());
		RateSymbolIssue.detect(rates, EURUSD)
				.test()
				.await()
				.assertNoValues();
	}

	@Test
	public void shouldHaveOneIssue() throws InterruptedException {
		Observable<Rate> rates = fiftyRates(EURUSD, M15, midnight())
				.concatWith(oneRate(GBPUSD, M15, midnightPlus(50, M15)));
		RateSymbolIssue.detect(rates, EURUSD)
				.test()
				.await()
				.assertValueCount(1);
	}

	@Test
	public void shouldHaveTenIssues() throws InterruptedException {
		Observable<Rate> rates = fiftyRates(EURUSD, M15, midnight())
				.concatWith(tenRates(GBPUSD, M15, midnightPlus(50, M15)));
		RateSymbolIssue.detect(rates, EURUSD)
				.test()
				.await()
				.assertValueCount(10);
	}
}
