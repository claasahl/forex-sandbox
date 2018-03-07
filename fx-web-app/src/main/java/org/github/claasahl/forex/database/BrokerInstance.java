package org.github.claasahl.forex.database;

import java.util.*;
import java.util.Map.Entry;
import org.github.claasahl.forex.broker.Candle;

public class BrokerInstance {
	private final String id;
	private final String name;
	private final String providerName;
	private final Properties properties;

	/**
	 * Creates a {@link Candle} with the specified builder.
	 * 
	 * @param builder
	 *            the builder with which the candle is constructed
	 */
	BrokerInstance(Builder builder) {
		id = builder.id;
		name = builder.name;
		providerName = builder.providerName;
		properties = new Properties();
		for (Entry<Object, Object> entry : builder.properties.entrySet()) {
			properties.put(entry.getKey(), entry.getValue());
		}
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getProviderName() {
		return providerName;
	}

	public Properties getConfiguration() {
		return properties;
	}

	/**
	 * Returns a {@link Builder} that has been initialized with this candle.
	 * 
	 * @return a {@link Builder}
	 */
	public Builder toBuilder() {
		return new Builder()
				.setId(id)
				.setName(name)
				.setProviderName(providerName)
				.setConfiguration(properties);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, providerName, properties);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof BrokerInstance))
			return false;
		BrokerInstance other = (BrokerInstance) obj;
		return Objects.equals(id, other.id)
				&& Objects.equals(name, other.name)
				&& Objects.equals(providerName, other.providerName)
				&& Objects.equals(properties, other.properties);
	}

	@Override
	public String toString() {
		return "BrokerInstance [id=" + id + ", name=" + name + ", providerName=" + providerName + ", properties="
				+ properties + "]";
	}

	/**
	 * The interface <code>Builder</code>. It represents ...
	 * 
	 * This interface is part of the <i>builder</i> design pattern. It provides
	 * builder-functions for implementations of the {@link Candle} interface.
	 *
	 * @author Claas Ahlrichs
	 *
	 */
	public static final class Builder {
		private String id;
		private String name;
		private String providerName;
		private Properties properties;

		/**
		 * Creates a {@link Builder} that has been initialized with default values.
		 * <ul>
		 * <li>id = null</li>
		 * <li>name = null</li>
		 * <li>providerName = null</li>
		 * <li>properties = empty properties</li>
		 * <ul>
		 */
		public Builder() {
			properties = new Properties();
		}

		public String getId() {
			return id;
		}

		public Builder setId(String id) {
			this.id = id;
			return this;
		}

		public String getName() {
			return name;
		}

		public Builder setName(String name) {
			this.name = name;
			return this;
		}

		public String getProviderName() {
			return providerName;
		}

		public Builder setProviderName(String providerName) {
			this.providerName = providerName;
			return this;
		}

		public Properties getConfiguration() {
			return properties;
		}

		public Builder setConfiguration(Properties properties) {
			this.properties = properties;
			return this;
		}

		/**
		 * Builds the broker instance with the specified parameters.
		 * 
		 * TODO this should also ensure that the constructed candle is valid
		 * 
		 * @return the broker instance with the specified parameters
		 */
		public BrokerInstance build() {
			return new BrokerInstance(this);
		}
	}
}
