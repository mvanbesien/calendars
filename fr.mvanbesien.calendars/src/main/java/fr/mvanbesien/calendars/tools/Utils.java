/**
 * $Id: Utils.java,v 1.4 2009/02/24 10:45:22 maxence Exp $
 * 
 * HISTORY
 * -------
 * $Log: Utils.java,v $
 * Revision 1.4  2009/02/24 10:45:22  maxence
 * MVA : Fixed OrbitalElement reference
 *
 * Revision 1.3  2008/11/05 10:13:35  maxence
 * Deledated image management to another plugin
 *
 * Revision 1.2  2008/11/05 10:00:30  maxence
 * Fixed
 *
 * Revision 1.1  2008/11/05 08:53:38  maxence
 * Saved
 *
 * Revision 1.2  2007/09/05 20:21:22  bezien
 * Added daylight ratio percentage
 *
 * Revision 1.1  2007/08/30 11:12:35  bezien
 * Initial revision
 *
 */
package fr.mvanbesien.calendars.tools;

import java.util.Calendar;

/**
 * The Utilitary Class
 * 
 * @author mvanbesien
 * 
 */
public class Utils {

	/**
	 * Constant to describe one hour length in milliseconds
	 */
	static final long ONE_HOUR = 60 * 60 * 1000L;

	/**
	 * Converts the value passed as parameter to roman chars
	 * 
	 * @param value
	 *            : integer value
	 * @return value as roman chars
	 */
	public static String convertToRomanChars(int value) {
		String result = "";
		boolean isNeg = false;
		if (value < 0) {
			value = -value;
			isNeg = true;
		}
		final String[] units = new String[] { "", "I", "II", "III", "IV", "V",
				"VI", "VII", "VIII", "IX" };
		final String[] tenths = new String[] { "", "X", "XX", "XXX", "XL", "L",
				"LX", "LXX", "LXXX", "XC" };
		final String[] hundreds = new String[] { "", "C", "CC", "CCC", "CD",
				"D", "DC", "DCC", "DCCC", "CM" };
		final String[] thousands = new String[] { "", "M", "MM", "MMM", "MMMM",
				"MMMMM", "?", "?", "?", "?" };
		final String[] over = new String[] { "", "?", "?", "?", "?", "?", "?",
				"?", "?", "?" };

		result = units[value % 10] + result;
		value = value / 10;
		result = tenths[value % 10] + result;
		value = value / 10;
		result = hundreds[value % 10] + result;
		value = value / 10;
		result = thousands[value % 10] + result;
		value = value / 10;
		result = over[value % 10] + result;

		if (isNeg)
			result = "-" + result;

		return result;
	}

	/**
	 * Counts the difference in days between two dates
	 * 
	 * @param beginDay
	 *            : beginning day
	 * @param beginMonth
	 *            : beginning month
	 * @param beginYear
	 *            : beginning year
	 * @param endDay
	 *            : end day
	 * @param endMonth
	 *            : end month
	 * @param endYear
	 *            : end year
	 * @return difference in days
	 */
	public static int dayCounter(int beginDay, int beginMonth, int beginYear,
			int endDay, int endMonth, int endYear) {
		Calendar beginDate = Calendar.getInstance();
		beginDate.set(beginYear, beginMonth - 1, beginDay, 12, 0, 0);
		int beginDOY = beginDate.get(Calendar.DAY_OF_YEAR);

		Calendar endDate = Calendar.getInstance();
		endDate.set(endYear, endMonth - 1, endDay, 12, 0, 0);
		int endDOY = endDate.get(Calendar.DAY_OF_YEAR);

		int gap = 0;

		if (endYear > beginYear)
			gap += (Utils.isLeapYear(beginYear) ? 366 : 365) - beginDOY;
		else
			gap -= beginDOY;

		int tempYear = beginYear;
		while (tempYear + 1 < endYear) {
			gap += Utils.isLeapYear(tempYear) ? 366 : 365;
			tempYear++;
		}

		gap += endDOY;
		return gap;

	}

	public static int dayCounter_old(int beginDay, int beginMonth,
			int beginYear, int endDay, int endMonth, int endYear) {

		Calendar beginDate = Calendar.getInstance();
		beginDate.set(beginYear, beginMonth - 1, beginDay, 0, 0, 0);
		Calendar endDate = Calendar.getInstance();
		endDate.set(endYear, endMonth - 1, endDay, 0, 0, 0);
		Calendar tempDate = Calendar.getInstance();
		tempDate.set(beginYear + 1, 0, 1, 0, 0, 0);
		int gap = 0;

		if (beginYear == endYear) {
			while (beginDate.getTimeInMillis() < endDate.getTimeInMillis()) {
				beginDate.add(Calendar.DAY_OF_YEAR, 1);
				gap++;
			}
			return gap;
		}
		while (beginDate.getTimeInMillis() < tempDate.getTimeInMillis()) {
			beginDate.add(Calendar.DAY_OF_YEAR, 1);
			gap++;
		}
		int tempYear = beginYear;
		while (tempYear < endYear - 1) {
			gap += Utils.isLeapYear(tempYear) ? 366 : 365;
			tempYear++;
		}
		tempDate.set(endYear, 0, 1, 0, 0, 0);
		if (beginYear < endYear)
			while (tempDate.getTimeInMillis() < endDate.getTimeInMillis()) {
				tempDate.add(Calendar.DAY_OF_YEAR, 1);
				gap++;
			}
		return gap;
	}

