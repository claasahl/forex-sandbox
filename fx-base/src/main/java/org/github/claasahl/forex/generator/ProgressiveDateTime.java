package org.github.claasahl.forex.generator;

import java.time.*;
import java.util.concurrent.atomic.*;
import java.util.function.*;

public class ProgressiveDateTime implements Supplier<OffsetDateTime> {
	private final AtomicReference<OffsetDateTime> dateTime;
	private final Duration duration;

	public ProgressiveDateTime(OffsetDateTime dateTime, Duration duration) {
		this.dateTime = new AtomicReference<>(dateTime);
		this.duration = duration;
	}

	@Override
	public OffsetDateTime get() {
		return this.dateTime.getAndUpdate(dT -> dT.plus(this.duration));
	}
}
