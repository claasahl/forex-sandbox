package org.github.claasahl.forex.playground;

import java.util.*;
import java.util.function.Function;
import org.github.claasahl.forex.Candle;

public class SimpleMovingAverage {
	private final Function<Candle, Double> priceAccessor;
	private final List<Candle> data;
	private final int count;
	private double accumulatedPrice;

	public SimpleMovingAverage(Function<Candle, Double> priceAccessor, int count) {
		super();
		this.priceAccessor = priceAccessor;
		this.data = new ArrayList<>();
		this.count = count;
		this.accumulatedPrice = 0.0;
	}

	public SimpleMovingAverage update(Candle candle) {
		this.data.add(candle);
		this.accumulatedPrice += priceAccessor.apply(candle);
		if (data.size() > this.count) {
			Candle oldest = this.data.remove(0);
			this.accumulatedPrice -= priceAccessor.apply(oldest);
		}
		return this;
	}

	public double getValue() {
		return this.accumulatedPrice / this.data.size();
	}

	@Override
	public String toString() {
		return "SimpleMovingAverage [count=" + count + ", accumulatedPrice=" + accumulatedPrice + ", value="
				+ getValue() + "]";
	}
}
