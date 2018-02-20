package org.github.claasahl.forex.model;

import java.time.Duration;

public interface Symbol {
	int getId();
	int getBrokerId();
	String getName();
	Duration getDuration();
}
