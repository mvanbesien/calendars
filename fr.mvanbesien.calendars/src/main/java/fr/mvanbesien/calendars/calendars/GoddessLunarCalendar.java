/**
 * $Id: GoddessLunarCalendar.java,v 1.3 2008/11/05 09:58:35 maxence Exp $
 * 
 * HISTORY
 * -------
 * $Log: GoddessLunarCalendar.java,v $
 * Revision 1.3  2008/11/05 09:58:35  maxence
 * Removed useless method
 *
 * Revision 1.2  2008/11/05 09:21:51  maxence
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

import fr.mvanbesien.calendars.dates.GoddessLunarDate;

/**
 * Computes the date in the Goddess Lunar Calendar
 * 
 * @author mvanbesien
 * 
 */
public class GoddessLunarCalendar {

	/**
	 * Returns the additional days amount for a year
	 * 
	 * @param year
	 * @return additional days amount
	 */
	private static int additionalDays(int year) {
		if (GoddessLunarCalendar.sum(year) == 2)
			return 28;
		if (GoddessLunarCalendar.sum(year) == 22)
			return 29;
		if (GoddessLunarCalendar.sum(year) == 23 || year % 9 == 0)
			return 30;
		if (year % 3 == 0 && year % 9 != 0)
			return 31;
		return 29;
	}

	public static GoddessLunarDate getDate() {
		return GoddessLunarCalendar.getDate(Calendar.getInstance());
	}

	/**
	 * Returns the Goddess Lunar Date for the date passed as parameter
	 * 
	 * @param calendar
	 *            : Calendar Instance
	 * @return the Goddess Lunar Date
	 */
	public static GoddessLunarDate getDate(Calendar calendar) {
		GoddessLunarDate date = new GoddessLunarDate();
		int days = new Double(ClassicCalendar.getJulianDay(calendar) + 0.5)
				.intValue() + 829029 + 1;

		int gEra = days / 616894;
		days = days - gEra * 616894;

		int gYear = 0;
		int gMonth = 0;
		int gDay = 0;
		int gWeekday = (days - 2) % 7;
		int thisYearNbOfDays = 0;
		while (days > thisYearNbOfDays) {
			gYear++;
			days -= thisYearNbOfDays;
			thisYearNbOfDays = 6 * 59;
			if (GoddessLunarCalendar.isLong(gYear))
				thisYearNbOfDays += GoddessLunarCalendar.additionalDays(gYear);
		}
		int[] months = new int[] { 29, 30, 29, 30, 29, 30, 29, 30, 29, 30, 29,
				30, 29 };
		if (GoddessLunarCalendar.isLong(gYear))
			months[0] = GoddessLunarCalendar.additionalDays(gYear);
		while (days > months[gMonth]) {
			days -= months[gMonth];
			gMonth++;
		}
		gMonth++;
		gDay = days;
		date.setOrdinaryDate(gWeekday, gDay, gMonth, gYear, gEra);
		return date;
	}

	/**
	 * Returns if a year is long or not, for a year passed as parameter
	 * 
	 * @param year
	 * @return true it year is long, false otherwise
	 */
	private static boolean isLong(int year) {
		boolean result1 = year % 3 == 0;
		boolean result2 = GoddessLunarCalendar.sum(year) == 2
				|| GoddessLunarCalendar.sum(year) == 22
				|| GoddessLunarCalendar.sum(year) == 23;

		return (result1 || result2) && !(result1 && result2);
	}

	/**
	 * Returns the sum of the digits of a year
	 * 
	 * @param year
	 * @return the sum
	 */
	private static int sum(int year) {
		int i = year;
		int sum = 0;
		while (i > 0) {
			sum += i % 10;
			i = i / 10;
		}
		return sum;
	}
}
