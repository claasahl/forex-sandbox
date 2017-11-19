package org.github.claasahl.forex.function;

import static java.time.ZoneOffset.*;
import java.time.*;
import java.util.List;
import org.github.claasahl.forex.*;
import io.reactivex.*;

public class RateAccumulator implements ObservableTransformer<Rate, Candle> {
	private final Duration duration;

	public RateAccumulator(Duration duration) {
		if (Duration.ZERO.equals(duration) || duration.getNano() != 0)
			throw new IllegalArgumentException();
		this.duration = duration;
	}

	@Override
	public ObservableSource<Candle> apply(Observable<Rate> upstream) {
		Observable<Rate> shared = upstream.publish().autoConnect(2);
		Observable<OffsetDateTime> boundary = shared.map(Rate::getDateTime)
				.distinctUntilChanged(this::toOpenTime)
				.skip(1);
		return shared.buffer(boundary)
				.filter(l -> !l.isEmpty())
				.map(this::accumulate);
	}

	private OffsetDateTime toOpenTime(OffsetDateTime dateTime) {
		long epochSeconds = dateTime.toEpochSecond();
		long durationSeconds = duration.getSeconds();
		// this is an integer division (i.e. it is not loss-free)
		epochSeconds = epochSeconds / durationSeconds * durationSeconds;
		return LocalDateTime.ofEpochSecond(epochSeconds, 0, UTC).atOffset(UTC);
	}

	private Candle accumulate(List<Rate> rates) {
		Rate firstRate = rates.get(0);
		Candle.Builder builder = builder(firstRate);
		for (Rate rate : rates) {
			if (!builder.getSymbol().equals(rate.getSymbol()))
				throw new IllegalArgumentException("'symbol' may not change");
			if (builder.getHigh() < rate.getBid())
				builder.setHigh(rate.getBid());
			if (builder.getLow() > rate.getBid())
				builder.setLow(rate.getBid());
			builder.setClose(rate.getBid());
		}
		return builder.build();
	}

	private Candle.Builder builder(Rate rate) {
		OffsetDateTime dateTime = toOpenTime(rate.getDateTime());
		return new Candle.Builder()
				.setDuration(duration)
				.setSymbol(rate.getSymbol())
				.setOpen(rate.getBid())
				.setHigh(rate.getBid())
				.setLow(rate.getBid())
				.setClose(rate.getBid())
				.setDateTime(dateTime);
	}
}
