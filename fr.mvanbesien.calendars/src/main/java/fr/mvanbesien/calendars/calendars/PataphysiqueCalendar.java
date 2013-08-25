/**
 * $Id: PataphysiqueCalendar.java,v 1.3 2008/11/05 09:58:35 maxence Exp $
 * 
 * HISTORY 
 * -------
 * $Log: PataphysiqueCalendar.java,v $
 * Revision 1.3  2008/11/05 09:58:35  maxence
 * Removed useless method
 *
 * Revision 1.2  2008/11/05 09:36:23  maxence
 * Fixed
 *
 * Revision 1.1  2008/11/05 08:53:40  maxence
 * Saved
 *
 * Revision 1.1  2008/02/04 19:20:46  bezien
 * Initial Revision of Pataphysique and Discordian calendars
 *
 */
package fr.mvanbesien.calendars.calendars;

import java.util.Calendar;

import fr.mvanbesien.calendars.dates.PataphysiqueDate;
import fr.mvanbesien.calendars.tools.Utils;

public class PataphysiqueCalendar {

	public static PataphysiqueDate getDate() {
		return PataphysiqueCalendar.getDate(Calendar.getInstance());
	}

	public static PataphysiqueDate getDate(Calendar instance) {

		Calendar base = Calendar.getInstance();
		base.set(instance.get(Calendar.YEAR), 8, 8, 0, 0, 0);
		base.set(Calendar.MILLISECOND, 0);

		if (instance.getTimeInMillis() < base.getTimeInMillis())
			base.add(Calendar.YEAR, -1);

		int dayInYear = Utils.dayCounter(base.get(Calendar.DAY_OF_MONTH), base
				.get(Calendar.MONTH) + 1, base.get(Calendar.YEAR), instance
				.get(Calendar.DAY_OF_MONTH), instance.get(Calendar.MONTH) + 1,
				instance.get(Calendar.YEAR));

		boolean isLeap = Utils.isLeapYear(instance.get(Calendar.YEAR));

		int dayIndex;
		int monthIndex;
		int yearIndex;

		monthIndex = dayInYear / 28;
		dayIndex = dayInYear % 28 + 1;
		yearIndex = base.get(Calendar.YEAR) - 1872;

		if (isLeap && monthIndex > 5) {
			dayIndex--;
			if (dayIndex == 0) {
				monthIndex--;
				dayIndex = monthIndex == 5 ? 29 : 28;
			}
		}

		if (monthIndex > 10) {
			dayIndex--;
			if (dayIndex == 0) {
				monthIndex--;
				dayIndex = monthIndex == 10 ? 29 : 28;
			}
		}

		int weekDay = dayIndex == 29 ? 7 : (dayIndex + 5) % 7;

		PataphysiqueDate date = new PataphysiqueDate();
		date.setOrdinaryDate(weekDay, dayIndex, monthIndex, yearIndex);
		return date;
	}

}
