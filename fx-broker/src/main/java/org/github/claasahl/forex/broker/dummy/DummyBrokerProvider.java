package org.github.claasahl.forex.broker.dummy;

import java.util.Properties;
import org.github.claasahl.forex.broker.Broker;
import org.github.claasahl.forex.broker.spi.BrokerProvider;

public class DummyBrokerProvider implements BrokerProvider {

	@Override
	public String getName() {
		return "dummy provider";
	}

	@Override
	public boolean isSupported(Properties configuration) {
		return true;
	}

	@Override
	public Broker instantiate(Properties configuration) {
		return new DummyBroker();
	}

}
