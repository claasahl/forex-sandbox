package org.github.claasahl.forex.filter;

import java.util.function.*;

public class FixedSymbol<T> implements Predicate<T> {
	private final String symbol;
	private final Function<T, String> getter;

	public FixedSymbol(String symbol, Function<T, String> getter) {
		this.symbol = symbol;
		this.getter = getter;
	}

	public String getSymbol() {
		return symbol;
	}

	@Override
	public boolean test(T t) {
		return getter.apply(t).equalsIgnoreCase(symbol);
	}
}
