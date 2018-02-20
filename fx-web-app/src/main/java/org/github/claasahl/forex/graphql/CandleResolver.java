package org.github.claasahl.forex.graphql;

import org.github.claasahl.forex.database.SymbolRepository;
import org.github.claasahl.forex.model.*;
import graphql.schema.DataFetchingEnvironment;

public class CandleResolver {
private final SymbolRepository symbolRepository;
	
	public CandleResolver(SymbolRepository symbolRepository) {
		this.symbolRepository = symbolRepository;
	}
	
	public Symbol getSymbol(DataFetchingEnvironment environment) {
		Candle candle = environment.getSource();
		return symbolRepository.getSymbolForId(candle.getSymbolId());
	}
}
