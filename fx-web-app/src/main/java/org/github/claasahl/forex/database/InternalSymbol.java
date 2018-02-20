package org.github.claasahl.forex.database;

import java.time.Duration;
import org.github.claasahl.forex.model.*;

class InternalSymbol implements Symbol {
	private final int id;
	private final int brokerId;
	private final String name;
	private final Duration duration;

	public InternalSymbol(int id, int brokerId, String name, Duration duration) {
		this.id = id;
		this.brokerId = brokerId;
		this.name = name;
		this.duration = duration;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public int getBrokerId() {
		return brokerId;
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
