package org.github.claasahl.forex.database;

public class BrokerInstanceFilter {
	private final String brokerId;

	public BrokerInstanceFilter(String brokerId) {
		this.brokerId = brokerId;
	}

	public String getBrokerId() {
		return brokerId;
	}
}
