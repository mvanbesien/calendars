/**
 * $Id: SeasonCalendar.java,v 1.1 2008/11/05 08:53:41 maxence Exp $
 * 
 * HISTORY
 * -------
 * $Log: SeasonCalendar.java,v $
 * Revision 1.1  2008/11/05 08:53:41  maxence
 * Saved
 *
 * Revision 1.4  2007/12/20 11:16:25  bezien
 * Added next season date methods
 *
 * Revision 1.3  2007/11/20 17:30:31  bezien
 * Removed ICalendar interface
 *
 * Revision 1.2  2007/09/24 20:14:34  bezien
 * Fixed bug : season date
 *
 * Revision 1.1  2007/08/30 11:12:36  bezien
 * Initial revision
 *
 */
package fr.mvanbesien.calendars.calendars;

import java.util.Calendar;

import fr.mvanbesien.calendars.tools.Utils;

/**
 * Computes equinoxes and solstices dates
 * 
 * @author mvanbesien
 * 
 */
public class SeasonCalendar {

	/**
	 * Returns the Calendar instance of the Autumn Equinox Date, for the current
	 * year
	 * 
	 * @return Calendar Instance
	 */
	public static Calendar getAutumnEquinox() {
		return SeasonCalendar.getAutumnEquinox(Calendar.getInstance());
	}

	/**
	 * Returns the Calendar instance of the Autumn Equinox, for the year of the
	 * date passed as parameter
	 * 
	 * @param Calendar
	 *            instance
	 * @return Calendar instance
	 */
	public static Calendar getAutumnEquinox(Calendar calendar) {
		int year = calendar.get(Calendar.YEAR);
		double m = (new Double(year).doubleValue() - 2000) / 1000;
		double value = 2451810.21715 + 365242.01767 * m - 0.11575 * m * m
				+ 0.00337 * m * m * m + 0.00078 * m * m * m * m;
		Calendar result = Utils.getCalendarFromJulianDay(value);

		return result;
	}

	/**
	 * Returns the next season change date for the current date
	 * 
	 * @return next season change date as Calendar
	 */
	public static String getNextSeasonDate() {
		return SeasonCalendar.getNextSeasonDate(Calendar.getInstance());
	}

	/**
	 * Returns the next season change date for the date passed as parameter
	 * 
	 * @param instance
	 *            : Calendar instance
	 * @return next season change date as Calendar
	 */
	public static String getNextSeasonDate(Calendar instance) {
		Calendar[] calendars = new Calendar[] {
				SeasonCalendar.getVernalEquinox(instance),
				SeasonCalendar.getSummerSolstice(instance),
				SeasonCalendar.getAutumnEquinox(instance),
				SeasonCalendar.getWinterSolstice(instance) };

		long[] dates = new long[calendars.length];

		for (int i = 0; i < calendars.length; i++) {
			calendars[i].set(Calendar.HOUR_OF_DAY, 0);
			calendars[i].set(Calendar.MINUTE, 0);
			calendars[i].set(Calendar.SECOND, 0);
			dates[i] = calendars[i].getTimeInMillis();
		}

		long value;
		Calendar c = Utils.getCalendarCopy(instance);
		long now = instance.getTimeInMillis();
		if (now < dates[0]) {
			value = dates[0] - now;
			value = value / 86400000 + 1;
			return value + " day" + (value > 1 ? "s" : "")
					+ " till next Spring";
		} else if (now >= dates[0] && now < dates[1]) {
			value = dates[1] - now;
			value = value / 86400000 + 1;
			return value + " day" + (value > 1 ? "s" : "")
					+ " till next Summer";
		} else if (now >= dates[1] && now < dates[2]) {
			value = dates[2] - now;
			value = value / 86400000 + 1;
			return value + " day" + (value > 1 ? "s" : "") + " till next Fall";
		} else if (now >= dates[2] && now < dates[3]) {
			value = dates[3] - now;
			value = value / 86400000 + 1;
			return value + " day" + (value > 1 ? "s" : "")
					+ " till next Winter";
		} else if (now >= dates[3]) {
			c.add(Calendar.YEAR, 1);
			value = SeasonCalendar.getVernalEquinox(c).getTimeInMillis() - now;
			value = value / 86400000 + 1;
			return value + " day" + (value > 1 ? "s" : "")
					+ " till next Spring";
		}
		return "";
	}

	/**
	 * Returns the Season Date of the current date
	 * 
	 * @return String containing the Season date
	 */
	public static String getSeasonDate() {
		return SeasonCalendar.getSeasonDate(Calendar.getInstance());
	}

