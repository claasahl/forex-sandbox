package org.github.claasahl.forex.database;

import java.time.Duration;

public class Symbol {
	private final String id;
	private final String brokerId;
	private final String name;
	private final Duration duration;

	public Symbol(int id, int brokerId, String name, Duration duration) {
		this.id = id + "";
		this.brokerId = brokerId + "";
		this.name = name;
		this.duration = duration;
	}

	public String getId() {
		return id;
	}

	public String getBrokerId() {
		return brokerId;
	}

	public String getName() {
		return name;
	}

	public Duration getDuration() {
		return duration;
	}
}
