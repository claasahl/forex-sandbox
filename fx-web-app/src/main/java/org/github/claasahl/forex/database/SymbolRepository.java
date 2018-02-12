package org.github.claasahl.forex.database;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import org.github.claasahl.forex.model.*;

public class SymbolRepository {
	private final List<Symbol> symbols;

	public SymbolRepository() {
		AtomicInteger ids = new AtomicInteger();
		symbols = new ArrayList<>();
		symbols.add(new InternalSymbol(ids.getAndIncrement(), "EURUSD", Duration.ofMinutes(1)));
		symbols.add(new InternalSymbol(ids.getAndIncrement(), "EURUSD", Duration.ofHours(1)));
		symbols.add(new InternalSymbol(ids.getAndIncrement(), "EURUSD", Duration.ZERO));
	}

	public List<Symbol> getSymbolsForBroker(Broker broker) {
		return symbols;
	}
}
