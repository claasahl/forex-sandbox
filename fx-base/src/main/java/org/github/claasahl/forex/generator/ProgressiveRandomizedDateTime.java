package org.github.claasahl.forex.generator;

import java.time.*;
import java.time.temporal.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.function.*;

public class ProgressiveRandomizedDateTime implements Supplier<OffsetDateTime> {
	private final AtomicReference<OffsetDateTime> dateTime;
	private final Duration duration;
	private final Random random;

	public ProgressiveRandomizedDateTime(OffsetDateTime dateTime, Duration duration) {
		this.dateTime = new AtomicReference<>(dateTime);
		this.duration = duration;
		this.random = new Random();
	}

	public ProgressiveRandomizedDateTime(OffsetDateTime dateTime, Duration duration, long seed) {
		this.dateTime = new AtomicReference<>(dateTime);
		this.duration = duration;
		this.random = new Random(seed);
	}

	@Override
	public OffsetDateTime get() {
		return this.dateTime.getAndUpdate(dT -> {
			long millis = (long) (random.nextDouble() * this.duration.toMillis());
			return dT.plus(millis, ChronoUnit.MILLIS);
		});
	}
}
