package fr.mvanbesien.calendars.dates;

import fr.mvanbesien.calendars.tools.Utils;

public class ClassicDate extends AbstractDate {

	/**
	 * Months, in english, with Jan=1 to Dec=12
	 */
	public static final String[] MONTH = new String[] { "", "January",
			"February", "March", "April", "May", "June", "July", "August",
			"September", "October", "November", "December" };

	/**
	 * Weekdays, in english, with Mon=1, to Sun=7
	 */
	public static final String[] WEEKDAYS_1 = new String[] { "", "Monday",
			"Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };

	@Override
	public String toString() {
		if (isExtraDay)
			return "";
		return String.format("%s, %s %s, %s", ClassicDate.WEEKDAYS_1[weekDay],
				ClassicDate.MONTH[month], Utils.ordinal(day, true), year);
	}
}
