package org.github.claasahl.forex.quality;

import java.time.Duration;
import java.util.Arrays;
import java.util.function.Predicate;
import org.github.claasahl.forex.Candle;
import org.github.claasahl.forex.filter.LiveDateTime;
import io.reactivex.*;

public class CandleLiveDateTimeIssue extends Issue<Candle> {
	private final Duration tolerableDifference;

	CandleLiveDateTimeIssue(Duration tolerableDifference, Candle candle) {
		super(String.format("Expected 'dateTime' to be within %s sec. of actual time",
				tolerableDifference.getSeconds()), Arrays.asList(candle));
		this.tolerableDifference = tolerableDifference;
	}

	public Duration getTolerableDifference() {
		return tolerableDifference;
	}

	@Override
	public Completable accept(IssueVisitor visitor) {
		return Completable.fromAction(() -> visitor.visitCandleLiveDateTimeIssue(this));
	}

	public static Observable<CandleLiveDateTimeIssue> detect(Observable<Candle> source,
			final Duration tolerableDifference) {
		Predicate<Candle> predicate = new LiveDateTime<>(tolerableDifference, Candle::getDateTime);
		return source.filter(predicate.negate()::test)
				.map(c -> new CandleLiveDateTimeIssue(tolerableDifference, c));
	}
}
