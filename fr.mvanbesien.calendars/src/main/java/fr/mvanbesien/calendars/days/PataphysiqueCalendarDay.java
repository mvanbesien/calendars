package fr.mvanbesien.calendars.days;

import java.io.IOException;
import java.util.Calendar;
import java.util.Properties;

import fr.mvanbesien.calendars.calendars.PataphysiqueCalendar;
import fr.mvanbesien.calendars.dates.AbstractDate;

public class PataphysiqueCalendarDay {

	/**
	 * Properties contaning Calendar Days
	 */
	private static Properties calendarDays;

	/**
	 * @return the calendar day associated with current date
	 */
	public static String getValue() {
		return PataphysiqueCalendarDay.getValue(Calendar.getInstance());
	}

	/**
	 * @param calendar
	 *            : Calendar instance
	 * @return the calendar day associated with Calendar passed as parameter
	 */
	public static String getValue(Calendar calendar) {
		AbstractDate date = PataphysiqueCalendar.getDate(calendar);
		if (PataphysiqueCalendarDay.calendarDays == null) {
			Properties properties = new Properties();
			try {
				properties
						.load(PataphysiqueCalendarDay.class
								.getResourceAsStream("/PataphysiqueCalendarDays.properties"));
				PataphysiqueCalendarDay.calendarDays = properties;
			} catch (IOException e) {
				return null;
			}
		}
		if (PataphysiqueCalendarDay.calendarDays == null)
			return null;
		else {
			String id = String.format("%02d%02d", date.getMonth(), date
					.getDay());
			return (String) PataphysiqueCalendarDay.calendarDays.get(id);
		}
	}

}