	/**
	 * Displays the time passed as parameter as hour-time (H:mm)
	 * 
	 * @param localT
	 *            : time
	 * @return hour-time
	 */
	public static String displayAsHours(double localT) {
		boolean isNegative = false;
		if (localT < 0) {
			localT = -localT;
			isNegative = true;
		}
		int hours = new Double(localT % 24).intValue();

		int minutes = new Double((localT - hours) * 60).intValue();

		// int secondes = new Double(
		// (localT - hours - new Double(minutes) / 60) * 3600).intValue();

		return (isNegative ? "-" : "") + Utils.setPrefix(hours, 2) + ":"
				+ Utils.setPrefix(minutes, 2);
	}

	/**
	 * Displays the time passed as parameter as minute-time (m'ss")
	 * 
	 * @param localT
	 *            : time
	 * @return minute-time
	 */
	public static String displayAsMinutes(double localT) {

		boolean isNegative = false;
		if (localT < 0) {
			localT = -localT;
			isNegative = true;
		}
		int minutes = new Double(localT * 60).intValue();

		int seconds = new Double((localT * 60 - minutes) * 60).intValue();
		return (isNegative ? "-" : "") + Utils.setPrefix(minutes, 1) + "'"
				+ Utils.setPrefix(seconds, 2) + "\"";
	}

	/**
	 * Clones a calendar
	 * 
	 * @param calendar
	 *            : Calendar instance
	 * @return clone of Calendar instance
	 */
	public static Calendar getCalendarCopy(Calendar calendar) {
		Calendar calCopy = Calendar.getInstance();
		calCopy.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DATE),
				calendar.get(Calendar.HOUR_OF_DAY), calendar
						.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
		calCopy.setTimeZone(calendar.getTimeZone());
		return calCopy;
	}

	/**
	 * Returns the Calendar date (Gregorian) corresponding to the Julian day
	 * passed as parameter
	 * 
	 * @param julianDay
	 *            : Julian Day
	 * @return Calendar date
	 */
	public static Calendar getCalendarFromJulianDay(double julianDay) {
		// TODO D�calage horaire ?
		Calendar calendar = Calendar.getInstance();
		int t1 = new Double(julianDay + 0.5).intValue();
		int t2 = new Double((t1 - 1867216.25) / 36524.25).intValue();
		int t3 = new Double(t2 / 4).intValue();
		int t4 = new Double(t1 + 1 + t2 - t3).intValue();
		int t5 = new Double(t4 + 1524).intValue();
		int t6 = new Double((t5 - 122.1) / 365.25).intValue();
		int t7 = new Double(365.25 * t6).intValue();
		int t8 = new Double((t5 - t7) / 30.6001).intValue();
		int t9 = new Double(30.6001 * t8).intValue();
		int dayofmonth = new Double(t5 - t7 - t9).intValue();
		int month = new Double(t8 - 1).intValue();
		if (month > 12)
			month += 12;
		int year = new Double(t6 - 4715).intValue();
		if (month > 2)
			year--;
		Double juliantime = new Double(julianDay
				- new Double(julianDay).intValue());
		int hours = new Double(juliantime * 24).intValue();
		juliantime = (juliantime * 24 - hours) / 24;
		int minutes = new Double(juliantime * 60 * 24).intValue();
		juliantime = (juliantime * 60 * 24 - minutes) / 60 / 24;
		int seconds = new Double(juliantime * 60 * 60 * 24).intValue();
		calendar.set(year, month - 1, dayofmonth, hours, minutes, seconds);
		return calendar;
	}

	/**
	 * Returns if year passed as parameter is a leap year
	 * 
	 * @param year
	 *            : year
	 * @return true if year is leap year, false otherwise
	 */
	public static boolean isLeapYear(int year) {

		// 1. les ann�es divisibles par 4 mais non divisibles par 100
		// 2. les ann�es divisibles par 400
		return year % 400 == 0 || year % 4 == 0 && year % 100 != 0;
	}

	/**
	 * Returns the french ordinal for the value passed as parameter
	 * 
	 * @param value
	 *            : integer value
	 * @return ordinal
	 */
	public static String ordinal(int value) {
		return Utils.ordinal(value, false);
	}

	/**
	 * Returns the ordinal corresponding to the value
	 * 
	 * @param value
	 *            : integer value
	 * @param english
	 *            : true to get the english ordinal, false to get the french one
	 * @return ordinal
	 */
	public static String ordinal(int value, boolean english) {
		if (english) {
			String ord = "th";
			if (value % 10 == 1 && value % 100 / 10 != 1)
				ord = "st";
			else if (value % 10 == 2 && value % 100 / 10 != 1)
				ord = "nd";
			else if (value % 10 == 3 && value % 100 / 10 != 1)
				ord = "rd";
			return value + ord;
		} else {
			String ord = "�me";
			if (value == 1)
				ord = "er";
			return value + ord;
		}
	}

	/**
	 * Adds 0-digits to the value if number length is shorter than size
	 * 
	 * @param value
	 *            : integer value
	 * @param size
	 *            : number size
	 * @return value with prefix, as String
	 */
	public static String setPrefix(int value, int size) {
		String result = value + "";
		while (result.length() < size)
			result = "0" + result;
		return result;
	}

}
