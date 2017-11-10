package org.github.claasahl.forex.quality;

import java.time.Duration;
import java.util.Arrays;
import java.util.function.Predicate;
import org.github.claasahl.forex.Candle;
import org.github.claasahl.forex.filter.FixedDuration;
import io.reactivex.*;

public class CandleDurationIssue extends Issue<Candle> {
	private final Duration duration;

	CandleDurationIssue(Duration duration, Candle candle) {
		super(String.format("Expected 'duration' to be %s", duration), Arrays.asList(candle));
		this.duration = duration;
	}

	public Duration getDuration() {
		return duration;
	}

	@Override
	public Completable accept(IssueVisitor visitor) {
		return Completable.fromAction(() -> visitor.visitCandleDurationIssue(this));
	}

	public static Observable<CandleDurationIssue> detect(Observable<Candle> source, final Duration duration) {
		Predicate<Candle> predicate = new FixedDuration<>(duration, Candle::getDuration);
		return source.filter(predicate.negate()::test)
				.map(c -> new CandleDurationIssue(duration, c));
	}
}
