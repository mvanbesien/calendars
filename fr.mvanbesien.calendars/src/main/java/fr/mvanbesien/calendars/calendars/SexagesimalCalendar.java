/**
 * $Id: SexagesimalCalendar.java,v 1.3 2008/11/05 09:58:34 maxence Exp $
 * 
 * HISTORY
 * -------
 * $Log: SexagesimalCalendar.java,v $
 * Revision 1.3  2008/11/05 09:58:34  maxence
 * Removed useless method
 *
 * Revision 1.2  2008/11/05 09:45:26  maxence
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

import fr.mvanbesien.calendars.dates.SexagesimalDate;
import fr.mvanbesien.calendars.tools.Utils;

/**
 * This Class converts a Gregorian Calendar Date into a Sexagesimal Calendar
 * Date
 * 
 * @author mvanbesien
 * 
 */
public class SexagesimalCalendar {

	public static SexagesimalDate getDate() {
		return SexagesimalCalendar.getDate(Calendar.getInstance());

	}

	/**
	 * Returns the Sexagesimal Date for the date passed as parameter
	 * 
	 * @param calendar
	 *            : Calendar Instance
	 * @return Sexagesimal Date
	 */
	public static SexagesimalDate getDate(Calendar calendar) {

		SexagesimalDate date = new SexagesimalDate();
		int day = calendar.get(Calendar.DATE);
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);

		int sYear = year - 2012;
		int nbOfDays = Utils.dayCounter(21, 12, year - 1, day, month, year);
		int nbOfDaysInYear = Utils.isLeapYear(year) ? 366 : 365;
		if (nbOfDays >= nbOfDaysInYear) {
			nbOfDays -= nbOfDaysInYear;
			sYear++;
		}

		int dayNb = nbOfDays % 61 + 1;
		int soixNb = nbOfDays / 61 + 1;

		int WeekdayNb = dayNb == 61 ? 0 : (dayNb - 1) % 6 + 1;
		if (dayNb != 61) {
			date.setOrdinaryDate(WeekdayNb, dayNb, soixNb, sYear);
			return date;
		} else {
			date.setExtraDate(soixNb, sYear);
			return date;
		}
	}
}
