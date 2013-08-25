/**
 * $Id: AztecCalendar.java,v 1.3 2008/11/05 09:58:35 maxence Exp $
 * 
 * HISTORY
 * -------
 * $Log: AztecCalendar.java,v $
 * Revision 1.3  2008/11/05 09:58:35  maxence
 * Removed useless method
 *
 * Revision 1.2  2008/11/05 09:14:54  maxence
 * Fixed
 *
 * Revision 1.1  2008/11/05 08:53:41  maxence
 * Saved
 *
 * Revision 1.4  2008/02/13 19:28:08  bezien
 * Fixed for dates before 1500
 *
 * Revision 1.3  2007/11/20 17:30:31  bezien
 * Removed ICalendar interface
 *
 * Revision 1.2  2007/09/17 16:40:41  bezien
 * Removed main() method
 *
 * Revision 1.1  2007/08/30 11:12:35  bezien
 * Initial revision
 *
 */
package fr.mvanbesien.calendars.calendars;

import java.util.Calendar;

import fr.mvanbesien.calendars.tools.Utils;

/**
 * This class converts Gregorian dates into the Aztec Calendar
 * 
 * @author mvanbesien
 * 
 */
public class AztecCalendar {

	/**
	 * Weekday names, for the Aztec Calendar
	 */
	public static final String[] A_WEEKDAYS = new String[] { "Cipactli",
			"Ehecatl", "Calli", "Cuetzpallin", "Coatl", "Miquiztli", "Mazatl",
			"Tochtli", "Atl", "Itzcuintli", "Ozomatli", "Malinalli", "Acatl",
			"Ocelotl", "Cuauhtli", "Cozcaquautli", "Ollin", "Tecpatl",
			"Quiauitl", "Xochitl" };

	/**
	 * Sign names, for the Aztec Calendar
	 */
	public static final String[] A_SIGNS = new String[] { "Tocltli", "Acatl",
			"Tecpatl", "Calli" };

	/**
	 * Month names, for the Aztec Calendar
	 */
	public static final String[] A_MONTHS = new String[] { "Atlcahuallo",
			"Tlacaxipehualitzi", "Tozoztontli", "Huey Tozoztli", "Toxcatl",
			"Etzalculizti", "Tecuilhuitontli", "Huey Tecuilhuitl",
			"Tlaxochimaco", "Xocotl Huetzi", "Ochpaniztli", "Teotelco",
			"Tepeilhuitl", "Quecholli", "Panquetzaliztli", "Atemozli",
			"Tititl", "Izcalli", "Nemontemi" };

	/**
	 * Returns the Aztec Date for the date passed as parameter
	 * 
	 * @param calendar
	 *            : Calendar Instance
	 * @return the Aztec Date
	 */
	public static String getSacredDate(Calendar calendar) {
		int julianDay = new Double(ClassicCalendar.getJulianDay(
				calendar) - 0.5).intValue();
		int origin = 163;
		int aztecDay = julianDay - origin;

		int tonalliDaySign = aztecDay % 20;
		int tonalliDayNumber = aztecDay % 13;

		int trecenasIndex = ((tonalliDaySign + 20) - tonalliDayNumber) % 20;

		return String.format("1-%s %s-%s",
				AztecCalendar.A_WEEKDAYS[trecenasIndex], tonalliDayNumber + 1,
				AztecCalendar.A_WEEKDAYS[tonalliDaySign]);

	}

	public static String getSolarDate(Calendar calendar) {
		int year = calendar.get(Calendar.YEAR);
		int dayInYear = calendar.get(Calendar.DAY_OF_YEAR);
		int dateOffset = 31 + 28 + (Utils.isLeapYear(year) ? 1 : 0);

		int aztecDay = dayInYear - dateOffset;
		if (aztecDay < 0) {
			year--;
			aztecDay = aztecDay + 365 + (Utils.isLeapYear(year) ? 1 : 0);
		}

		int month = aztecDay / 20;
		int dayInMonth = aztecDay % 20;

		int yearOffset = 50;
		int yearNumber = (year - yearOffset) % 13;
		int yearSign = (year - yearOffset) % 4;

		while (yearNumber < 0)
			yearNumber = yearNumber + 13;
		while (yearSign < 0)
			yearSign = yearSign + 4;

		return String.format("%s %s %s-%s", dayInMonth + 1, A_MONTHS[month],
				yearNumber + 1, AztecCalendar.A_SIGNS[yearSign]);
	}

	public static String getDate(Calendar calendar) {
		return String.format("Solar date: " + getSolarDate(calendar)
				+ ", Sacred date: " + getSacredDate(calendar));
	}

}
