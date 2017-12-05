package org.github.claasahl.forex.data;

import java.time.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import io.reactivex.*;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.*;

public class DateTimeHelper {
	public static Observable<OffsetDateTime> live(final Duration duration, final OffsetDateTime from) {
		return live(duration, from, Schedulers.computation());
	}

	public static Observable<OffsetDateTime> live(final Duration duration, final OffsetDateTime from,
			final Scheduler scheduler) {
		return Observable.interval(0, duration.toMillis(), TimeUnit.MILLISECONDS, scheduler)
				.map(multiplicand -> from.plus(duration.multipliedBy(multiplicand)));
	}

	public static Observable<OffsetDateTime> live(final Duration minDuration, final Duration maxDuration,
			final Random random, final OffsetDateTime from) {
		return live(minDuration, maxDuration, random, from, Schedulers.computation());
	}

	public static Observable<OffsetDateTime> live(final Duration minDuration, final Duration maxDuration,
			final Random random, final OffsetDateTime from, Scheduler scheduler) {
		final int variation = (int) (maxDuration.toMillis() - minDuration.toMillis());
		Subject<Duration> delay = BehaviorSubject.createDefault(Duration.ZERO);
		return delay.concatMap(duration -> wait(duration, scheduler).doOnComplete(() -> {
			Duration millis = Duration.ofMillis(random.nextInt(variation));
			delay.onNext(minDuration.plus(millis));
		})).scan(Duration.ZERO, Duration::plus)
				.map(duration -> from.plus(duration))
				.distinctUntilChanged();
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

	public static Observable<OffsetDateTime> historic(final Duration minDuration, final Duration maxDuration,
			final Random random, final OffsetDateTime from) {
		final int variation = (int) (maxDuration.toMillis() - minDuration.toMillis());
		return Observable.<OffsetDateTime, OffsetDateTime>generate(() -> from, (dateTime, emitter) -> {
			emitter.onNext(dateTime);
			Duration millis = Duration.ofMillis(random.nextInt(variation));
			return dateTime.plus(minDuration.plus(millis));
		});
	}

	public static Observable<OffsetDateTime> historic(final Duration minDuration, final Duration maxDuration,
			final Random random, final OffsetDateTime from, final OffsetDateTime to) {
		return historic(minDuration, maxDuration, random, from).takeWhile(d -> !d.isAfter(to));
	}

	private static Observable<Duration> wait(Duration duration, Scheduler scheduler) {
		return Observable.timer(duration.toMillis(), TimeUnit.MILLISECONDS, scheduler).map(ignored -> duration);
	}
}
