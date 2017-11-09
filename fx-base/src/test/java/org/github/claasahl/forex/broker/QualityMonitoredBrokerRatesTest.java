package org.github.claasahl.forex.broker;

import static org.github.claasahl.forex.util.Helpers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import java.time.*;
import org.github.claasahl.forex.*;
import org.github.claasahl.forex.broker.QualityMonitoredBroker;
import org.github.claasahl.forex.quality.Issue;
import org.junit.*;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import io.reactivex.subjects.BehaviorSubject;

public class QualityMonitoredBrokerRatesTest {
	private QualityMonitoredBroker broker;
	private BehaviorSubject<Rate> rates;
	private TestObserver<String> issues;

	@Before
	public void before() {
		rates = BehaviorSubject.create();
		Broker broker = mock(Broker.class);
		when(broker.rates(anyString())).thenReturn(rates);
		when(broker.rates(anyString(), any(), any())).thenReturn(rates);
		this.broker = new QualityMonitoredBroker(broker);
		issues = this.broker.issues().map(Issue::getDescription).test();
	}

	// ---------------
	// offline rates
	// ---------------

	@Test
	public void emptyResultShouldBeValidResultForOfflineRates() throws InterruptedException {
		request(broker.rates(EURUSD, midnightMinus(10, M15), midnight()));
		emit(Observable.empty());
		issues.assertNoValues();
	}

	@Test
	public void shouldBeValidResultForOfflineRates() throws InterruptedException {
		request(broker.rates(EURUSD, midnightMinus(10, M15), midnight()));
		emit(tenRates(EURUSD, M15, midnightMinus(10, M15)));
		issues.assertNoValues();
	}

	@Test
	public void offlineRatesMustHaveDistinctDateTime() throws InterruptedException {
		request(broker.rates(EURUSD, midnightMinus(11, M15), midnight()));
		emit(tenRates(EURUSD, M15, midnightMinus(11, M15))
				.concatWith(oneRate(EURUSD, M15, midnightMinus(2, M15)))
				.concatWith(oneRate(EURUSD, M15, midnightMinus(1, M15))));
		issues.assertValueCount(1)
				.assertValue("Expected 'dateTime' to be ascending");
	}

	@Test
	public void offlineRatesMustHaveSpecifiedSymbol() throws InterruptedException {
		request(broker.rates(EURUSD, midnightMinus(11, M15), midnight()));
		emit(tenRates(EURUSD, M15, midnightMinus(11, M15))
				.concatWith(oneRate(GBPUSD, M15, midnightMinus(1, M15))));
		issues.assertValueCount(1)
				.assertValue("Expected 'symbol' to be EURUSD");
	}

	@Test
	public void offlineRatesMustNotBeBeforeSpecifiedTimePeriod() throws InterruptedException {
		request(broker.rates(EURUSD, midnightMinus(20, M15), midnight()));
		emit(tenRates(EURUSD, M15, midnightMinus(21, M15)));
		issues.assertValueCount(1)
				.assertValue("Expected 'dateTime' to be after " + midnightMinus(20, M15) + " (incl.)");
	}

	@Test
	public void offlineRateMayBeOnLowerEndOfSpecifiedTimePeriod() throws InterruptedException {
		request(broker.rates(EURUSD, midnightMinus(20, M15), midnight()));
		emit(tenRates(EURUSD, M15, midnightMinus(20, M15)));
		issues.assertNoValues();
	}

	@Test
	public void offlineRateMustNotBeOnUpperEndOfSpecifiedTimePeriod() throws InterruptedException {
		request(broker.rates(EURUSD, midnightMinus(20, M15), midnight()));
		emit(tenRates(EURUSD, M15, midnight()));
		issues.assertValueCount(10)
				.assertValueAt(9, s -> ("Expected 'dateTime' to be before " + midnight() + " (excl.)").equals(s));
	}

