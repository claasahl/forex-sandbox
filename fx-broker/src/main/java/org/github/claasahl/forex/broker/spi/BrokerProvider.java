package org.github.claasahl.forex.broker.spi;

import java.util.Properties;
import org.github.claasahl.forex.broker.Broker;

public interface BrokerProvider {
	String getName();
	boolean isSupported(Properties configuration);
	Broker instantiate(Properties configuration);
}
