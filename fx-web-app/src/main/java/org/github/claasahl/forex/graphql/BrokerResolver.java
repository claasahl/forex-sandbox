package org.github.claasahl.forex.graphql;

import java.util.*;
import org.github.claasahl.forex.model.*;
import graphql.schema.DataFetchingEnvironment;

public class BrokerResolver {
	List<Symbol> getSymbols(DataFetchingEnvironment environment) {
		Broker broker = environment.getSource();
		return Collections.emptyList();
	}
}
