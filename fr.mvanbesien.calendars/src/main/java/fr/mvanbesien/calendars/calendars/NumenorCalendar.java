/**
 * $Id: NumenorCalendar.java,v 1.3 2008/11/05 09:58:34 maxence Exp $
 * 
 * HISTORY : 
 * -------
 * $Log: NumenorCalendar.java,v $
 * Revision 1.3  2008/11/05 09:58:34  maxence
 * Removed useless method
 *
 * Revision 1.2  2008/11/05 09:32:44  maxence
 * Fixed
 *
 * Revision 1.1  2008/11/05 08:53:41  maxence
 * Saved
 *
 * Revision 1.3  2007/11/20 17:30:31  bezien
 * Removed ICalendar interface
 *
 * Revision 1.2  2007/10/08 16:26:38  bezien
 * Fixed Day of the Week
 *
 * Revision 1.1  2007/09/17 16:41:27  bezien
 * Initial Revision
 *
 */
package fr.mvanbesien.calendars.calendars;

import java.util.Calendar;

import fr.mvanbesien.calendars.dates.NumenorDate;
import fr.mvanbesien.calendars.tools.Utils;

public class NumenorCalendar {

	public static NumenorDate getDate() {
		return NumenorCalendar.getDate(Calendar.getInstance());
	}

	/**
	 * Returns the Numenor Date for the day passed as parameter
	 * 
	 * @param calendar
	 *            : Calendar Instance
	 * @return Numenor Date
	 */
	public static NumenorDate getDate(Calendar calendar) {

		NumenorDate date = new NumenorDate();
		int day = calendar.get(Calendar.DATE);
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);
		int days = Utils.dayCounter(1, 1, year, day, month, year) + 1;

		int offset = days + 9;

		int nbOfDaysInYear = Utils.isLeapYear(year) ? 366 : 365;
		if (offset >= nbOfDaysInYear) {
			offset = offset - nbOfDaysInYear;
			year++;
		}

		if (offset == 0) {
			date.setExtraDate(0, year);
			return date;
		}
		offset--;
		if (Utils.isLeapYear(year) && offset == 182) {
			date.setExtraDate(5, year);
			return date;
		} else if (Utils.isLeapYear(year) && offset > 182)
			offset--;

		int dayInPeriod = offset % 91;
		int period = offset / 91;
		if (dayInPeriod == 90)
			date.setExtraDate(period + 1, year);
		else
			date.setOrdinaryDate(offset % 7 + 1, dayInPeriod % 30 + 1, period
					* 3 + dayInPeriod / 30, year);
		return date;
	}
}
