package org.github.claasahl.forex.quality;

import java.util.function.BiPredicate;
import org.github.claasahl.forex.Rate;
import org.github.claasahl.forex.filter.ProgressiveDateTime;
import io.reactivex.*;

public class RateStagnatingDateTimeIssue extends Issue<Rate> {
	RateStagnatingDateTimeIssue(Iterable<Rate> rates) {
		super("Expected 'dateTime' to be ascending", rates);
	}

	@Override
	public Completable accept(IssueVisitor visitor) {
		return Completable.fromAction(() -> visitor.visitRateStagnatingDateTimeIssue(this));
	}

	public static Observable<RateStagnatingDateTimeIssue> detect(Observable<Rate> source) {
		BiPredicate<Rate, Rate> predicate = new ProgressiveDateTime<>(Rate::getDateTime).negate();
		return source.buffer(2, 1)
				.filter(b -> b.size() == 2)
				.filter(b -> predicate.test(b.get(0), b.get(1)))
				.map(RateStagnatingDateTimeIssue::new);
	}
}
