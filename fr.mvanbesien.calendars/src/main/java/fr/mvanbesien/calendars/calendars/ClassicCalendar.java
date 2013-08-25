/**
 * $Id: ClassicCalendar.java,v 1.4 2009/02/24 10:45:22 maxence Exp $
 * 
 * HISTORY
 * -------
 * $Log: ClassicCalendar.java,v $
 * Revision 1.4  2009/02/24 10:45:22  maxence
 * MVA : Fixed OrbitalElement reference
 *
 * Revision 1.3  2008/11/05 09:58:35  maxence
 * Removed useless method
 *
 * Revision 1.2  2008/11/05 09:14:54  maxence
 * Fixed
 *
 * Revision 1.1  2008/11/05 08:53:40  maxence
 * Saved
 *
 * Revision 1.3  2007/11/20 17:30:31  bezien
 * Removed ICalendar interface
 *
 * Revision 1.2  2007/09/21 10:07:23  bezien
 * Fixed bug in Week Date
 *
 * Revision 1.1  2007/08/30 11:12:35  bezien
 * Initial revision
 *
 */
package fr.mvanbesien.calendars.calendars;

import java.util.Calendar;

import fr.mvanbesien.calendars.dates.ClassicDate;
import fr.mvanbesien.calendars.tools.Utils;

/**
 * Displays a Gregorian Calendar Date.
 * 
 * @author mvanbesien
 * 
 */
public class ClassicCalendar {

	public static ClassicDate getDate() {
		return ClassicCalendar.getDate(Calendar.getInstance());
	}

	/**
	 * Returns the Gregorian Date for the date passed as parameter
	 * 
	 * @param calendar
	 *            : Calendar Instance
	 * @return Gregorian Date
	 */
	public static ClassicDate getDate(Calendar calendar) {
		ClassicDate date = new ClassicDate();
		int weekDay = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (weekDay < 1)
			weekDay = weekDay + 7;

		date.setOrdinaryDate(weekDay, calendar.get(Calendar.DAY_OF_MONTH),
				calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));
		return date;
	}

	/**
	 * Returns the day in year for the current day
	 * 
	 * @return day in year
	 */
	public static int getDayInYear() {
		return ClassicCalendar.getDayInYear(Calendar.getInstance());
	}

	/**
	 * Returns the day in year for the date passed as parameter
	 * 
	 * @param calendar
	 *            : Calendar Instance
	 * @return day in year
	 */
	public static int getDayInYear(Calendar calendar) {
		return calendar.get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * Returns the day position in year for the current day
	 * 
	 * @return day position in year
	 */
	public static String getDayPositionInYear() {
		return ClassicCalendar.getDayPositionInYear(Calendar.getInstance());
	}

	/**
	 * Returns the day position in year for the date passed as parameter
	 * 
	 * @param calendar
	 *            : Calendar Instance
	 * @return day position in year
	 */
	public static String getDayPositionInYear(Calendar calendar) {
		int nbOfDays = 365 + (Utils.isLeapYear(calendar.get(Calendar.YEAR)) ? 1
				: 0);
		return "("
				+ Utils.setPrefix(ClassicCalendar.getDayInYear(calendar), 3)
				+ ","
				+ Utils.setPrefix((nbOfDays - ClassicCalendar
						.getDayInYear(calendar)), 3) + ")";
	}

	/**
	 * Returns the julian day of the current day
	 * 
	 * @return julian day
	 */
	public static double getJulianDay() {
		return ClassicCalendar.getJulianDay(Calendar.getInstance());
	}

	/**
	 * Returns the julian day for the date passed as parameter
	 * 
	 * @param calendar
	 *            : Calendar Instance
	 * @return julian day
	 */
	public static double getJulianDay(Calendar calendar) {
		Double time = new Double(calendar.getTimeInMillis());
		time -= calendar.get(Calendar.ZONE_OFFSET)
				+ calendar.get(Calendar.DST_OFFSET);
		return time / 86400000 + 2440587.5;
	}

	/**
	 * Returns the ISO Q-Date for the current day
	 * 
	 * @return ISO Q-Date
	 */
	public static String getQDate() {
		return ClassicCalendar.getQDate(Calendar.getInstance());
	}

	/**
	 * Returns the ISO Q-Date for the date passed as parameter
	 * 
	 * @param calendar
	 *            : Calendar Instance
	 * @return ISO Q-Date
	 */
	public static String getQDate(Calendar calendar) {
		int day = calendar.get(Calendar.DATE);
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);

		int tm = (month - 1) / 3 + 1;
		int dm = 0;
		switch (tm) {
		case 1:
			dm = Utils.dayCounter(1, 1, year, day, month, year) + 1;
			break;
		case 2:
			dm = Utils.dayCounter(1, 4, year, day, month, year) + 1;
			break;
		case 3:
			dm = Utils.dayCounter(1, 7, year, day, month, year) + 1;
			break;
		case 4:
			dm = Utils.dayCounter(1, 10, year, day, month, year) + 1;
			break;
		}
		return year + "-Q" + tm + "-" + Utils.setPrefix(dm, 2);
	}

	/**
	 * Returns the ISO Week-Date for the current day
	 * 
	 * @return ISO Week-Date
	 */
	public static String getWeekDate() {
		return ClassicCalendar.getWeekDate(Calendar.getInstance());
	}

	/**
	 * Returns the ISO Week-Date for the date passed as parameter
	 * 
	 * @param calendar
	 *            : Calendar Instance
	 * @return ISO Week-Date
	 */
	public static String getWeekDate(Calendar calendar) {
		int wk = calendar.get(Calendar.WEEK_OF_YEAR);
		int yr = calendar.get(Calendar.YEAR);
		int mt = calendar.get(Calendar.MONTH);
		int d = calendar.get(Calendar.DAY_OF_WEEK);
		d = d - 2;
		if (d < 0)
			d = d + 7;
		d++;
		if (wk > 10 && mt == 0)
			yr--;
		if (wk < 10 && mt == 11)
			yr++;
		return yr + "-W" + Utils.setPrefix(wk, 2) + "-" + d;

	}

	/**
	 * Returns the week number for the current day
	 * 
	 * @return week number
	 */
	public static int getWeekNumber() {
		return ClassicCalendar.getWeekNumber(Calendar.getInstance());
	}

	/**
	 * Returns the week number for the date passed as parameter
	 * 
	 * @param calendar
	 *            : Calendar Instance
	 * @return week number
	 */
	public static int getWeekNumber(Calendar calendar) {
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}
}
