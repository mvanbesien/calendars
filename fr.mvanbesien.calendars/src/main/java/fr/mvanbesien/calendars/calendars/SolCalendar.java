/**
 * $Id: SolCalendar.java,v 1.3 2008/11/05 09:58:34 maxence Exp $
 * 
 * HISTORY
 * -------
 * $Log: SolCalendar.java,v $
 * Revision 1.3  2008/11/05 09:58:34  maxence
 * Removed useless method
 *
 * Revision 1.2  2008/11/05 09:48:42  maxence
 * Fixed
 *
 * Revision 1.1  2008/11/05 08:53:39  maxence
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

import fr.mvanbesien.calendars.dates.SolDate;
import fr.mvanbesien.calendars.tools.Utils;

/**
 * This Class converts a Gregorian Calendar Date into a Sol Calendar Date.
 * 
 * @author mvanbesien
 * 
 */
public class SolCalendar {

	public static SolDate getDate() {
		return SolCalendar.getDate(Calendar.getInstance());
	}

	/**
	 * Returns the Sol Date for the date passed as parameter
	 * 
	 * @param calendar
	 *            : Calendar Instance
	 * @return Sol Date
	 */
	public static SolDate getDate(Calendar calendar) {

		int[] offsets = new int[13];
		for (int i = 0; i < 13; i++)
			offsets[i] = 28 * i;

		SolDate date = new SolDate();
		int day = calendar.get(Calendar.DATE);
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);

		int nbOfDays = Utils.dayCounter(1, 1, year, day, month, year);

		int fDay;
		int fMonth = 1;
		int fYear = year;
		int fWeekDay;

		int index = 1;

		while (index < 13 && nbOfDays >= offsets[index]) {
			index++;
			fMonth = index;

		}
		fDay = nbOfDays - offsets[fMonth - 1] + 1;

		/*
		 * Convention : In 2006, the beginning of each month is a Sunday. the
		 * gap increases of 1 day per year, 2 days for leap years.
		 */
		int refCycle = (year - 1) / 28;
		int refYear = refCycle * 28 + 1;
		int i = year - refYear;
		int refWeekDay = (i + i / 4) % 7;
		fWeekDay = (refWeekDay + fDay) % 7;
		if (fWeekDay <= 0)
			fWeekDay += 7;
		date.setOrdinaryDate(fWeekDay, fDay, fMonth, fYear);
		return date;

	}
}
