/**
 * $Id: RepublicanCalendar.java,v 1.3 2008/11/05 09:58:35 maxence Exp $
 * 
 * HISTORY
 * -------
 * $Log: RepublicanCalendar.java,v $
 * Revision 1.3  2008/11/05 09:58:35  maxence
 * Removed useless method
 *
 * Revision 1.2  2008/11/05 09:40:52  maxence
 * Fixed
 *
 * Revision 1.1  2008/11/05 08:53:40  maxence
 * Saved
 *
 * Revision 1.2  2007/11/20 17:30:31  bezien
 * Removed ICalendar interface
 *
 * Revision 1.1  2007/08/30 11:12:35  bezien
 * Initial revision
 *
 */
package fr.mvanbesien.calendars.calendars;

import java.util.Calendar;

import fr.mvanbesien.calendars.dates.RepublicanDate;
import fr.mvanbesien.calendars.tools.Utils;

/**
 * This Class converts a Gregorian Calendar Date into a Republican Calendar
 * Date.
 * 
 * @author mvanbesien
 * 
 */
public class RepublicanCalendar {

	public static RepublicanDate getDate() {
		return RepublicanCalendar.getDate(Calendar.getInstance());
	}

	/**
	 * Returns the French Republican Date for the day passed as parameter
	 * 
	 * @param calendar
	 *            : Calendar instance
	 * @return French Republican Date
	 */
	public static RepublicanDate getDate(Calendar calendar) {

		RepublicanDate date = new RepublicanDate();

		int day = calendar.get(Calendar.DATE);
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);

		int nbOfDays = Utils.dayCounter(22, 9, year - 1, day, month, year);
		int nbOfDaysInYear = Utils.isLeapYear(year) ? 366 : 365;
		int rYear = year - 1792;
		rYear = rYear + (nbOfDays > nbOfDaysInYear ? 1 : 0);
		nbOfDays = nbOfDays > nbOfDaysInYear ? nbOfDays - nbOfDaysInYear
				: nbOfDays;

		int rDays = (nbOfDays - 1) % 30 + 1;
		int rMonth = (nbOfDays - 1) / 30 + 1;
		int rWeekday = nbOfDays % 10;
		if (rMonth == 13) {
			date.setExtraDate(rDays, rYear);
			return date;
		} else {
			date.setOrdinaryDate(rWeekday, rDays, rMonth, rYear);
			return date;
		}
	}
}
