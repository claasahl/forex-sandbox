package org.github.claasahl.forex.function;

import static java.time.ZoneOffset.*;
import java.time.*;
import java.util.*;
import org.github.claasahl.forex.Candle;
import io.reactivex.*;
import io.reactivex.Observable;

public class CandleAccumulator implements ObservableTransformer<Candle, Candle> {
	private final Duration duration;
	// FIXME verify accumulation of M1 candles to H1 candles for spotware data on
	// 2017-07-14
	// FIXME 18:00 is missing, but there seems to be data available

	public CandleAccumulator(Duration duration) {
		if (Duration.ZERO.equals(duration) || duration.getNano() != 0)
			throw new IllegalArgumentException();
		this.duration = duration;
	}

	@Override
	public ObservableSource<Candle> apply(Observable<Candle> upstream) {
		Observable<Group> shared = upstream
				.scanWith(Group::new, this::accumulator)
				.publish()
				.autoConnect(2);
		return shared.buffer(2, 1)
				.flatMap(this::bla)
				.filter(Group::isComplete)
				.mergeWith(shared.takeLast(1))
				.distinctUntilChanged()
				.map(Group::getCandles)
				.filter(list -> !list.isEmpty())
				.map(this::accumulate);
	}
	
	private Observable<Group> bla(List<Group> list) {
		if(list.size() <= 1)
			return Observable.fromIterable(list);
		else {
			Group x = list.get(0);
			Group y = list.get(1);
			if(x.dateTime.isEqual(y.dateTime))
				return Observable.just(x, y);
			else
				return Observable.just(new Group(x, true), y);
		}
	}

	private Group accumulator(Group group, Candle candle) {
		Group newGroup;
		OffsetDateTime openDateTime = toOpenTime(candle.getDateTime());
		if (group.getDateTime().isEqual(openDateTime))
			newGroup = new Group(group, false);
		else
			newGroup = new Group(openDateTime);
		newGroup.append(candle);
		return newGroup;
	}

	private OffsetDateTime toOpenTime(OffsetDateTime dateTime) {
		long epochSeconds = dateTime.toEpochSecond();
		long durationSeconds = duration.getSeconds();
		// this is an integer division (i.e. it is not loss-free)
		epochSeconds = epochSeconds / durationSeconds * durationSeconds;
		return LocalDateTime.ofEpochSecond(epochSeconds, 0, UTC).atOffset(UTC);
	}

	private Candle accumulate(List<Candle> candles) {
		Candle firstCandle = candles.get(0);
		Candle.Builder builder = builder(firstCandle);
		for (Candle candle : candles) {
			if (!builder.getSymbol().equals(candle.getSymbol()))
				throw new IllegalArgumentException("'symbol' may not change");
			if (builder.getHigh() < candle.getHigh())
				builder.setHigh(candle.getHigh());
			if (builder.getLow() > candle.getLow())
				builder.setLow(candle.getLow());
			builder.setClose(candle.getClose());
			// FIXME accumulate volume
		}
		return builder.build();
	}

	private Candle.Builder builder(Candle candle) {
		OffsetDateTime dateTime = toOpenTime(candle.getDateTime());
		return candle.toBuilder()
				.setDuration(duration)
				.setDateTime(dateTime);
	}

	private class Group {
		private final OffsetDateTime dateTime;
		private final Stack<Candle> candles;
		private final boolean complete;

		public Group() {
			this(OffsetDateTime.MIN);
		}

		public Group(OffsetDateTime dateTime) {
			this.dateTime = dateTime;
			this.complete = false;
			this.candles = new Stack<>();
		}

		public Group(Group group, boolean complete) {
			this.dateTime = group.dateTime;
			this.complete = complete;
			this.candles = new Stack<>();
			this.candles.addAll(group.candles);
		}

		public OffsetDateTime getDateTime() {
			return dateTime;
		}

		public void append(Candle candle) {
			candles.add(candle);
		}

		public List<Candle> getCandles() {
			return candles;
		}

		public boolean isComplete() {
			if (candles.isEmpty())
				return false;
			else if(complete)
				return true;
			Candle candle = candles.peek();
			Duration duration = candle.getDuration();
			OffsetDateTime nextDateTime = candle.getDateTime().plus(duration);
			return toOpenTime(nextDateTime).isAfter(dateTime);
		}

		@Override
		public int hashCode() {
			return Objects.hashCode(dateTime);
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == this)
				return true;
			if (!(obj instanceof Group))
				return false;
			Group other = (Group) obj;
			return Objects.equals(dateTime, other.dateTime);
		}

		@Override
		public String toString() {
			return "Group [dateTime=" + dateTime + ", candles=" + candles + "]";
		}
	}
}
