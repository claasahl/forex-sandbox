package org.github.claasahl.forex.quality;

import java.util.Arrays;
import java.util.function.Predicate;
import org.github.claasahl.forex.Rate;
import org.github.claasahl.forex.filter.FixedSymbol;
import io.reactivex.*;

public class RateSymbolIssue extends Issue<Rate> {
	private final String symbol;

	RateSymbolIssue(String symbol, Rate rate) {
		super(String.format("Expected 'symbol' to be %s", symbol), Arrays.asList(rate));
		this.symbol = symbol;
	}

	public String getSymbol() {
		return symbol;
	}

	@Override
	public Completable accept(IssueVisitor visitor) {
		return Completable.fromAction(() -> visitor.visitRateSymbolIssue(this));
	}

	public static Observable<RateSymbolIssue> detect(Observable<Rate> source, final String symbol) {
		Predicate<Rate> predicate = new FixedSymbol<>(symbol, Rate::getSymbol);
		return source.filter(predicate.negate()::test)
				.map(r -> new RateSymbolIssue(symbol, r));
	}
}
