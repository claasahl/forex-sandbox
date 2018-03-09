package org.github.claasahl.forex.database;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import io.reactivex.Observable;
import io.reactivex.Single;

public class SymbolRepository {
	private final Map<String, Symbol> symbols;

	public SymbolRepository() {
		AtomicInteger ids = new AtomicInteger();
		List<Symbol> symbols = new ArrayList<>();
		symbols.add(new Symbol(ids.getAndIncrement(), 0, "AUDGBP", Duration.ofMinutes(1)));
		symbols.add(new Symbol(ids.getAndIncrement(), 1, "EURUSD", Duration.ofHours(1)));
		symbols.add(new Symbol(ids.getAndIncrement(), 1, "EURUSD", null));
		this.symbols = symbols.stream().collect(Collectors.toMap(Symbol::getId, Function.identity()));
	}

	public Observable<Symbol> getSymbolsForBrokerId(String brokerId) {
		return Observable.fromIterable(symbols.values())
				.filter(symbol -> symbol.getBrokerId().equals(brokerId));
	}

	public Single<Symbol> getSymbolForId(String symbolId) {
		return Single.just(symbols.get(symbolId));
	}
}
