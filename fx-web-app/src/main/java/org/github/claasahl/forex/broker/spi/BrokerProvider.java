package org.github.claasahl.forex.broker.spi;

import java.util.Properties;

public interface BrokerProvider {
	String getName();
	boolean isSupported(Properties configuration);
	Broker instantiate(Properties configuration);
}
