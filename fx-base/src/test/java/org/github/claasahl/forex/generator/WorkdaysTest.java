package org.github.claasahl.forex.generator;

import static java.time.temporal.TemporalAdjusters.*;
import static org.github.claasahl.forex.util.Helpers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import java.time.*;
import java.util.function.Supplier;
import org.junit.Test;
import io.reactivex.Observable;

public class WorkdaysTest {
	@Test
	public void shouldFilterMondays() throws InterruptedException {
		Workdays filter = new Workdays();
		OffsetDateTime monday = midnight().with(previousOrSame(DayOfWeek.MONDAY));
		Supplier<OffsetDateTime> supplier = new ProgressiveDateTime(monday, M1);
		Observable.range(0, 1440)
				.map(num -> supplier.get())
				.filter(filter::test)
				.test()
				.await()
				.assertValueCount(1440)
				.assertComplete();
	}

	@Test
	public void shouldFilterTuesdays() throws InterruptedException {
		Workdays filter = new Workdays();
		OffsetDateTime tuesday = midnight().with(previousOrSame(DayOfWeek.TUESDAY));
		Supplier<OffsetDateTime> supplier = new ProgressiveDateTime(tuesday, M1);
		Observable.range(0, 1440)
				.map(num -> supplier.get())
				.filter(filter::test)
				.test()
				.await()
				.assertValueCount(1440)
				.assertComplete();
	}

	@Test
	public void shouldFilterWednesdays() throws InterruptedException {
		Workdays filter = new Workdays();
		OffsetDateTime wednesday = midnight().with(previousOrSame(DayOfWeek.WEDNESDAY));
		Supplier<OffsetDateTime> supplier = new ProgressiveDateTime(wednesday, M1);
		Observable.range(0, 1440)
				.map(num -> supplier.get())
				.filter(filter::test)
				.test()
				.await()
				.assertValueCount(1440)
				.assertComplete();
	}

	@Test
	public void shouldFilterThurdays() throws InterruptedException {
		Workdays filter = new Workdays();
		OffsetDateTime thursday = midnight().with(previousOrSame(DayOfWeek.THURSDAY));
		Supplier<OffsetDateTime> supplier = new ProgressiveDateTime(thursday, M1);
		Observable.range(0, 1440)
				.map(num -> supplier.get())
				.filter(filter::test)
				.test()
				.await()
				.assertValueCount(1440)
				.assertComplete();
	}

	@Test
	public void shouldDropFridaysAfter21Z() throws InterruptedException {
		Workdays filter = new Workdays();
		OffsetDateTime friday = midnight().with(previousOrSame(DayOfWeek.FRIDAY));
		Supplier<OffsetDateTime> supplier = new ProgressiveDateTime(friday, M1);
		Observable.range(0, 1440)
				.map(num -> supplier.get())
				.filter(filter::test)
				.test()
				.await()
				.assertValueCount(1260)
				.assertComplete();
	}

	@Test
	public void shouldDropSaturdays() throws InterruptedException {
		Workdays filter = new Workdays();
		OffsetDateTime saturday = midnight().with(previousOrSame(DayOfWeek.SATURDAY));
		Supplier<OffsetDateTime> supplier = new ProgressiveDateTime(saturday, M1);
		Observable.range(0, 1440)
				.map(num -> supplier.get())
				.filter(filter::test)
				.test()
				.await()
				.assertValueCount(0)
				.assertComplete();
	}

	@Test
	public void shouldDropSundaysAfter21Z() throws InterruptedException {
		Workdays filter = new Workdays();
		OffsetDateTime sunday = midnight().with(previousOrSame(DayOfWeek.SUNDAY));
		Supplier<OffsetDateTime> supplier = new ProgressiveDateTime(sunday, M1);
		Observable.range(0, 1440)
				.map(num -> supplier.get())
				.filter(filter::test)
				.test()
				.await()
				.assertValueCount(180)
				.assertComplete();
	}

	@Test
	public void shouldFilterFridaysBefore21Z() throws InterruptedException {
		Workdays filter = new Workdays();
		OffsetDateTime friday = midnight().with(previousOrSame(DayOfWeek.FRIDAY));
		OffsetDateTime dateTime = friday.withHour(20).withMinute(59);
		assertThat(filter.test(dateTime), is(true));
	}

	@Test
	public void shouldDropFridaysAt21Z() throws InterruptedException {
		Workdays filter = new Workdays();
		OffsetDateTime friday = midnight().with(previousOrSame(DayOfWeek.FRIDAY));
		OffsetDateTime dateTime = friday.withHour(21);
		assertThat(filter.test(dateTime), is(false));
	}

	@Test
	public void shouldDropSundaysBefore21Z() throws InterruptedException {
		Workdays filter = new Workdays();
		OffsetDateTime sunday = midnight().with(previousOrSame(DayOfWeek.SUNDAY));
		OffsetDateTime dateTime = sunday.withHour(20).withMinute(59);
		assertThat(filter.test(dateTime), is(false));
	}

	@Test
	public void shouldFilterSundaysAt21Z() throws InterruptedException {
		Workdays filter = new Workdays();
		OffsetDateTime sunday = midnight().with(previousOrSame(DayOfWeek.SUNDAY));
		OffsetDateTime dateTime = sunday.withHour(21);
		assertThat(filter.test(dateTime), is(true));
	}
}
