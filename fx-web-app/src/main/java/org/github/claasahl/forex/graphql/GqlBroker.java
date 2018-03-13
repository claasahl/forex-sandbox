package org.github.claasahl.forex.graphql;

import org.github.claasahl.forex.database.BrokerInstance;

/**
 * The class {@link GqlBroker} is exposed through GraphQL. It wraps internal
 * objects and (re-)shapes them as described in the GraphQL schema. This class
 * also adds an extra layer of abstraction which makes it more difficult to leak
 * (i.e. unintentionally expose) details about the wrapped internal objects.
 * 
 * @author Claas Ahlrichs
 *
 */
class GqlBroker {
	private final BrokerInstance brokerInstance;

	protected GqlBroker(BrokerInstance brokerInstance) {
		this.brokerInstance = brokerInstance;
	}

	/**
	 * Returns this broker's id.
	 * 
	 * @see BrokerInstance#getId()
	 * @return this broker's id
	 */
	protected String getId() {
		return brokerInstance.getId();
	}

	/**
	 * Returns this broker's name.
	 * 
	 * @see BrokerInstance#getName()
	 * @return this broker's name
	 */
	protected String getName() {
		return brokerInstance.getName();
	}
}
