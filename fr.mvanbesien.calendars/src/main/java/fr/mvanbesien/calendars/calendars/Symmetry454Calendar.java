/**
 * $Id: Symmetry454Calendar.java,v 1.3 2008/11/05 09:58:35 maxence Exp $
 * 
 * HISTORY
 * -------
 * $Log: Symmetry454Calendar.java,v $
 * Revision 1.3  2008/11/05 09:58:35  maxence
 * Removed useless method
 *
 * Revision 1.2  2008/11/05 09:51:49  maxence
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

import fr.mvanbesien.calendars.dates.Symmetry454Date;

/**
 * This class converts a Gregorian Calendar to the Symmetry454 Calendar
 * 
 * @author mvanbesien
 * 
 */
public class Symmetry454Calendar {

	public static Symmetry454Date getDate() {
		return Symmetry454Calendar.getDate(Calendar.getInstance());
	}

	/**
	 * Converts the Date passed as parameter into the Symmetry454 Calendar
	 * 
	 * @param calendar
	 *            : Date
	 * @return Converted Symmetry454 date
	 */
	public static Symmetry454Date getDate(Calendar calendar) {
		double referenceDay = ClassicCalendar.getJulianDay(calendar) - 1721424.5;
		long trunkedReferenceDay = new Double(referenceDay).intValue();

		long cycle = trunkedReferenceDay / (293 * 365 + 71);
		long day = trunkedReferenceDay % (293 * 365 + 71);

		long year = 293 * cycle;

		boolean loop = true;
		while (loop) {
			year++;
			int nbDaysInCurrentYear = (52 * year + 166) % 293 < 52 ? 371 : 364;
			if (day > nbDaysInCurrentYear)
				day -= nbDaysInCurrentYear;
			else
				loop = false;
		}

		int month = 1;
		loop = true;
		while (loop && month != 12) {
			int daysInMonth = month % 3 - 2 == 0 ? 35 : 28;
			if (day > daysInMonth) {
				month++;
				day -= daysInMonth;
			} else
				loop = false;
		}

		int weekDay = new Long(day).intValue() % 7;

		Symmetry454Date date = new Symmetry454Date();
		date.setOrdinaryDate(weekDay + 1, new Long(day).intValue(), month,
				new Long(year).intValue());

		return date;
	}

}
