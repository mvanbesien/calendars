package fr.mvanbesien.calendars.calendars;

import java.util.Calendar;

import fr.mvanbesien.calendars.dates.AbstractDate;
import fr.mvanbesien.calendars.dates.MuslimDate;

public class MuslimCalendar {

	private static final int CALENDAR_BEGIN = 1948438;

	private static final int CYCLE_LENGTH = 354 * 30 + 11;

	public static AbstractDate getDate() {
		return getDate(Calendar.getInstance());
	}

	public static AbstractDate getDate(Calendar calendar) {
		int dayInEra = (int) (ClassicCalendar.getJulianDay(calendar) - CALENDAR_BEGIN - 0.5);

		int cycleIndex = (dayInEra / CYCLE_LENGTH);
		int daysInCycle = (dayInEra % CYCLE_LENGTH);

		int yearInCycle = -1;
		int daysInYear = -1;
		int[] addedDayCountPerYear = getAddedDayCountPerYear();
		for (int i = 0; i < addedDayCountPerYear.length && yearInCycle == -1; i++) {
			if (addedDayCountPerYear[i] > daysInCycle) {
				yearInCycle = i;
				daysInYear = i == 0 ? daysInCycle : daysInCycle - addedDayCountPerYear[i - 1];
			}
		}
		int year = cycleIndex * 30 + yearInCycle + 1;
		int daysInTwoMonths = daysInYear % 59;
		int month = daysInYear / 59 * 2 + daysInTwoMonths / 30 + 1;

		int day = (daysInTwoMonths > 29 ? (daysInTwoMonths % 59 - 30) : daysInTwoMonths % 59) + 1;
		
		int dayOfWeek = (dayInEra + 4) % 7;
		MuslimDate date = new MuslimDate();
		date.setOrdinaryDate(dayOfWeek, day, month, year);
		return date;
	}

	private static int[] getAddedDayCountPerYear() {
		int[] array = new int[30];
		int sum = 0;
		for (int i = 0; i < array.length; i++) {
			sum += (i == 1 || i == 4 || i == 6 || i == 9 || i == 12 || i == 14 || i == 17 || i == 20 || i == 23
					|| i == 25 || i == 28) ? 355 : 354;
			array[i] = sum;
		}
		return array;
	}
}
