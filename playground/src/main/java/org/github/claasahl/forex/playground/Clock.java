package org.github.claasahl.forex.playground;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;

public class Clock {

	public static void main(String[] args) throws IOException {
		Observable.interval(100, TimeUnit.MILLISECONDS)
				.map(l -> now())
				.distinctUntilChanged()
				.doOnNext(System.out::println)
				.subscribe();
		System.in.read();
	}

	private static LocalDateTime now() {
		return LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
	}

}
