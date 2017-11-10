package org.github.claasahl.forex.quality;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.function.Predicate;
import org.github.claasahl.forex.Candle;
import org.github.claasahl.forex.filter.BeforeDateTime;
import io.reactivex.*;

public class CandleDateTimeTooEarlyIssue extends Issue<Candle> {
	private final OffsetDateTime dateTime;

	CandleDateTimeTooEarlyIssue(OffsetDateTime dateTime, Candle candle) {
		super(String.format("Expected 'dateTime' to be after %s (incl.)", dateTime), Arrays.asList(candle));
		this.dateTime = dateTime;
	}

	public OffsetDateTime getDateTime() {
		return dateTime;
	}

	@Override
	public Completable accept(IssueVisitor visitor) {
		return Completable.fromAction(() -> visitor.visitCandleDateTimeTooEarlyIssue(this));
	}

	public static Observable<CandleDateTimeTooEarlyIssue> detect(Observable<Candle> source,
			final OffsetDateTime dateTime) {
		Predicate<Candle> predicate = new BeforeDateTime<>(dateTime, Candle::getDateTime);
		return source.filter(predicate::test)
				.map(c -> new CandleDateTimeTooEarlyIssue(dateTime, c));
	}
}
