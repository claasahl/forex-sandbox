package org.github.claasahl.forex.function;

import io.reactivex.*;

public class SimpleMovingAverage implements ObservableTransformer<Number, Double> {
	private final int period;

	public SimpleMovingAverage(int period) {
		this.period = period;
	}

	@Override
	public ObservableSource<Double> apply(Observable<Number> upstream) {
		Observable<Observable<Number>> windows = upstream
				.publish()
				.autoConnect(2)
				.window(period, 1);
		Observable<Long> sizes = windows.flatMapSingle(Observable::count);
		Observable<Double> sums = windows.flatMapSingle(w -> w.reduce(0.0, (sum, val) -> sum + val.doubleValue()));
		return Observable.zip(sums, sizes, (sum, size) -> sum / (double) size)
				.skipLast(period - 1);
	}
}
