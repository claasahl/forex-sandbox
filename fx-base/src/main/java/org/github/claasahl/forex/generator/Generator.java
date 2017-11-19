package org.github.claasahl.forex.generator;

import java.time.*;
import java.util.function.*;
import io.reactivex.*;
import io.reactivex.schedulers.*;

public class Generator<V> {
	private final Supplier<Duration> duration;
	private final Predicate<OffsetDateTime> filter;
	private final Function<OffsetDateTime, V> producer;

	public Generator(Supplier<Duration> duration, Function<OffsetDateTime, V> producer) {
		this(duration, dT -> true, producer);
	}

	public Generator(Supplier<Duration> duration, Predicate<OffsetDateTime> filter,
			Function<OffsetDateTime, V> producer) {
		this.duration = duration;
		this.filter = filter;
		this.producer = producer;
	}

	public Observable<V> generate(OffsetDateTime from) {
		return Observable.generate(() -> from, this::increment)
				.filter(filter::test)
				.map(producer::apply)
				.subscribeOn(Schedulers.computation())
				.observeOn(Schedulers.trampoline());
	}
	
	private OffsetDateTime increment(OffsetDateTime dateTime, Emitter<OffsetDateTime> emitter) {
		emitter.onNext(dateTime);
		return dateTime.plus(duration.get());
	}
}
