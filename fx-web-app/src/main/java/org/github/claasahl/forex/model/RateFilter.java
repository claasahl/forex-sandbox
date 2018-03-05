package org.github.claasahl.forex.model;

import java.util.Map;

public class RateFilter {
	private final Integer brokerId;
	private final String symbol;

	public RateFilter(Integer brokerId, String symbol) {
		this.brokerId = brokerId;
		this.symbol = symbol;
	}

	public Integer getBrokerId() {
		return brokerId;
	}

	public String getSymbol() {
		return symbol;
	}

	public static RateFilter fromMap(Map<String, Object> map) {
		if(map == null)
			return null;
		Integer brokerId = getField(map, "brokerId");
		String symbol = (String) map.get("symbol");
		return new RateFilter(brokerId, symbol);
	}
	
	private static Integer getField(Map<String, Object> map, String field) {
		if(map.get(field) != null) {
			return Integer.valueOf((String) map.get(field));
		}
		return null;
	}
}
