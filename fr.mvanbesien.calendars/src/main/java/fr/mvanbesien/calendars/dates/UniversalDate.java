package fr.mvanbesien.calendars.dates;

import fr.mvanbesien.calendars.tools.Utils;

public class UniversalDate extends AbstractDate {

	/**
	 * Weekdays in english, with Sun=1 to Sat=7
	 */
	public static final String[] WEEKDAYS_0 = new String[] { "", "Sunday",
			"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };

	/**
	 * Extra day names in english, for Sol and Universal Calendar
	 */
	public static final String[] R_EXTRA = new String[] { "New Year's Day",
			"Leap Day" };

	/**
	 * Months, in english, with Jan=1 to Dec=12
	 */
	public static final String[] MONTH = new String[] { "", "January",
			"February", "March", "April", "May", "June", "July", "August",
			"September", "October", "November", "December" };

	@Override
	public String toString() {
		if (isExtraDay)
			return String.format("%s %s", UniversalDate.R_EXTRA[extraDayIndex],
					year);
		return String.format("%s, %s %s, %s",
				UniversalDate.WEEKDAYS_0[weekDay], UniversalDate.MONTH[month],
				Utils.ordinal(day, true), year);
	}
}
