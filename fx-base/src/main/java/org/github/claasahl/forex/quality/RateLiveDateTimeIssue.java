package org.github.claasahl.forex.quality;

import java.time.Duration;
import java.util.Arrays;
import java.util.function.Predicate;
import org.github.claasahl.forex.Rate;
import org.github.claasahl.forex.filter.LiveDateTime;
import io.reactivex.*;

public class RateLiveDateTimeIssue extends Issue<Rate> {
	private final Duration tolerableDifference;

	RateLiveDateTimeIssue(Duration tolerableDifference, Rate rate) {
		super(String.format("Expected 'dateTime' to be within %s sec. of actual time",
				tolerableDifference.getSeconds()), Arrays.asList(rate));
		this.tolerableDifference = tolerableDifference;
	}

	public Duration getTolerableDifference() {
		return tolerableDifference;
	}

	@Override
	public Completable accept(IssueVisitor visitor) {
		return Completable.fromAction(() -> visitor.visitRateLiveDateTimeIssue(this));
	}

	public static Observable<RateLiveDateTimeIssue> detect(Observable<Rate> source,
			final Duration tolerableDifference) {
		Predicate<Rate> predicate = new LiveDateTime<>(tolerableDifference, Rate::getDateTime);
		return source.filter(predicate.negate()::test)
				.map(c -> new RateLiveDateTimeIssue(tolerableDifference, c));
	}
}
