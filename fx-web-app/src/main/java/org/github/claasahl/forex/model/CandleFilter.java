package org.github.claasahl.forex.model;

import java.time.Duration;
import java.util.Map;

public class CandleFilter {
	private final Integer brokerId;
	private final String symbol;
	private final Duration duration;

	public CandleFilter(Integer brokerId, String symbol, Duration duration) {
		this.brokerId = brokerId;
		this.symbol = symbol;
		this.duration = duration;
	}

	public Integer getBrokerId() {
		return brokerId;
	}

	public String getSymbol() {
		return symbol;
	}

	public Duration getDuration() {
		return duration;
	}

	public static CandleFilter fromMap(Map<String, Object> map) {
		if(map == null)
			return null;
		Integer brokerId = getField(map, "brokerId");
		String symbol = (String) map.get("symbol");
		Duration duration = (Duration) map.get("duration");
		return new CandleFilter(brokerId, symbol, duration);
	}
	
	private static Integer getField(Map<String, Object> map, String field) {
		if(map.get(field) != null) {
			return Integer.valueOf((String) map.get(field));
		}
		return null;
	}
}