	/**
	 * Returns the Season Date of the date passed as parameter
	 * 
	 * @param calendar
	 *            : Calendar Instance
	 * @return String contaning the Season date
	 */
	public static String getSeasonDate(Calendar calendar) {

		Calendar[] calendars = new Calendar[] {
				SeasonCalendar.getVernalEquinox(calendar),
				SeasonCalendar.getSummerSolstice(calendar),
				SeasonCalendar.getAutumnEquinox(calendar),
				SeasonCalendar.getWinterSolstice(calendar) };

		long[] dates = new long[calendars.length];

		for (int i = 0; i < calendars.length; i++) {
			calendars[i].set(Calendar.HOUR_OF_DAY, 0);
			calendars[i].set(Calendar.MINUTE, 0);
			calendars[i].set(Calendar.SECOND, 0);
			dates[i] = calendars[i].getTimeInMillis();
		}

		long value;
		Calendar c = Utils.getCalendarCopy(calendar);
		long now = calendar.getTimeInMillis();
		if (now < dates[0]) {
			c.add(Calendar.YEAR, -1);
			value = now - SeasonCalendar.getWinterSolstice(c).getTimeInMillis();
			value = value / 86400000;
			return Utils.ordinal(new Long(value).intValue() + 1, true)
					+ " day of Winter";
		} else if (now >= dates[0] && now < dates[1]) {
			value = now - dates[0];
			value = value / 86400000;
			return Utils.ordinal(new Long(value).intValue() + 1, true)
					+ " day of Spring";
		} else if (now >= dates[1] && now < dates[2]) {
			value = now - dates[1];
			value = value / 86400000;
			return Utils.ordinal(new Long(value).intValue() + 1, true)
					+ " day of Summer";
		} else if (now >= dates[2] && now < dates[3]) {
			value = now - dates[2];
			value = value / 86400000;
			return Utils.ordinal(new Long(value).intValue() + 1, true)
					+ " day of Fall";
		} else if (now >= dates[3]) {
			value = now - dates[3];
			value = value / 86400000;
			return Utils.ordinal(new Long(value).intValue() + 1, true)
					+ " day of Winter";
		}
		return "";
	}

	/**
	 * Returns the Calendar instance of the Summer Solstice Date, for the
	 * current year
	 * 
	 * @return Calendar Instance
	 */
	public static Calendar getSummerSolstice() {
		return SeasonCalendar.getSummerSolstice(Calendar.getInstance());
	}

	/**
	 * Returns the Calendar instance of the Summer Solstice, for the year of the
	 * date passed as parameter
	 * 
	 * @param Calendar
	 *            instance
	 * @return Calendar instance
	 */
	public static Calendar getSummerSolstice(Calendar calendar) {
		int year = calendar.get(Calendar.YEAR);
		double m = (new Double(year).doubleValue() - 2000) / 1000;
		double value = 2451716.56767 + 365241.62603 * m + 0.00325 * m * m
				+ 0.00888 * m * m * m - 0.00030 * m * m * m * m;
		Calendar result = Utils.getCalendarFromJulianDay(value);

		return result;
	}

	/**
	 * Returns the Calendar instance of the Vernal Equinox Date, for the current
	 * year
	 * 
	 * @return Calendar Instance
	 */
	public static Calendar getVernalEquinox() {
		return SeasonCalendar.getVernalEquinox(Calendar.getInstance());
	}

	/**
	 * Returns the Calendar instance of the Vernal Equinox, for the year of the
	 * date passed as parameter
	 * 
	 * @param Calendar
	 *            instance
	 * @return Calendar instance
	 */
	public static Calendar getVernalEquinox(Calendar calendar) {
		int year = calendar.get(Calendar.YEAR);
		double m = (new Double(year).doubleValue() - 2000) / 1000;
		double value = 2451623.80984 + 365242.37404 * m + 0.05169 * m * m
				- 0.00411 * m * m * m - 0.00057 * m * m * m * m;
		Calendar result = Utils.getCalendarFromJulianDay(value);

		return result;
	}

	/**
	 * Returns the Calendar instance of the Winter Solstice, for the year of the
	 * date passed as parameter
	 * 
	 * @param Calendar
	 *            instance
	 * @return Calendar instance
	 */
	public static Calendar getWinterSolstice() {
		return SeasonCalendar.getWinterSolstice(Calendar.getInstance());
	}

	/**
	 * Returns the Calendar instance of the Winter Solstice, for the year of the
	 * date passed as parameter
	 * 
	 * @param Calendar
	 *            instance
	 * @return Calendar instance
	 */
	public static Calendar getWinterSolstice(Calendar calendar) {
		int year = calendar.get(Calendar.YEAR);
		double m = (new Double(year).doubleValue() - 2000) / 1000;
		double value = 2451900.05952 + 365242.74049 * m - 0.06223 * m * m
				- 0.00823 * m * m * m + 0.00032 * m * m * m * m;
		Calendar result = Utils.getCalendarFromJulianDay(value);

		return result;
	}
}
