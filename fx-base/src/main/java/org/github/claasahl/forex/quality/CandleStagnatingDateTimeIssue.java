package org.github.claasahl.forex.quality;

import java.util.function.BiPredicate;
import org.github.claasahl.forex.Candle;
import org.github.claasahl.forex.filter.ProgressiveDateTime;
import io.reactivex.*;

public class CandleStagnatingDateTimeIssue extends Issue<Candle> {
	CandleStagnatingDateTimeIssue(Iterable<Candle> candles) {
		super("Expected 'dateTime' to be ascending", candles);
	}

	@Override
	public Completable accept(IssueVisitor visitor) {
		return Completable.fromAction(() -> visitor.visitCandleStagnatingDateTimeIssue(this));
	}

	public static Observable<CandleStagnatingDateTimeIssue> detect(Observable<Candle> source) {
		BiPredicate<Candle, Candle> predicate = new ProgressiveDateTime<>(Candle::getDateTime).negate();
		return source.buffer(2, 1)
				.filter(b -> b.size() == 2)
				.filter(b -> predicate.test(b.get(0), b.get(1)))
				.map(CandleStagnatingDateTimeIssue::new);
	}
}
