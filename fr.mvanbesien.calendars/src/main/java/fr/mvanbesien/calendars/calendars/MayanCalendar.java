/**
 * $Id: MayanCalendar.java,v 1.2 2008/11/05 09:27:59 maxence Exp $
 * 
 * HISTORY
 * -------
 * $Log: MayanCalendar.java,v $
 * Revision 1.2  2008/11/05 09:27:59  maxence
 * Fixed
 *
 * Revision 1.1  2008/11/05 08:53:42  maxence
 * Saved
 *
 * Revision 1.2  2007/11/20 17:30:31  bezien
 * Removed ICalendar interface
 *
 * Revision 1.1  2007/08/30 11:12:36  bezien
 * Initial revision
 *
 */
package fr.mvanbesien.calendars.calendars;

import java.util.Calendar;

/**
 * This class converts a gregorian date into the Mayan Calendar
 * 
 * @author mvanbesien
 * 
 */
public class MayanCalendar {

	/**
	 * Haab names, for the Mayan Calendar
	 */
	public static final String[] M_HAABS = new String[] { "Pop", "Uo", "Zip",
			"Zodz", "Zec", "Xul", "Yaxkin", "Mol", "Chen", "Yax", "Zac", "Ceh",
			"Mac", "Kankin", "Muan", "Pax", "Kayab", "Cumku", "Uayeb" };

	/**
	 * Tzolkin names, for the Mayan Calendar
	 */
	public static final String[] M_TZOLKINS = new String[] { "Imix", "Ik",
			"Akbal", "Kan", "Chicchan", "Cimi", "Manik", "Lamat", "Muluc",
			"Oc", "Chuen", "Eb", "Ben", "Ix", "Men", "Cib", "Caban", "Edznab",
			"Cauac", "Ahau" };

	/**
	 * Returns the full Mayan Date as String, for the current day
	 * 
	 * @return full Mayan Date as String
	 */
	public static String getDate() {
		return MayanCalendar.getDate(Calendar.getInstance());
	}

	/**
	 * Returns the full Mayan Date as String for the date passed as parameter
	 * 
	 * @param calendar
	 *            : Calendar Instance
	 * @return full Mayan Date as String
	 */
	public static String getDate(Calendar calendar) {
		return MayanCalendar.getTHDate(calendar) + " "
				+ MayanCalendar.getLongDate(calendar);
	}

	/**
	 * Returns the Long Mayan Date as String, for the current day
	 * 
	 * @return Mayan Long Date as String
	 */
	public static String getLongDate() {
		return MayanCalendar.getLongDate(Calendar.getInstance());
	}

	/**
	 * Returns the Long Mayan Date as String, for the date passed as parameter
	 * 
	 * @param calendar
	 *            : Calendar Instance
	 * @return Mayan Long Date as String
	 */
	public static String getLongDate(Calendar calendar) {
		double julianDay = ClassicCalendar.getJulianDay(calendar);
		long day = new Double(julianDay - 584282.5).longValue();

		int kin = 0;
		int uinal = 0;
		int tun = 0;
		int katun = 0;
		int baktun = 0;
		int pictun = 0;

		baktun = new Long(day / 144000).intValue();
		day = day - baktun * 144000;

		katun = new Long(day / 7200).intValue();
		day = day - katun * 7200;

		tun = new Long(day / 360).intValue();
		day = day - tun * 360;

		uinal = new Long(day / 20).intValue();
		day = day - uinal * 20;

		kin = new Long(day).intValue();

		pictun = baktun / 13;
		baktun = baktun - 13 * pictun;

		return (pictun > 0 ? pictun + "." : "") + baktun + "." + katun + "."
				+ tun + "." + uinal + "." + kin;
	}

	/**
	 * Returns the Tzolkin/Haab Mayan Date as String, for the current day
	 * 
	 * @return Tzolkin/Haab Mayan Date as String
	 */
	public static String getTHDate() {
		return MayanCalendar.getTHDate(Calendar.getInstance());
	}

	/**
	 * Returns the Tzolkin/Haab Mayan Date as String, for date passed as
	 * parameter
	 * 
	 * @param calendar
	 *            : Calendar instance
	 * @return Tzolkin/Haab Mayan Date as String
	 */
	public static String getTHDate(Calendar calendar) {
		double julianDay = ClassicCalendar.getJulianDay(calendar);
		long day = new Double(julianDay - 584283.5).longValue();
		while (day < 0)
			day += 18980;
		String haab = null;
		String tzolkin = null;

		long dayT = day + 160;
		tzolkin = dayT % 13 + 1 + " "
				+ MayanCalendar.M_TZOLKINS[new Long(dayT % 20).intValue()];

		int dayH = new Long((day + 349) % 365).intValue();
		haab = dayH % 20 + " "
				+ MayanCalendar.M_HAABS[new Long(dayH / 20).intValue()];

		return tzolkin + " " + haab;
	}

}
