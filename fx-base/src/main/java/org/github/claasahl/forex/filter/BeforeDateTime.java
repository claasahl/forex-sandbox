package org.github.claasahl.forex.filter;

import java.time.OffsetDateTime;
import java.util.function.*;

public class BeforeDateTime<T> implements Predicate<T> {
	private final OffsetDateTime dateTime;
	private final Function<T, OffsetDateTime> accessor;

	public BeforeDateTime(OffsetDateTime dateTime, Function<T, OffsetDateTime> accessor) {
		this.dateTime = dateTime;
		this.accessor = accessor;
	}

	public OffsetDateTime getDateTime() {
		return dateTime;
	}

	@Override
	public boolean test(T t) {
		return accessor.apply(t).isBefore(dateTime);
	}
}
