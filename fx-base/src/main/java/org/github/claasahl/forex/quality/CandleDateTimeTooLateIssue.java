package org.github.claasahl.forex.quality;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.function.Predicate;
import org.github.claasahl.forex.Candle;
import org.github.claasahl.forex.filter.BeforeDateTime;
import io.reactivex.*;

public class CandleDateTimeTooLateIssue extends Issue<Candle> {
	private final OffsetDateTime dateTime;

	CandleDateTimeTooLateIssue(OffsetDateTime dateTime, Candle candle) {
		super(String.format("Expected 'dateTime' to be before %s (excl.)", dateTime), Arrays.asList(candle));
		this.dateTime = dateTime;
	}

	public OffsetDateTime getDateTime() {
		return dateTime;
	}

	@Override
	public Completable accept(IssueVisitor visitor) {
		return Completable.fromAction(() -> visitor.visitCandleDateTimeTooLateIssue(this));
	}

	public static Observable<CandleDateTimeTooLateIssue> detect(Observable<Candle> source,
			final OffsetDateTime dateTime) {
		Predicate<Candle> predicate = new BeforeDateTime<>(dateTime, Candle::getDateTime);
		return source.filter(predicate.negate()::test)
				.map(c -> new CandleDateTimeTooLateIssue(dateTime, c));
	}
}
