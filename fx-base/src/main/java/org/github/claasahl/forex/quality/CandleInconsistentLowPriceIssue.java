package org.github.claasahl.forex.quality;

import java.util.Arrays;
import java.util.function.Predicate;
import org.github.claasahl.forex.Candle;
import org.github.claasahl.forex.filter.ConsistentLowPrice;
import io.reactivex.*;

public class CandleInconsistentLowPriceIssue extends Issue<Candle> {
	CandleInconsistentLowPriceIssue(Candle candle) {
		super("Expected 'low' price to be the lowest price", Arrays.asList(candle));
	}

	@Override
	public Completable accept(IssueVisitor visitor) {
		return Completable.fromAction(() -> visitor.visitCandleInconsistentLowPriceIssue(this));
	}

	public static Observable<CandleInconsistentLowPriceIssue> detect(Observable<Candle> source) {
		Predicate<Candle> predicate = new ConsistentLowPrice().negate();
		return source.filter(predicate::test)
				.map(CandleInconsistentLowPriceIssue::new);
	}
}
