package org.github.claasahl.forex.data;

import java.time.*;
import java.util.concurrent.TimeUnit;
import io.reactivex.*;
import io.reactivex.schedulers.Schedulers;

public class DateTimeHelper {
	public static Observable<OffsetDateTime> live(final OffsetDateTime start, final Duration duration) {
		return live(start, duration, Schedulers.computation());
	}

	public static Observable<OffsetDateTime> live(final OffsetDateTime start, final Duration duration,
			final Scheduler scheduler) {
		return Observable.interval(0, duration.toMillis(), TimeUnit.MILLISECONDS, scheduler)
				.map(multiplicand -> start.plus(duration.multipliedBy(multiplicand)));
	}

	public static Observable<OffsetDateTime> live(final OffsetDateTime start) {
		return Observable.empty();
	}

	public static Observable<OffsetDateTime> historic(final OffsetDateTime start, final Duration duration) {
		return Observable.<OffsetDateTime, OffsetDateTime>generate(() -> start, (dateTime, emitter) -> {
			emitter.onNext(dateTime);
			return dateTime.plus(duration);
		});
	}

	public static Observable<OffsetDateTime> historic(final OffsetDateTime start, final OffsetDateTime end,
			final Duration duration) {
		return historic(start, duration).takeWhile(d -> !d.isAfter(end));
	}
}
