package org.github.claasahl.forex.graphql;

import java.time.Duration;
import org.github.claasahl.forex.database.*;

class GqlSymbol {
	private final InternalSymbol symbol;

	public GqlSymbol(InternalSymbol symbol) {
		this.symbol = symbol;
	}

	public InternalSymbol getSymbol() {
		return symbol;
	}

	protected String getId() {
		return symbol.getId();
	}

	protected String getName() {
		return symbol.getName();
	}
	
	protected Duration getDuration() {
		return symbol.getDuration();
	}
}