	@Test
	public void offlineRatesMustNotBeAfterSpecifiedTimePeriod() throws InterruptedException {
		request(broker.rates(EURUSD, midnightMinus(20, M15), midnight()));
		emit(tenRates(EURUSD, M15, midnightPlus(1, M15)));
		issues.assertValueCount(10)
				.assertValueAt(3, s -> ("Expected 'dateTime' to be before " + midnight() + " (excl.)").equals(s));
	}

	@Test
	public void offlineRatesDateTimeMustIncrease() throws InterruptedException {
		request(broker.rates(EURUSD, midnightMinus(20, M15), midnight()));
		emit(tenRates(EURUSD, M15, midnightMinus(15, M15))
				.concatWith(tenRates(EURUSD, M15, midnightMinus(15, M15))));
		issues.assertValueCount(1)
				.assertValue("Expected 'dateTime' to be ascending");
	}

	// ------------
	// live rates
	// ------------

	@Test
	public void emptyResultShouldBeValidResultForLiveRates() throws InterruptedException {
		request(broker.rates(EURUSD));
		emit(Observable.empty());
		issues.assertNoValues();
	}

	@Test
	public void shouldBeValidResultForLiveRates() throws InterruptedException {
		request(broker.rates(EURUSD));
		emit(oneRate(EURUSD, M1, OffsetDateTime.now()));
		issues.assertNoValues();
	}

	@Test
	public void liveRatesMustHaveDistinctDateTime() throws InterruptedException {
		request(broker.rates(EURUSD));
		OffsetDateTime now = OffsetDateTime.now();
		emit(oneRate(EURUSD, M1, now)
				.concatWith(oneRate(EURUSD, M1, now)));
		issues.assertValueCount(1)
				.assertValue("Expected 'dateTime' to be ascending");
	}

	@Test
	public void liveRatesMustHaveSpecifiedSymbol() throws InterruptedException {
		request(broker.rates(EURUSD));
		emit(oneRate(GBPUSD, M1, OffsetDateTime.now()));
		issues.assertValueCount(1)
				.assertValue("Expected 'symbol' to be EURUSD");
	}

	@Test
	public void liveRatesMustNotBeOlderThan30Seconds() throws InterruptedException {
		request(broker.rates(EURUSD));
		emit(oneRate(EURUSD, M1, OffsetDateTime.now().minus(Duration.ofMillis(30500))));
		issues.assertValueCount(1)
				.assertValue("Expected 'dateTime' to be within 30 sec. of actual time");
	}

	@Test
	public void liveRateMayBe30SecondsOld() throws InterruptedException {
		request(broker.rates(EURUSD));
		emit(oneRate(EURUSD, M1, OffsetDateTime.now().minus(Duration.ofMillis(29500))));
		issues.assertNoValues();
	}

	@Test
	public void liveRateMayBe30SecondsInTheFuture() throws InterruptedException {
		request(broker.rates(EURUSD));
		emit(oneRate(EURUSD, M1, OffsetDateTime.now().plus(Duration.ofMillis(29500))));
		issues.assertNoValues();
	}

	@Test
	public void liveRatesMustNotBeMoreThan30SecondsInTheFuture() throws InterruptedException {
		request(broker.rates(EURUSD));
		emit(oneRate(EURUSD, M1, OffsetDateTime.now().plus(Duration.ofMillis(30500))));
		issues.assertValueCount(1)
				.assertValue("Expected 'dateTime' to be within 30 sec. of actual time");
	}

	@Test
	public void liveRatesDateTimeMustIncrease() throws InterruptedException {
		request(broker.rates(EURUSD));
		emit(oneRate(EURUSD, M1, OffsetDateTime.now().plusSeconds(10))
				.concatWith(oneRate(EURUSD, M1, OffsetDateTime.now())));
		issues.assertValueCount(1)
				.assertValue("Expected 'dateTime' to be ascending");
	}

	private TestObserver<Rate> request(Observable<Rate> data) {
		return data.test();
	}

	private void emit(Observable<Rate> data) {
		data.doOnNext(rates::onNext)
				.doOnComplete(rates::onComplete)
				.doOnError(rates::onError)
				.blockingSubscribe();
	}
}