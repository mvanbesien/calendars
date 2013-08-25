/**
 * $Id: RepublicanCalendarDay.java,v 1.1 2008/11/05 10:04:36 maxence Exp $
 * 
 * HISTORY : 
 * -------
 * $Log: RepublicanCalendarDay.java,v $
 * Revision 1.1  2008/11/05 10:04:36  maxence
 * Moved ***Day to days
 *
 * Revision 1.2  2008/11/05 09:41:04  maxence
 * Fixed
 *
 * Revision 1.1  2008/11/05 08:53:41  maxence
 * Saved
 *
 * Revision 1.2  2007/11/20 17:30:31  bezien
 * Removed ICalendar interface
 *
 * Revision 1.1  2007/09/21 10:07:45  bezien
 * Moved from InProgress to Calendars package
 *
 * Revision 1.1  2007/09/20 18:54:36  bezien
 * Initial Revision
 *
 */
package fr.mvanbesien.calendars.days;

import java.io.IOException;
import java.util.Calendar;
import java.util.Properties;

import fr.mvanbesien.calendars.calendars.RepublicanCalendar;
import fr.mvanbesien.calendars.dates.AbstractDate;

/**
 * Returns the Franch Republican calendar day name.
 * 
 * @author mvanbesien
 * 
 */
public class RepublicanCalendarDay {

	/**
	 * Properties contaning Calendar Days
	 */
	private static Properties calendarDays;

	/**
	 * @return the calendar day associated with current date
	 */
	public static String getValue() {
		return RepublicanCalendarDay.getValue(Calendar.getInstance());
	}

	/**
	 * @param calendar
	 *            : Calendar instance
	 * @return the calendar day associated with Calendar passed as parameter
	 */
	public static String getValue(Calendar calendar) {
		AbstractDate date = RepublicanCalendar.getDate(calendar);
		if (RepublicanCalendarDay.calendarDays == null) {
			Properties properties = new Properties();
			try {
				properties
						.load(PataphysiqueCalendarDay.class
								.getResourceAsStream("/RepublicanCalendarDays.properties"));
				RepublicanCalendarDay.calendarDays = properties;
			} catch (IOException e) {
				return null;
			}
		}
		if (RepublicanCalendarDay.calendarDays == null)
			return null;
		else {
			String id = String.format("%02d%02d", date.getMonth(), date
					.getDay());
			return (String) RepublicanCalendarDay.calendarDays.get(id);
		}
	}
}
