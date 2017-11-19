package org.github.claasahl.forex.filter;

import java.time.*;
import java.util.function.*;

public class LiveDateTime<T> implements Predicate<T> {
	private final Duration tolerableDifference;
	private final Function<T, OffsetDateTime> accessor;

	public LiveDateTime(Duration tolerableDifference, Function<T, OffsetDateTime> accessor) {
		this.tolerableDifference = tolerableDifference;
		this.accessor = accessor;
	}

	public Duration getTolerableDifference() {
		return tolerableDifference;
	}

	@Override
	public boolean test(T t) {
		OffsetDateTime now = OffsetDateTime.now();
		OffsetDateTime lower = now.minus(tolerableDifference);
		OffsetDateTime upper = now.plus(tolerableDifference);
		OffsetDateTime dateTime = accessor.apply(t);
		return dateTime.isAfter(lower) && dateTime.isBefore(upper);
	}
}
