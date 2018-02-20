package org.github.claasahl.forex.database;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.github.claasahl.forex.model.*;

public class SymbolRepository {
	private final Map<Integer, Symbol> symbols;

	public SymbolRepository() {
		AtomicInteger ids = new AtomicInteger();
		List<Symbol> symbols = new ArrayList<>();
		symbols.add(new InternalSymbol(ids.getAndIncrement(), 0, "EURUSD", Duration.ofMinutes(1)));
		symbols.add(new InternalSymbol(ids.getAndIncrement(), 1, "EURUSD", Duration.ofHours(1)));
		symbols.add(new InternalSymbol(ids.getAndIncrement(), 1, "EURUSD", Duration.ZERO));
		this.symbols = symbols.stream().collect(Collectors.toMap(Symbol::getId, Function.identity()));
	}

	public Collection<Symbol> getSymbolsForBroker(Broker broker) {
		return symbols.values().stream()
				.filter(symbol -> symbol.getBrokerId() == broker.getId())
				.collect(Collectors.toList());
	}

	public Symbol getSymbolForId(int symbolId) {
		return symbols.get(symbolId);
	}
}
