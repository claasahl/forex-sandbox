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

public class QualityMonitoredBrokerCandlesTest {
	private QualityMonitoredBroker broker;
	private BehaviorSubject<Candle> candles;
	private TestObserver<String> issues;

	@Before
	public void before() {
		candles = BehaviorSubject.create();
		Broker broker = mock(Broker.class);
		when(broker.candles(anyString(), any())).thenReturn(candles);
		when(broker.candles(anyString(), any(), any(), any())).thenReturn(candles);
		this.broker = new QualityMonitoredBroker(broker);
		issues = this.broker.issues().map(Issue::getDescription).test();
	}

	// ---------------
	// offline candles
	// ---------------

	@Test
	public void emptyResultShouldBeValidResultForOfflineCandles() throws InterruptedException {
		request(broker.candles(EURUSD, M15, midnightMinus(10, M15), midnight()));
		emit(Observable.empty());
		issues.assertNoValues();
	}

	@Test
	public void shouldBeValidResultForOfflineCandles() throws InterruptedException {
		request(broker.candles(EURUSD, M15, midnightMinus(10, M15), midnight()));
		emit(tenCandles(EURUSD, M15, midnightMinus(10, M15)));
		issues.assertNoValues();
	}

	@Test
	public void offlineCandlesMustHaveDistinctDateTime() throws InterruptedException {
		request(broker.candles(EURUSD, M15, midnightMinus(11, M15), midnight()));
		emit(tenCandles(EURUSD, M15, midnightMinus(11, M15))
				.concatWith(oneCandle(EURUSD, M15, midnightMinus(2, M15)))
				.concatWith(oneCandle(EURUSD, M15, midnightMinus(1, M15))));
		issues.assertValueCount(1)
				.assertValue("Expected 'dateTime' to be ascending");
	}

	@Test
	public void offlineCandlesMustHaveSpecifiedSymbol() throws InterruptedException {
		request(broker.candles(EURUSD, M15, midnightMinus(11, M15), midnight()));
		emit(tenCandles(EURUSD, M15, midnightMinus(11, M15))
				.concatWith(oneCandle(GBPUSD, M15, midnightMinus(1, M15))));
		issues.assertValueCount(1)
				.assertValue("Expected 'symbol' to be EURUSD");
	}

	@Test
	public void offlineCandlesMustHaveSpecifiedDuration() throws InterruptedException {
		request(broker.candles(EURUSD, M15, midnightMinus(11, M15), midnight()));
		emit(tenCandles(EURUSD, M15, midnightMinus(11, M15))
				.concatWith(oneCandle(EURUSD, M5, midnightMinus(1, M15))));
		issues.assertValueCount(1)
				.assertValue("Expected 'duration' to be PT15M");
	}

	@Test
	public void offlineCandlesMustNotBeBeforeSpecifiedTimePeriod() throws InterruptedException {
		request(broker.candles(EURUSD, M15, midnightMinus(20, M15), midnight()));
		emit(tenCandles(EURUSD, M15, midnightMinus(21, M15)));
		issues.assertValueCount(1)
				.assertValue("Expected 'dateTime' to be after " + midnightMinus(20, M15) + " (incl.)");
	}

	@Test
	public void offlineCandleMayBeOnLowerEndOfSpecifiedTimePeriod() throws InterruptedException {
		request(broker.candles(EURUSD, M15, midnightMinus(20, M15), midnight()));
		emit(tenCandles(EURUSD, M15, midnightMinus(20, M15)));
		issues.assertNoValues();
	}

	@Test
	public void offlineCandleMustNotBeOnUpperEndOfSpecifiedTimePeriod() throws InterruptedException {
		request(broker.candles(EURUSD, M15, midnightMinus(20, M15), midnight()));
		emit(tenCandles(EURUSD, M15, midnight()));
		issues.assertValueCount(10)
				.assertValueAt(9, s -> ("Expected 'dateTime' to be before " + midnight() + " (excl.)").equals(s));
	}

	@Test
	public void offlineCandlesMustNotBeAfterSpecifiedTimePeriod() throws InterruptedException {
		request(broker.candles(EURUSD, M15, midnightMinus(20, M15), midnight()));
		emit(tenCandles(EURUSD, M15, midnightPlus(1, M15)));
		issues.assertValueCount(10)
				.assertValueAt(3, s -> ("Expected 'dateTime' to be before " + midnight() + " (excl.)").equals(s));
	}

	@Test
	public void offlineCandlesDateTimeMustIncrease() throws InterruptedException {
		request(broker.candles(EURUSD, M15, midnightMinus(20, M15), midnight()));
		emit(tenCandles(EURUSD, M15, midnightMinus(15, M15))
				.concatWith(tenCandles(EURUSD, M15, midnightMinus(15, M15))));
		issues.assertValueCount(1)
				.assertValue("Expected 'dateTime' to be ascending");
	}

	@Test
	public void offlineCandlesMustHaveConsistentHighPrice() throws InterruptedException {
		request(broker.candles(EURUSD, M15, midnightMinus(20, M15), midnight()));
		emit(tenCandles(EURUSD, M15, midnightMinus(15, M15))
				.concatWith(oneCandle(EURUSD, M15, midnightMinus(5, M15))
						.map(c -> c.toBuilder().setHigh(c.getOpen()).setOpen(c.getHigh()).build())));
		issues.assertValueCount(1)
				.assertValue("Expected 'high' price to be the highest price");
	}

