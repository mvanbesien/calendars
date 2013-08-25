/**
 * $Id: UniversalCalendar.java,v 1.3 2008/11/05 09:58:35 maxence Exp $
 * 
 * HISTORY
 * -------
 * $Log: UniversalCalendar.java,v $
 * Revision 1.3  2008/11/05 09:58:35  maxence
 * Removed useless method
 *
 * Revision 1.2  2008/11/05 09:54:29  maxence
 * Fixed
 *
 * Revision 1.1  2008/11/05 08:53:41  maxence
 * Saved
 *
 * Revision 1.3  2007/11/20 17:30:31  bezien
 * Removed ICalendar interface
 *
 * Revision 1.2  2007/10/08 16:26:53  bezien
 * Fixed year of the New Year's day
 *
 * Revision 1.1  2007/08/30 11:12:36  bezien
 * Initial revision
 *
 */
package fr.mvanbesien.calendars.calendars;

import java.util.Calendar;

import fr.mvanbesien.calendars.dates.UniversalDate;
import fr.mvanbesien.calendars.tools.Utils;

/**
 * This Class converts a Gregorian Calendar Date into a Universal Calendar Date.
 * 
 * @author mvanbesien
 * 
 */
public class UniversalCalendar {

	public static UniversalDate getDate() {
		return UniversalCalendar.getDate(Calendar.getInstance());
	}

	/**
	 * Returns the Universal Date for the date passed as parameter
	 * 
	 * @param calendar
	 *            : Calendar Instance
	 * @return Universal Date
	 */
	public static UniversalDate getDate(Calendar calendar) {

		int[] offsets = new int[] { 0, 31, 61, 91, 122, 152, 182, 213, 243,
				273, 304, 334 };

		UniversalDate date = new UniversalDate();

		int day = calendar.get(Calendar.DATE);
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);

		if (Utils.isLeapYear(year))
			for (int i = 6; i < 12; i++)
				offsets[i]++;

		int nbOfDays = Utils.dayCounter(1, 1, year, day, month, year);

		int uDay;
		int uMonth = 1;
		int uYear = year;
		int uWeekday;

		int index = 1;

		while (index < 12 && nbOfDays >= offsets[index]) {
			index++;
			uMonth = index;

		}
		uDay = nbOfDays - offsets[uMonth - 1] + 1;
		if (uDay == 31 && uMonth == 6) {
			date.setExtraDate(1, uYear);
			return date;
		} else if (uDay == 31 && uMonth == 12) {
			date.setExtraDate(0, uYear + 1);
			return date;
		} else {
			uWeekday = nbOfDays % 7 + 1;
			date.setOrdinaryDate(uWeekday, uDay, uMonth, uYear);
			return date;
		}
	}

}
