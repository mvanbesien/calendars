package fr.mvanbesien.calendars.dates;

import fr.mvanbesien.calendars.tools.Utils;

public class Symmetry454Date extends AbstractDate {

	/**
	 * Weekdays in english, with Sun=1 to Sat=7
	 */
	public static final String[] WEEKDAYS_0 = new String[] { "", "Sunday",
			"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };

	/**
	 * Months, in english, with Jan=1 to Dec=12
	 */
	public static final String[] MONTH = new String[] { "", "January",
			"February", "March", "April", "May", "June", "July", "August",
			"September", "October", "November", "December" };

	@Override
	public String toString() {
		if (isExtraDay)
			return "";
		return String.format("%s, %s %s, %s",
				Symmetry454Date.WEEKDAYS_0[weekDay],
				Symmetry454Date.MONTH[month], Utils.ordinal(day, true), year);

	}

}
