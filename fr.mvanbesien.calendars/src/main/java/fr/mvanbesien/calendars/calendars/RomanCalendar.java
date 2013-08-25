/**
 * $Id: RomanCalendar.java,v 1.2 2008/11/05 09:41:50 maxence Exp $
 * 
 * HISTORY
 * -------
 * $Log: RomanCalendar.java,v $
 * Revision 1.2  2008/11/05 09:41:50  maxence
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

import fr.mvanbesien.calendars.tools.Utils;

/**
 * This Class converts a Gregorian Calendar Date into a Roman Calendar Date.
 * 
 * @author mvanbesien
 * 
 */
public class RomanCalendar {

	/**
	 * Month names (Gen), for the Roman Calendar
	 */
	public static final String[] RO_MENSIS_1 = new String[] { "Ianuariis",
			"Februariis", "Martiis", "Aprilibus", "Maiis", "Iuniis", "Iuliis",
			"Augustis", "Septembribus", "Octobribus", "Novembribus",
			"Decembribus", "Ianuariis" };

	/**
	 * Month names (Abl), for the Roman Calendar
	 */
	public static final String[] RO_MENSIS_2 = new String[] { "Ianuarias",
			"Februarias", "Martias", "Apriles", "Maias", "Iunias", "Iulias",
			"Augustas", "Septembres", "Octobres", "Novembres", "Decembres",
			"Ianuarias" };

	/**
	 * Numbers in Latin, for the Roman Calendar
	 */
	public static final String[] RO_DIES = new String[] { "", "", "Pridie",
			"tertium", "quartum", "quintum", "sextum", "septimum", "octavum",
			"nonum", "decimum", "undecimum", "duodecimum", "tertium decimum",
			"quartum decimum", "quintum decimum", "sextum decimum",
			"septimum decimum", "duodevicesimum", "undevicesimum" };

	/**
	 * Array describing the month type
	 */
	private static final int[] MONTH_TYPE = new int[] { 1, 4, 3, 2, 3, 2, 3, 1,
			2, 3, 2, 1 };

	/**
	 * Returns the Roman Date as String, for the current day
	 * 
	 * @return String containing the Roman Date
	 */
	public static String getDateAsString() {
		return RomanCalendar.getDateAsString(Calendar.getInstance());
	}

	/**
	 * Returns the Roman Date as String, for the date passed as parameter
	 * 
	 * @param calendar
	 *            : Calendar Instance
	 * @return String containing the Roman Date
	 */
	public static String getDateAsString(Calendar calendar) {
		// Let's suppose year starts on Jan 1st... (Julian Calendar !)

		Calendar julCal = Utils.getCalendarCopy(calendar);

		julCal.add(Calendar.DAY_OF_YEAR, -13);
		int year = julCal.get(Calendar.YEAR);
		int rYear = year + 753;
		int date = julCal.get(Calendar.DAY_OF_MONTH);
		int month = julCal.get(Calendar.MONTH);
		int monthTypeIndex = RomanCalendar.MONTH_TYPE[month];

		int kalendis = 1;
		int nonis = 0;
		int idibus = 0;
		int nextKalendis = 0;
		switch (monthTypeIndex) {
		case 1:
			nonis = 5;
			idibus = 13;
			nextKalendis = 32;
			break;
		case 2:
			nonis = 5;
			idibus = 13;
			nextKalendis = 31;
			break;
		case 3:
			nonis = 7;
			idibus = 15;
			nextKalendis = 32;
			break;
		case 4:
			nonis = 5;
			idibus = 13;
			nextKalendis = 29;
			break;
		}

		boolean isBissextile = Utils.isLeapYear(year);
		if (isBissextile && monthTypeIndex == 4 && date == 25)
			return "Ante diem bis sextum Kalendas Februarias "
					+ Utils.convertToRomanChars(rYear) + " A.U.C.";
		if (isBissextile && monthTypeIndex == 4 && date > 13 && date < 25)
			date--;

		if (date == kalendis)
			return "Kalendis " + RomanCalendar.RO_MENSIS_1[month] + " "
					+ Utils.convertToRomanChars(rYear) + " A.U.C.";
		else if (date == nonis)
			return "Nonis " + RomanCalendar.RO_MENSIS_1[month] + " "
					+ Utils.convertToRomanChars(rYear) + " A.U.C.";
		else if (date == idibus)
			return "Idibus " + RomanCalendar.RO_MENSIS_1[month] + " "
					+ Utils.convertToRomanChars(rYear) + " A.U.C.";
		else if (date == nextKalendis)
			return "Pridie Kalendis "
					+ RomanCalendar.RO_MENSIS_1[month + 1]
					+ " "
					+ Utils
							.convertToRomanChars(month == 11 ? rYear + 1
									: rYear) + " A.U.C.";
		else {
			String mens = RomanCalendar.RO_MENSIS_2[month];
			int index = 0;
			String zone = "";
			if (date < nonis) {
				index = nonis - date + 1;
				zone = "Nonas";
			} else if (date < idibus) {
				index = idibus - date + 1;
				zone = "Idus";
			} else if (date < nextKalendis) {
				index = nextKalendis - date + 1;
				zone = "Kalendis";
				mens = RomanCalendar.RO_MENSIS_2[month + 1];
				if (month == 11)
					rYear++;

			}
			String die = index == 2 ? "Pridie" : Utils
					.convertToRomanChars(index);
			return (index != 2 ? "Ante diem " : "") + die + " " + zone + " "
					+ mens + " " + Utils.convertToRomanChars(rYear) + " A.U.C.";
		}
	}
}
