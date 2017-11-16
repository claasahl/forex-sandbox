package org.github.claasahl.forex.playground;

import java.time.*;
import org.github.claasahl.forex.*;
import io.reactivex.Observable;

public class ThreeDucks {
	public static void main(String[] args) {
		Observable<Candle> source = candles("EURUSD", Duration.ofHours(1), 10).doOnNext(System.out::println).publish()
				.autoConnect(4);
		Observable<SimpleMovingAverage> duck1 = source.scan(new SimpleMovingAverage(Candle::getClose, 2),
				SimpleMovingAverage::update);
		Observable<SimpleMovingAverage> duck2 = source.scan(new SimpleMovingAverage(Candle::getClose, 5),
				SimpleMovingAverage::update);
		Observable<SimpleMovingAverage> duck3 = source.scan(new SimpleMovingAverage(Candle::getClose, 10),
				SimpleMovingAverage::update);
		Observable.combineLatest(source, duck1, duck2, duck3, ThreeDucks::threeDucks)
				.doOnNext(System.out::println)
				.subscribe();
	}

	private static Observable<Candle> candles(String symbol, Duration duration, int count) {
		Candle.Builder candleBuilder = new Candle.Builder()
				.setSymbol(symbol)
				.setDuration(duration);
		Observable<Candle.Builder> builder = Observable.just(candleBuilder);
		Observable<Duration> candleDuration = Observable.<Duration>just(duration);
		Observable<Integer> range = Observable.range(1, count);
		return Observable.combineLatest(builder, candleDuration, range, ThreeDucks::buildCandle);
	}

	private static Candle buildCandle(Candle.Builder builder, Duration duration, int number) {
		int epochSeconds = (int) (duration.toMillis() * number / 1000);
		OffsetDateTime dateTime = LocalDateTime.ofEpochSecond(epochSeconds, epochSeconds, ZoneOffset.UTC)
				.atOffset(ZoneOffset.UTC);
		return builder.setOpen(number + 2d).setHigh(number + 3d).setLow(number).setClose(number + 1d).setDateTime(dateTime)
				.build();
	}

	private static String threeDucks(Candle candle, SimpleMovingAverage duck1, SimpleMovingAverage duck2,
			SimpleMovingAverage duck3) {
		System.out.format("C:%.2f D1:%.2f D2:%.2f D3:%.2f%n", candle.getClose(), duck1.getValue(), duck2.getValue(),
				duck3.getValue());
		double referencePrice = candle.getClose();
		if (referencePrice > duck1.getValue() && referencePrice > duck2.getValue()
				&& referencePrice > duck3.getValue()) {
			return "BUY";
		} else if (referencePrice < duck1.getValue() && referencePrice < duck2.getValue()
				&& referencePrice < duck3.getValue()) {
			return "SELL";
		}
		return "ABORT/CANCLE";
	}
}
