package org.github.claasahl.forex.quality;

import java.util.Arrays;
import java.util.function.Predicate;
import org.github.claasahl.forex.Candle;
import org.github.claasahl.forex.filter.FixedSymbol;
import io.reactivex.*;

public class CandleSymbolIssue extends Issue<Candle> {
	private final String symbol;

	CandleSymbolIssue(String symbol, Candle candle) {
		super(String.format("Expected 'symbol' to be %s", symbol), Arrays.asList(candle));
		this.symbol = symbol;
	}

	public String getSymbol() {
		return symbol;
	}

	@Override
	public Completable accept(IssueVisitor visitor) {
		return Completable.fromAction(() -> visitor.visitCandleSymbolIssue(this));
	}

	public static Observable<CandleSymbolIssue> detect(Observable<Candle> source, final String symbol) {
		Predicate<Candle> predicate = new FixedSymbol<>(symbol, Candle::getSymbol);
		return source.filter(predicate.negate()::test)
				.map(c -> new CandleSymbolIssue(symbol, c));
	}
}
