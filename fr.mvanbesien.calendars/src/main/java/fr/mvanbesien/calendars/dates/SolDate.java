package fr.mvanbesien.calendars.dates;

import fr.mvanbesien.calendars.tools.Utils;

public class SolDate extends AbstractDate {

	/**
	 * Month names in english, for Sol Calendar, with Jan=1, to Dec=13
	 */
	public static final String[] SOL_MONTH = new String[] { "", "January",
			"February", "March", "April", "May", "June", "Sol", "July",
			"August", "September", "October", "November", "December" };

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

	@Override
	public String toString() {
		if (isExtraDay)
			return String.format("%s %s", SolDate.R_EXTRA[extraDayIndex], year);
		return String.format("%s, %s %s, %s", SolDate.WEEKDAYS_0[weekDay],
				SolDate.SOL_MONTH[month], Utils.ordinal(day, true), year);
	}

}
