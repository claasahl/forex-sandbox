package org.github.claasahl.forex.data;

import java.time.*;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;

public class DateTimeHelperTest {

	@Test
	public void shouldEmitLiveDateTimes() {
		TestScheduler scheduler = new TestScheduler();
		Duration duration = Duration.ofMinutes(1);
		OffsetDateTime from = OffsetDateTime.of(2017, 11, 1, 15, 23, 0, 0, ZoneOffset.UTC);

		TestObserver<OffsetDateTime> test = DateTimeHelper
				.live(duration, from, scheduler)
				.take(4)
				.test();
		scheduler.advanceTimeTo(59999, TimeUnit.MILLISECONDS);
		test.assertValueCount(1);
		scheduler.advanceTimeTo(60000, TimeUnit.MILLISECONDS);
		test.assertValueCount(2);

		scheduler.advanceTimeTo(119999, TimeUnit.MILLISECONDS);
		test.assertValueCount(2);
		scheduler.advanceTimeTo(120000, TimeUnit.MILLISECONDS);
		test.assertValueCount(3);

		scheduler.advanceTimeTo(179999, TimeUnit.MILLISECONDS);
		test.assertValueCount(3);
		scheduler.advanceTimeTo(180000, TimeUnit.MILLISECONDS);
		test.assertValues(OffsetDateTime.of(2017, 11, 1, 15, 23, 0, 0, ZoneOffset.UTC),
				OffsetDateTime.of(2017, 11, 1, 15, 24, 0, 0, ZoneOffset.UTC),
				OffsetDateTime.of(2017, 11, 1, 15, 25, 0, 0, ZoneOffset.UTC),
				OffsetDateTime.of(2017, 11, 1, 15, 26, 0, 0, ZoneOffset.UTC))
				.assertComplete();
	}

	@Test
	public void shouldEmitUnlimitedHistoricDateTimes() {
		OffsetDateTime from = OffsetDateTime.of(2017, 11, 1, 15, 23, 0, 0, ZoneOffset.UTC);
		Duration duration = Duration.ofMinutes(1);

		DateTimeHelper.historic(duration, from)
				.take(3)
				.test()
				.assertValues(OffsetDateTime.of(2017, 11, 1, 15, 23, 0, 0, ZoneOffset.UTC),
						OffsetDateTime.of(2017, 11, 1, 15, 24, 0, 0, ZoneOffset.UTC),
						OffsetDateTime.of(2017, 11, 1, 15, 25, 0, 0, ZoneOffset.UTC))
				.assertComplete();
	}
	
	@Test
	public void shouldEmitLimitedHistoricDateTimes() {
		OffsetDateTime from = OffsetDateTime.of(2017, 11, 1, 15, 23, 0, 0, ZoneOffset.UTC);
		OffsetDateTime to = OffsetDateTime.of(2017, 11, 1, 15, 25, 0, 0, ZoneOffset.UTC);
		Duration duration = Duration.ofMinutes(1);

		DateTimeHelper.historic(duration, from, to)
				.test()
				.assertValues(OffsetDateTime.of(2017, 11, 1, 15, 23, 0, 0, ZoneOffset.UTC),
						OffsetDateTime.of(2017, 11, 1, 15, 24, 0, 0, ZoneOffset.UTC),
						OffsetDateTime.of(2017, 11, 1, 15, 25, 0, 0, ZoneOffset.UTC))
				.assertComplete();
	}

}
