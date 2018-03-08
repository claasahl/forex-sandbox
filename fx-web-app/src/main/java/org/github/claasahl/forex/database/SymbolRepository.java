package org.github.claasahl.forex.database;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import io.reactivex.Observable;
import io.reactivex.Single;

public class SymbolRepository {
	private final Map<String, InternalSymbol> symbols;

	public SymbolRepository() {
		AtomicInteger ids = new AtomicInteger();
		List<InternalSymbol> symbols = new ArrayList<>();
		symbols.add(new InternalSymbol(ids.getAndIncrement(), 0, "AUDGBP", Duration.ofMinutes(1)));
		symbols.add(new InternalSymbol(ids.getAndIncrement(), 1, "EURUSD", Duration.ofHours(1)));
		symbols.add(new InternalSymbol(ids.getAndIncrement(), 1, "EURUSD", null));
		this.symbols = symbols.stream().collect(Collectors.toMap(InternalSymbol::getId, Function.identity()));
	}

	public Observable<InternalSymbol> getSymbolsForBrokerId(String brokerId) {
		return Observable.fromIterable(symbols.values())
				.filter(symbol -> symbol.getBrokerId().equals(brokerId));
	}

	public Single<InternalSymbol> getSymbolForId(String symbolId) {
		return Single.just(symbols.get(symbolId));
	}
}
