package org.github.claasahl.forex.data;

import java.time.*;
import java.util.concurrent.TimeUnit;
import io.reactivex.*;
import io.reactivex.schedulers.Schedulers;

public class DateTimeHelper {
	public static Observable<OffsetDateTime> live(final Duration duration, final OffsetDateTime from) {
		return live(duration, from, Schedulers.computation());
	}

	public static Observable<OffsetDateTime> live(final Duration duration, final OffsetDateTime from,
			final Scheduler scheduler) {
		return Observable.interval(0, duration.toMillis(), TimeUnit.MILLISECONDS, scheduler)
				.map(multiplicand -> from.plus(duration.multipliedBy(multiplicand)));
	}

	public static Observable<OffsetDateTime> live(final OffsetDateTime from) {
		return Observable.empty();
	}

	public static Observable<OffsetDateTime> historic(final Duration duration, final OffsetDateTime from) {
		return Observable.<OffsetDateTime, OffsetDateTime>generate(() -> from, (dateTime, emitter) -> {
			emitter.onNext(dateTime);
			return dateTime.plus(duration);
		});
	}

	public static Observable<OffsetDateTime> historic(final Duration duration, final OffsetDateTime from,
			final OffsetDateTime to) {
		return historic(duration, from).takeWhile(d -> !d.isAfter(to));
	}
}