	@Test
	public void offlineCandlesMustHaveConsistentLowPrice() throws InterruptedException {
		request(broker.candles(EURUSD, M15, midnightMinus(20, M15), midnight()));
		emit(tenCandles(EURUSD, M15, midnightMinus(15, M15))
				.concatWith(oneCandle(EURUSD, M15, midnightMinus(5, M15))
						.map(c -> c.toBuilder().setLow(c.getOpen()).setOpen(c.getLow()).build())));
		issues.assertValueCount(1)
				.assertValue("Expected 'low' price to be the lowest price");
	}

	// ------------
	// live candles
	// ------------

	@Test
	public void emptyResultShouldBeValidResultForLiveCandles() throws InterruptedException {
		request(broker.candles(EURUSD, M1));
		emit(Observable.empty());
		issues.assertNoValues();
	}

	@Test
	public void shouldBeValidResultForLiveCandles() throws InterruptedException {
		request(broker.candles(EURUSD, M1));
		emit(oneCandle(EURUSD, M1, OffsetDateTime.now()));
		issues.assertNoValues();
	}

	@Test
	public void liveCandlesMustHaveDistinctDateTime() throws InterruptedException {
		request(broker.candles(EURUSD, M1));
		OffsetDateTime now = OffsetDateTime.now();
		emit(oneCandle(EURUSD, M1, now)
				.concatWith(oneCandle(EURUSD, M1, now)));
		issues.assertValueCount(1)
				.assertValue("Expected 'dateTime' to be ascending");
	}

	@Test
	public void liveCandlesMustHaveSpecifiedSymbol() throws InterruptedException {
		request(broker.candles(EURUSD, M1));
		emit(oneCandle(GBPUSD, M1, OffsetDateTime.now()));
		issues.assertValueCount(1)
				.assertValue("Expected 'symbol' to be EURUSD");
	}

	@Test
	public void liveCandlesMustHaveSpecifiedDuration() throws InterruptedException {
		request(broker.candles(EURUSD, M1));
		emit(oneCandle(EURUSD, M15, OffsetDateTime.now()));
		issues.assertValueCount(1)
				.assertValue("Expected 'duration' to be PT1M");
	}

	@Test
	public void liveCandlesMustNotBeOlderThan30Seconds() throws InterruptedException {
		request(broker.candles(EURUSD, M1));
		emit(oneCandle(EURUSD, M1, OffsetDateTime.now().minus(Duration.ofMillis(30500))));
		issues.assertValueCount(1)
				.assertValue("Expected 'dateTime' to be within 30 sec. of actual time");
	}

	@Test
	public void liveCandleMayBe30SecondsOld() throws InterruptedException {
		request(broker.candles(EURUSD, M1));
		emit(oneCandle(EURUSD, M1, OffsetDateTime.now().minus(Duration.ofMillis(29500))));
		issues.assertNoValues();
	}

	@Test
	public void liveCandleMayBe30SecondsInTheFuture() throws InterruptedException {
		request(broker.candles(EURUSD, M1));
		emit(oneCandle(EURUSD, M1, OffsetDateTime.now().plus(Duration.ofMillis(29500))));
		issues.assertNoValues();
	}

	@Test
	public void liveCandlesMustNotBeMoreThan30SecondsInTheFuture() throws InterruptedException {
		request(broker.candles(EURUSD, M1));
		emit(oneCandle(EURUSD, M1, OffsetDateTime.now().plus(Duration.ofMillis(30500))));
		issues.assertValueCount(1)
				.assertValue("Expected 'dateTime' to be within 30 sec. of actual time");
	}

	@Test
	public void liveCandlesDateTimeMustIncrease() throws InterruptedException {
		request(broker.candles(EURUSD, M1));
		emit(oneCandle(EURUSD, M1, OffsetDateTime.now().plusSeconds(10))
				.concatWith(oneCandle(EURUSD, M1, OffsetDateTime.now())));
		issues.assertValueCount(1)
				.assertValue("Expected 'dateTime' to be ascending");
	}

	@Test
	public void liveCandlesMustHaveConsistentHighPrice() throws InterruptedException {
		request(broker.candles(EURUSD, M1));
		emit(oneCandle(EURUSD, M1, OffsetDateTime.now())
				.map(c -> c.toBuilder().setHigh(c.getOpen()).setOpen(c.getHigh()).build()));
		issues.assertValueCount(1)
				.assertValue("Expected 'high' price to be the highest price");
	}

	@Test
	public void liveCandlesMustHaveConsistentLowPrice() throws InterruptedException {
		request(broker.candles(EURUSD, M1));
		emit(oneCandle(EURUSD, M1, OffsetDateTime.now())
				.map(c -> c.toBuilder().setLow(c.getOpen()).setOpen(c.getLow()).build()));
		issues.assertValueCount(1)
				.assertValue("Expected 'low' price to be the lowest price");
	}

	private void request(Observable<Candle> data) {
		data.subscribe();
	}

	private void emit(Observable<Candle> data) {
		data.doOnNext(candles::onNext)
				.doOnComplete(candles::onComplete)
				.doOnError(candles::onError)
				.blockingSubscribe();
	}
}
