/**
 * $Id: ImladrisCalendar.java,v 1.3 2008/11/05 09:58:35 maxence Exp $
 * 
 * HISTORY : 
 * -------
 * $Log: ImladrisCalendar.java,v $
 * Revision 1.3  2008/11/05 09:58:35  maxence
 * Removed useless method
 *
 * Revision 1.2  2008/11/05 09:27:26  maxence
 * Fixed
 *
 * Revision 1.1  2008/11/05 08:53:41  maxence
 * Saved
 *
 * Revision 1.2  2007/11/20 17:30:31  bezien
 * Removed ICalendar interface
 *
 * Revision 1.1  2007/09/17 16:41:27  bezien
 * Initial Revision
 *
 */
package fr.mvanbesien.calendars.calendars;

import java.util.Calendar;

import fr.mvanbesien.calendars.dates.ImladrisDate;
import fr.mvanbesien.calendars.tools.Utils;

/**
 * This Class converts a Gregorian Calendar Date into a Imladris Calendar Date
 * 
 * @author mvanbesien
 * 
 */
public class ImladrisCalendar {

	public static ImladrisDate getDate() {
		return ImladrisCalendar.getDate(Calendar.getInstance());
	}

	/**
	 * Returns the Imladris Season Date for the day passed as parameter
	 * 
	 * @param calendar
	 *            : Calendar instance
	 * @return Imladris Season Date
	 */
	public static ImladrisDate getDate(Calendar calendar) {
		ImladrisDate date = new ImladrisDate();
		int day = calendar.get(Calendar.DATE);
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);
		int yen = year / 144 + 1;
		int loa = year - (yen - 1) * 144;
		if (loa == 0) {
			loa = 144;
			yen--;
		}
		int days = Utils.dayCounter(1, 1, year, day, month, year);
		int offset = days - 95;
		int nbDecalDueToBissextileYear = (year - 1) % 12 / 4;
		offset = offset - nbDecalDueToBissextileYear;
		
		int nbDaysInImladrisYear = 365;
		if ((year - 1) % 12 == 0)
			nbDaysInImladrisYear = 368;
		
		int relativeDay = 0;
		int gap = nbDaysInImladrisYear - 365;

		if (offset <= 0) {
			offset = offset + nbDaysInImladrisYear;
			loa--;
			if (loa == 0) {
				loa = 144;
				yen--;
			}
		}
		
		if (offset == 1) {
			date.setExtraDate(0, loa, yen);
			return date;
		}
		if (offset > 1 && offset <= 55) {
			relativeDay = offset - 1;
			date.setOrdinaryDate(relativeDay % 6 + 1, relativeDay, 0, loa, yen);
			return date;
		}
		if (offset > 55 && offset <= 127) {
			relativeDay = offset - 55;
			date.setOrdinaryDate(relativeDay % 6 + 1, relativeDay, 1, loa, yen);
			return date;
		}
		if (offset > 127 && offset <= 181) {
			relativeDay = offset - 127;
			date.setOrdinaryDate(relativeDay % 6 + 1, relativeDay, 2, loa, yen);
			return date;
		}
		if (offset > 181 && offset - gap <= 184) {
			relativeDay = offset - 181;
			if (relativeDay == 1) {
				date.setExtraDate(1, loa, yen);
				return date;
			}
			if (relativeDay == 2) {
				date.setExtraDate(2, loa, yen);
				return date;
			}
			if (relativeDay == 3 && gap > 0) {
				date.setExtraDate(3, loa, yen);
				return date;
			}
			if (relativeDay == 4 && gap > 0) {
				date.setExtraDate(4, loa, yen);
				return date;
			}
			if (relativeDay == 5 && gap > 0) {
				date.setExtraDate(5, loa, yen);
				return date;
			}
			if (relativeDay - gap == 3) {
				date.setExtraDate(6, loa, yen);
				return date;
			}
		}
		if (offset - gap > 184 && offset - gap <= 238) {
			relativeDay = offset - gap - 184;
			date.setOrdinaryDate(relativeDay % 6 + 1, relativeDay, 3, loa, yen);
			return date;
		}
		if (offset - gap > 238 && offset - gap <= 310) {
			relativeDay = offset - gap - 238;
			date.setOrdinaryDate(relativeDay % 6 + 1, relativeDay, 4, loa, yen);
			return date;
		}
		if (offset - gap > 310 && offset - gap <= 364) {
			relativeDay = offset - gap - 310;
			date.setOrdinaryDate(relativeDay % 6 + 1, relativeDay, 5, loa, yen);
			return date;
		}
		if (offset - gap == 365) {
			date.setExtraDate(7, loa, yen);
			return date;
		}
		return null;
	}
}
