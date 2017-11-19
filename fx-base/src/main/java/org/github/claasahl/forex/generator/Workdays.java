package org.github.claasahl.forex.generator;

import java.time.*;
import java.util.function.*;

/**
 * The class/interface <code>Weekdays</code>. It represents a filter for dates
 * and times that roughly corresponds to workdays (i.e. Monday - Friday). When
 * applying this filter, dates and times that fall in the period of Friday 9pm
 * (incl.) to Sunday 9pm (excl.) are filtered out.
 *
 * @author Claas Ahlrichs
 *
 */
public class Workdays implements Predicate<OffsetDateTime> {
	@Override
	public boolean test(OffsetDateTime dT) {
		OffsetDateTime dateTime = dT.withOffsetSameInstant(ZoneOffset.UTC);
		return !((dateTime.getDayOfWeek() == DayOfWeek.FRIDAY && dateTime.getHour() >= 21)
				|| dateTime.getDayOfWeek() == DayOfWeek.SATURDAY
				|| (dateTime.getDayOfWeek() == DayOfWeek.SUNDAY && dateTime.getHour() < 21));
	}
}
