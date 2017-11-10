package org.github.claasahl.forex.quality;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.function.Predicate;
import org.github.claasahl.forex.Rate;
import org.github.claasahl.forex.filter.BeforeDateTime;
import io.reactivex.*;

public class RateDateTimeTooLateIssue extends Issue<Rate> {
	private final OffsetDateTime dateTime;

	RateDateTimeTooLateIssue(OffsetDateTime dateTime, Rate rate) {
		super(String.format("Expected 'dateTime' to be before %s (excl.)", dateTime), Arrays.asList(rate));
		this.dateTime = dateTime;
	}

	public OffsetDateTime getDateTime() {
		return dateTime;
	}

	@Override
	public Completable accept(IssueVisitor visitor) {
		return Completable.fromAction(() -> visitor.visitRateDateTimeTooLateIssue(this));
	}

	public static Observable<RateDateTimeTooLateIssue> detect(Observable<Rate> source,
			final OffsetDateTime dateTime) {
		Predicate<Rate> predicate = new BeforeDateTime<>(dateTime, Rate::getDateTime);
		return source.filter(predicate.negate()::test)
				.map(c -> new RateDateTimeTooLateIssue(dateTime, c));
	}
}
