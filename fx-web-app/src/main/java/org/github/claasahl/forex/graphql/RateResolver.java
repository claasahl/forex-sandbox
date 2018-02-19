package org.github.claasahl.forex.graphql;

import org.github.claasahl.forex.model.Rate;
import graphql.schema.DataFetchingEnvironment;

public class RateResolver {
	public double getSpread(DataFetchingEnvironment environment) {
		Rate rate = environment.getSource();
		return rate.getSpread();
	}
}
