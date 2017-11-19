package org.github.claasahl.forex.filter;

import java.time.Duration;
import java.util.function.*;

public class FixedDuration<T> implements Predicate<T> {
	private final Duration duration;
	private final Function<T, Duration> accessor;

	public FixedDuration(Duration duration, Function<T, Duration> accessor) {
		this.duration = duration;
		this.accessor = accessor;
	}

	public Duration getDuration() {
		return duration;
	}

	@Override
	public boolean test(T t) {
		return accessor.apply(t).equals(duration);
	}
}
