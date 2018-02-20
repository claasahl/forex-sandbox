package org.github.claasahl.forex.graphql;

import org.github.claasahl.forex.database.SymbolRepository;
import org.github.claasahl.forex.model.*;
import graphql.schema.DataFetchingEnvironment;

class RateResolver {
	private final SymbolRepository symbolRepository;
	
	public RateResolver(SymbolRepository symbolRepository) {
		this.symbolRepository = symbolRepository;
	}
	
	public Symbol getSymbol(DataFetchingEnvironment environment) {
		Rate rate = environment.getSource();
		return symbolRepository.getSymbolForId(rate.getSymbolId());
	}
	
	public double getSpread(DataFetchingEnvironment environment) {
		Rate rate = environment.getSource();
		return rate.getSpread();
	}
}
