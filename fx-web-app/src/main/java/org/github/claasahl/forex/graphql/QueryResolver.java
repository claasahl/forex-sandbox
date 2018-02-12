package org.github.claasahl.forex.graphql;

import java.util.*;
import org.github.claasahl.forex.model.Broker;
import graphql.schema.DataFetchingEnvironment;

public class QueryResolver {
	public List<Broker> getBrokers(DataFetchingEnvironment environment) {
		return Collections.emptyList();
	}
}
