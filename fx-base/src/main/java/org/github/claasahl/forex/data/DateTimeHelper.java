package org.github.claasahl.forex.data;

import java.time.*;
import java.util.concurrent.TimeUnit;
import io.reactivex.*;
import io.reactivex.schedulers.Schedulers;

public class DateTimeHelper {
	public static Observable<OffsetDateTime> live(final OffsetDateTime from, final Duration duration) {
		return live(from, duration, Schedulers.computation());
	}

	public static Observable<OffsetDateTime> live(final OffsetDateTime from, final Duration duration,
			final Scheduler scheduler) {
		return Observable.interval(0, duration.toMillis(), TimeUnit.MILLISECONDS, scheduler)
				.map(multiplicand -> from.plus(duration.multipliedBy(multiplicand)));
	}

	public static Observable<OffsetDateTime> live(final OffsetDateTime from) {
		return Observable.empty();
	}

	public static Observable<OffsetDateTime> historic(final OffsetDateTime from, final Duration duration) {
		return Observable.<OffsetDateTime, OffsetDateTime>generate(() -> from, (dateTime, emitter) -> {
			emitter.onNext(dateTime);
			return dateTime.plus(duration);
		});
	}

	public static Observable<OffsetDateTime> historic(final OffsetDateTime from, final OffsetDateTime to,
			final Duration duration) {
		return historic(from, duration).takeWhile(d -> !d.isAfter(to));
	}
}
