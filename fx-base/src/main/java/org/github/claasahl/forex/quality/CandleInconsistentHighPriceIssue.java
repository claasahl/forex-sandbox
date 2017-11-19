package org.github.claasahl.forex.quality;

import java.util.Arrays;
import java.util.function.Predicate;
import org.github.claasahl.forex.Candle;
import org.github.claasahl.forex.filter.ConsistentHighPrice;
import io.reactivex.*;

public class CandleInconsistentHighPriceIssue extends Issue<Candle> {
	CandleInconsistentHighPriceIssue(Candle candle) {
		super("Expected 'high' price to be the highest price", Arrays.asList(candle));
	}

	@Override
	public Completable accept(IssueVisitor visitor) {
		return Completable.fromAction(() -> visitor.visitCandleInconsistentHighPriceIssue(this));
	}

	public static Observable<CandleInconsistentHighPriceIssue> detect(Observable<Candle> source) {
		Predicate<Candle> predicate = new ConsistentHighPrice().negate();
		return source.filter(predicate::test)
				.map(CandleInconsistentHighPriceIssue::new);
	}
}
