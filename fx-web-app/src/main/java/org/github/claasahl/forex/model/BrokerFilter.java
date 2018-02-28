package org.github.claasahl.forex.model;

import java.util.Map;

public class BrokerFilter {
	private final Integer brokerId;

	public BrokerFilter(Integer brokerId) {
		this.brokerId = brokerId;
	}

	public Integer getBrokerId() {
		return brokerId;
	}

	public static BrokerFilter fromMap(Map<String, Object> map) {
		if(map == null)
			return null;
		Integer brokerId = getField(map, "brokerId");
		return new BrokerFilter(brokerId);
	}
	
	private static Integer getField(Map<String, Object> map, String field) {
		if(map.get(field) != null) {
			return Integer.valueOf((String) map.get(field));
		}
		return null;
	}
}
