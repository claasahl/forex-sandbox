package org.github.claasahl.forex.data;

import java.time.*;
import java.util.Random;
import org.github.claasahl.forex.Rate;
import org.github.claasahl.forex.data.spi.RateSeries;
import org.github.claasahl.forex.generator.RandomRates;
import io.reactivex.Observable;

public class RandomRatesProvider implements RateSeries {
	@Override
	public Observable<Rate> rates(String symbol) {
		final RandomRates producer = new RandomRates(symbol);
		final Duration minDuration = Duration.ofSeconds(2);
		final Duration maxDuration = Duration.ofMinutes(2);
		final Random random = new Random();
		final OffsetDateTime from = OffsetDateTime.now();
		return DateTimeHelper.live(minDuration, maxDuration, random, from)
				.map(producer::apply);
	}

	@Override
	public Observable<Rate> rates(String symbol, OffsetDateTime from, OffsetDateTime to) {
		final RandomRates producer = new RandomRates(symbol);
		final Duration minDuration = Duration.ofSeconds(2);
		final Duration maxDuration = Duration.ofMinutes(2);
		final Random random = new Random();
		return DateTimeHelper.historic(minDuration, maxDuration, random, from)
				.map(producer::apply);
	}
}