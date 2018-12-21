package fr.mvanbesien.calendars.calendars;

import java.util.Calendar;

import fr.mvanbesien.calendars.dates.BorealSeasonDate;
import fr.mvanbesien.calendars.tools.Utils;

public class BorealSeasonCalendar {

	private BorealSeasonCalendar() {
	}

	public static BorealSeasonDate getDate() {
		return getDate(Calendar.getInstance());
	}

	public static BorealSeasonDate getDate(Calendar calendar) {
		Calendar[] milestones = new Calendar[5];
		int year = calendar.get(Calendar.YEAR);

		milestones[0] = getWinterSolstice(year);
		if (milestones[0].getTimeInMillis() < calendar.getTimeInMillis()) {
			milestones[1] = getVernalEquinox(year + 1);
			milestones[2] = getSummerSolstice(year + 1);
			milestones[3] = getAutumnEquinox(year + 1);
			milestones[4] = getWinterSolstice(year + 1);
		} else {
			milestones[1] = getVernalEquinox(year);
			milestones[2] = getSummerSolstice(year);
			milestones[3] = getAutumnEquinox(year);
			milestones[4] = getWinterSolstice(year);
			milestones[0] = getWinterSolstice(year - 1);
		}

		int quarter = -1;
		for (int i = 0; i < 4 && quarter == -1; i++) {
			if (milestones[i].getTimeInMillis() <= calendar.getTimeInMillis()
					&& calendar.getTimeInMillis() < milestones[i + 1].getTimeInMillis()) {
				quarter = i;
			}
		}

		int dayInQuarter = dayCounter(milestones[quarter], milestones[quarter + 1]);
		int daysElapsed = dayCounter(milestones[quarter], calendar);
		
		int seasonYear = milestones[1].get(Calendar.YEAR);
		int seasonQuarter = quarter;
		
		int month;
		int day;
		
		if (daysElapsed < 30) {
			month = 0;
			day = daysElapsed + 1;
		} else if (daysElapsed < dayInQuarter - 30) {
			month = 1;
			day = daysElapsed - 30 + 1;
		} else {
			month = 2;
			day = daysElapsed - 30 - (dayInQuarter - 60) + 1;
		}
		
		BorealSeasonDate date = new BorealSeasonDate();
		date.setOrdinaryDate(-1, day, 3 * month + seasonQuarter, seasonYear);
		return date;
		
	}

	/**
	 * Returns the Calendar instance of the Autumn Equinox, for the year of the
	 * date passed as parameter
	 * 
	 * @param Calendar
	 *            instance
	 * @return Calendar instance
	 */
	private static Calendar getAutumnEquinox(int year) {
		double m = (new Double(year).doubleValue() - 2000) / 1000;
		double value = 2451810.21715 + 365242.01767 * m - 0.11575 * m * m + 0.00337 * m * m * m
				+ 0.00078 * m * m * m * m;
		Calendar result = Utils.getCalendarFromJulianDay(value);
		return result;
	}

	/**
	 * Returns the Calendar instance of the Summer Solstice, for the year of the
	 * date passed as parameter
	 * 
	 * @param Calendar
	 *            instance
	 * @return Calendar instance
	 */
	private static Calendar getSummerSolstice(int year) {
		double m = (new Double(year).doubleValue() - 2000) / 1000;
		double value = 2451716.56767 + 365241.62603 * m + 0.00325 * m * m + 0.00888 * m * m * m
				- 0.00030 * m * m * m * m;
		Calendar result = Utils.getCalendarFromJulianDay(value);
		return result;
	}

	/**
	 * Returns the Calendar instance of the Vernal Equinox, for the year of the
	 * date passed as parameter
	 * 
	 * @param Calendar
	 *            instance
	 * @return Calendar instance
	 */
	private static Calendar getVernalEquinox(int year) {
		double m = (new Double(year).doubleValue() - 2000) / 1000;
		double value = 2451623.80984 + 365242.37404 * m + 0.05169 * m * m - 0.00411 * m * m * m
				- 0.00057 * m * m * m * m;
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
	private static Calendar getWinterSolstice(int year) {
		double m = (new Double(year).doubleValue() - 2000) / 1000;
		double value = 2451900.05952 + 365242.74049 * m - 0.06223 * m * m - 0.00823 * m * m * m
				+ 0.00032 * m * m * m * m;
		Calendar result = Utils.getCalendarFromJulianDay(value);
		return result;
	}
	
	private static int dayCounter(Calendar beginDate, Calendar endDate) {

		int beginDOY = beginDate.get(Calendar.DAY_OF_YEAR);
		int endDOY = endDate.get(Calendar.DAY_OF_YEAR);
		int beginYear = beginDate.get(Calendar.YEAR);
		int endYear = endDate.get(Calendar.YEAR);
				

		int gap = 0;

		if (endYear > beginYear)
			gap += (Utils.isLeapYear(beginYear) ? 366 : 365) - beginDOY;
		else
			gap -= beginDOY;

		int tempYear = beginYear;
		while (tempYear + 1 < endYear) {
			gap += Utils.isLeapYear(tempYear) ? 366 : 365;
			tempYear++;
		}

		gap += endDOY;
		return gap;

	}

}
