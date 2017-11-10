package org.github.claasahl.forex.quality;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.function.Predicate;
import org.github.claasahl.forex.Rate;
import org.github.claasahl.forex.filter.BeforeDateTime;
import io.reactivex.*;

public class RateDateTimeTooEarlyIssue extends Issue<Rate> {
	private final OffsetDateTime dateTime;

	RateDateTimeTooEarlyIssue(OffsetDateTime dateTime, Rate rate) {
		super(String.format("Expected 'dateTime' to be after %s (incl.)", dateTime), Arrays.asList(rate));
		this.dateTime = dateTime;
	}

	public OffsetDateTime getDateTime() {
		return dateTime;
	}

	@Override
	public Completable accept(IssueVisitor visitor) {
		return Completable.fromAction(() -> visitor.visitRateDateTimeTooEarlyIssue(this));
	}

	public static Observable<RateDateTimeTooEarlyIssue> detect(Observable<Rate> source,
			final OffsetDateTime dateTime) {
		Predicate<Rate> predicate = new BeforeDateTime<>(dateTime, Rate::getDateTime);
		return source.filter(predicate::test)
				.map(c -> new RateDateTimeTooEarlyIssue(dateTime, c));
	}
}
