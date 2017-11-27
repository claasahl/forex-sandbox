package org.github.claasahl.forex.data;

import java.time.*;
import org.junit.Test;

public class DateTimeHelperTest {

	@Test
	public void shouldEmitHistoricDateTimes() {
		OffsetDateTime start = OffsetDateTime.of(2017, 11, 1, 15, 23, 0, 0, ZoneOffset.UTC);
		OffsetDateTime end = OffsetDateTime.of(2017, 11, 1, 15, 25, 0, 0, ZoneOffset.UTC);
		Duration duration = Duration.ofMinutes(1);

		DateTimeHelper.historicDateTime(start, end, duration)
		.doOnNext(System.out::println)
				.test()
				.assertValues(OffsetDateTime.of(2017, 11, 1, 15, 23, 0, 0, ZoneOffset.UTC),
						OffsetDateTime.of(2017, 11, 1, 15, 24, 0, 0, ZoneOffset.UTC),
						OffsetDateTime.of(2017, 11, 1, 15, 25, 0, 0, ZoneOffset.UTC))
				.assertComplete();
	}

}
