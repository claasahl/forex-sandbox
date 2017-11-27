package org.github.claasahl.forex.data;

import java.time.*;
import io.reactivex.Observable;

public class DateTimeHelper {
	public static Observable<OffsetDateTime> liveDateTime(OffsetDateTime start, Duration duration) {
		return Observable.empty();
	}

	public static Observable<OffsetDateTime> liveDateTime(OffsetDateTime start) {
		return Observable.empty();
	}

	public static Observable<OffsetDateTime> historicDateTime(OffsetDateTime start, OffsetDateTime end,
			Duration duration) {
		return Observable.<OffsetDateTime, OffsetDateTime>generate(() -> start, (dateTime, emitter) -> {
			emitter.onNext(dateTime);
			return dateTime.plus(duration);
		}).takeWhile(d -> !d.isAfter(end));
	}

	public static Observable<OffsetDateTime> historicDateTime(OffsetDateTime start, OffsetDateTime end) {
		return Observable.empty();
	}
}
