package org.github.claasahl.forex.util;

import java.time.*;
import java.time.temporal.ChronoUnit;
import org.github.claasahl.forex.*;
import org.github.claasahl.forex.generator.*;
import io.reactivex.Observable;

public final class Helpers {
	public static final Duration M1 = Duration.ofMinutes(1);
	public static final Duration M5 = Duration.ofMinutes(5);
	public static final Duration M15 = Duration.ofMinutes(15);
	public static final Duration M30 = Duration.ofMinutes(30);
	public static final Duration H1 = Duration.ofHours(1);
	public static final Duration H3 = Duration.ofHours(3);
	public static final Duration H6 = Duration.ofHours(6);
	public static final Duration D1 = Duration.ofDays(1);
	public static final String EURUSD = "EURUSD";
	public static final String GBPUSD = "GBPUSD";

	private Helpers() {
		// not meant to be instantiated
	}

	public static OffsetDateTime midnight() {
		return OffsetDateTime.now()
				.withOffsetSameInstant(ZoneOffset.UTC)
				.truncatedTo(ChronoUnit.DAYS);
	}

	public static OffsetDateTime midnightMinus(int multiplicand, Duration duration) {
		return midnight().minus(duration.multipliedBy(multiplicand));
	}

	public static OffsetDateTime midnightPlus(int multiplicand, Duration duration) {
		return midnight().plus(duration.multipliedBy(multiplicand));
	}

	public static OffsetDateTime now() {
		return OffsetDateTime.now()
				.withOffsetSameInstant(ZoneOffset.UTC)
				.truncatedTo(ChronoUnit.MINUTES);
	}

	public static OffsetDateTime nowMinus(int multiplicand, Duration duration) {
		return now().minus(duration.multipliedBy(multiplicand));
	}

	public static OffsetDateTime nowPlus(int multiplicand, Duration duration) {
		return now().plus(duration.multipliedBy(multiplicand));
	}

	private static Observable<Candle> randomCandles(String symbol, Duration duration, OffsetDateTime from) {
		return new Generator<>(() -> duration, new RandomCandles(symbol, duration)).generate(from);
	}

	public static Observable<Candle> oneCandle(String symbol, Duration duration, OffsetDateTime from) {
		return randomCandles(symbol, duration, from).take(1);
	}

	public static Observable<Candle> tenCandles(String symbol, Duration duration, OffsetDateTime from) {
		return randomCandles(symbol, duration, from).take(10);
	}

	public static Observable<Candle> fiftyCandles(String symbol, Duration duration, OffsetDateTime from) {
		return randomCandles(symbol, duration, from).take(50);
	}
	
	private static Observable<Rate> randomRates(String symbol, Duration duration, OffsetDateTime from) {
		return new Generator<>(() -> duration, new RandomRates(symbol)).generate(from);
	}

	public static Observable<Rate> oneRate(String symbol, Duration duration, OffsetDateTime from) {
		return randomRates(symbol, duration, from).take(1);
	}

	public static Observable<Rate> tenRates(String symbol, Duration duration, OffsetDateTime from) {
		return randomRates(symbol, duration, from).take(10);
	}

	public static Observable<Rate> fiftyRates(String symbol, Duration duration, OffsetDateTime from) {
		return randomRates(symbol, duration, from).take(50);
	}
}
