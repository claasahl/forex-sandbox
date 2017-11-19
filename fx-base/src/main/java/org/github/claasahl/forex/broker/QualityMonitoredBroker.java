package org.github.claasahl.forex.broker;

import java.time.*;
import org.github.claasahl.forex.*;
import org.github.claasahl.forex.quality.*;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class QualityMonitoredBroker extends DecoratedBroker {
	private final BehaviorSubject<Issue<?>> issues;

	public QualityMonitoredBroker(Broker broker) {
		super(broker);
		this.issues = BehaviorSubject.create();
	}

	public Observable<Issue<?>> issues() {
		return this.issues;
	}

	@Override
	public Observable<Candle> candles(String symbol, Duration duration) {
		Observable<Candle> candles = getBroker().candles(symbol, duration)
				.publish()
				.autoConnect(7);
		Observable<Observable<? extends Issue<Candle>>> detectors = Observable.just(
				CandleSymbolIssue.detect(candles, symbol), CandleDurationIssue.detect(candles, duration),
				CandleLiveDateTimeIssue.detect(candles, Duration.ofSeconds(30)),
				CandleStagnatingDateTimeIssue.detect(candles), CandleInconsistentHighPriceIssue.detect(candles),
				CandleInconsistentLowPriceIssue.detect(candles));
		Observable.merge(detectors)
				.subscribe(this.issues::onNext);
		return candles;
	}

	@Override
	public Observable<Candle> candles(String symbol, Duration duration, OffsetDateTime from, OffsetDateTime to) {
		Observable<Candle> candles = getBroker().candles(symbol, duration, from, to)
				.publish()
				.autoConnect(8);
		Observable<Observable<? extends Issue<Candle>>> detectors = Observable.just(
				CandleSymbolIssue.detect(candles, symbol), CandleDurationIssue.detect(candles, duration),
				CandleDateTimeTooEarlyIssue.detect(candles, from), CandleDateTimeTooLateIssue.detect(candles, to),
				CandleStagnatingDateTimeIssue.detect(candles), CandleInconsistentHighPriceIssue.detect(candles),
				CandleInconsistentLowPriceIssue.detect(candles));
		Observable.merge(detectors)
				.subscribe(this.issues::onNext);
		return candles;
	}

	@Override
	public Observable<Rate> rates(String symbol) {
		Observable<Rate> rates = getBroker().rates(symbol)
				.publish()
				.autoConnect(4);
		Observable<Observable<? extends Issue<Rate>>> detectors = Observable.just(RateSymbolIssue.detect(rates, symbol),
				RateLiveDateTimeIssue.detect(rates, Duration.ofSeconds(30)), RateStagnatingDateTimeIssue.detect(rates));
		Observable.merge(detectors)
				.subscribe(this.issues::onNext);
		return rates;
	}

	@Override
	public Observable<Rate> rates(String symbol, OffsetDateTime from, OffsetDateTime to) {
		Observable<Rate> rates = getBroker().rates(symbol, from, to)
				.publish()
				.autoConnect(5);
		Observable<Observable<? extends Issue<Rate>>> detectors = Observable.just(RateSymbolIssue.detect(rates, symbol),
				RateDateTimeTooEarlyIssue.detect(rates, from), RateDateTimeTooLateIssue.detect(rates, to),
				RateStagnatingDateTimeIssue.detect(rates));
		Observable.merge(detectors)
				.subscribe(this.issues::onNext);
		return rates;
	}
}
