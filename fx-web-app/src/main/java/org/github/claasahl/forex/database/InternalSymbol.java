package org.github.claasahl.forex.database;

import java.time.Duration;
import org.github.claasahl.forex.model.Symbol;

class InternalSymbol implements Symbol {
	private final int id;
	private final String name;
	private final Duration duration;

	public InternalSymbol(int id, String name, Duration duration) {
		this.id = id;
		this.name = name;
		this.duration = duration;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Duration getDuration() {
		return duration;
	}
}
