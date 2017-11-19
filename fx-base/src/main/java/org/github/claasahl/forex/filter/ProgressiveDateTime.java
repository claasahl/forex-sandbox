package org.github.claasahl.forex.filter;

import java.time.OffsetDateTime;
import java.util.function.*;

public class ProgressiveDateTime<T> implements BiPredicate<T, T> {
	private final Function<T, OffsetDateTime> accessor;

	public ProgressiveDateTime(Function<T, OffsetDateTime> accessor) {
		this.accessor = accessor;
	}

	@Override
	public boolean test(T t, T u) {
		OffsetDateTime first = accessor.apply(t);
		OffsetDateTime second = accessor.apply(u);
		return first.isBefore(second);
	}
}
