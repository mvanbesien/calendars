/**
 * $Id: ChineseCalendar.java,v 1.2 2009/04/23 09:39:18 maxence Exp $
 * 
 * HISTORY 
 * -------
 * $Log: ChineseCalendar.java,v $
 * Revision 1.2  2009/04/23 09:39:18  maxence
 * Added draft of lunar date calculation
 *
 * Revision 1.1  2009/02/24 11:00:58  maxence
 * MVA : Initial Revision
 *
 */
package fr.mvanbesien.calendars.calendars;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import fr.mvanbesien.calendars.dates.ChineseLunarDate;
import fr.mvanbesien.calendars.dates.ChineseSolarDate;
import fr.mvanbesien.calendars.ephemerides.LunarPhases;
import fr.mvanbesien.calendars.tools.Angles;
import fr.mvanbesien.calendars.tools.OrbitalElements;
import fr.mvanbesien.calendars.tools.Utils;

/**
 * Chinese Calendar
 * 
 * @author mvanbesien
 * @version $Revision: 1.2 $
 * @since $Date: 2009/04/23 09:39:18 $
 * 
 */
public class ChineseCalendar {

	public static ChineseLunarDate getLunarDate() {
		return ChineseCalendar.getLunarDate(Calendar.getInstance());
	}

	public static ChineseLunarDate getLunarDate(Calendar instance) {
		// TODO Gerer les mois embolismiques ?
		Date lunarNewYear = ChineseCalendar.getLunarNewYear(instance
				.get(Calendar.YEAR));
		boolean isNewYearInPreviousYear = false;
		if (lunarNewYear.getTime() > instance.getTimeInMillis()) {
			lunarNewYear = ChineseCalendar.getLunarNewYear(instance
					.get(Calendar.YEAR) - 1);
			isNewYearInPreviousYear = true;
		}
		Calendar monthBegin = Calendar.getInstance();
		monthBegin.setTimeInMillis(lunarNewYear.getTime());

		Calendar nextMonthBegin = Calendar.getInstance();
		nextMonthBegin.setTime(monthBegin.getTime());
		nextMonthBegin.add(Calendar.DAY_OF_YEAR, 25);
		nextMonthBegin.setTime(LunarPhases.getNextNewMoon(nextMonthBegin));
		int month = 1;
		while (nextMonthBegin.getTimeInMillis() < instance.getTimeInMillis()) {
			monthBegin.setTime(nextMonthBegin.getTime());
			nextMonthBegin.add(Calendar.DAY_OF_YEAR, 25);
			nextMonthBegin.setTime(LunarPhases.getNextNewMoon(nextMonthBegin));
			month++;
		}

		int year = instance.get(Calendar.YEAR) + 2698;
		if (isNewYearInPreviousYear)
			year--;

		int monthBeginDOY = monthBegin.get(Calendar.DAY_OF_YEAR);
		int currentDOY = instance.get(Calendar.DAY_OF_YEAR);

		int day = currentDOY - monthBeginDOY;
		if (day < 0)
			day += Utils.isLeapYear(monthBegin.get(Calendar.YEAR)) ? 366 : 365;

		ChineseLunarDate date = new ChineseLunarDate();
		date.setOrdinaryDate(-1, day, month, year % 60, year / 60);
		return date;
	}

	/**
	 * Returns the Lunar New Year date for the current date. Returned date is
	 * formatted as follows : yyyy-MM-dd
	 * 
	 * @return New Year Date as String
	 */
	public static Date getLunarNewYear() {
		return ChineseCalendar.getLunarNewYear(Calendar.getInstance().get(
				Calendar.YEAR));
	}

	/**
	 * Returns the Lunar New Year date for the year passed as parameter.
	 * Returned date is formatted as follows : yyyy-MM-dd
	 * 
	 * @param year
	 *            : year
	 * @return New Year Date as String
	 */
	public static Date getLunarNewYear(int year) {
		Calendar c = Calendar.getInstance();
		c.set(year, 0, 1, 0, 0, 0);
		c.add(Calendar.HOUR_OF_DAY, -1);
		c.set(Calendar.MILLISECOND, 0);
		Calendar ws = SeasonCalendar.getWinterSolstice(c);
		Date nextNewMoon = LunarPhases.getNextNewMoon(ws);
		ws.setTime(nextNewMoon);
		ws.add(Calendar.DAY_OF_WEEK, 25);
		nextNewMoon = LunarPhases.getNextNewMoon(ws);
		ws.setTime(nextNewMoon);

		if (ws.get(Calendar.DAY_OF_MONTH) < 21
				&& ws.get(Calendar.MONTH) == Calendar.JANUARY) {
			ws.add(Calendar.DAY_OF_WEEK, 25);
			nextNewMoon = LunarPhases.getNextNewMoon(ws);
			ws.setTime(nextNewMoon);
		}

		Calendar newYearDate = Calendar.getInstance();
		newYearDate.setTimeInMillis(ws.getTimeInMillis());
		newYearDate.set(Calendar.HOUR_OF_DAY, 0);
		newYearDate.set(Calendar.MINUTE, 0);
		newYearDate.set(Calendar.SECOND, 0);
		newYearDate.set(Calendar.MILLISECOND, 0);
		int hourInChina = ws.get(Calendar.HOUR_OF_DAY)
				+ (-ws.get(Calendar.DST_OFFSET) - ws.get(Calendar.ZONE_OFFSET) + TimeZone
						.getTimeZone("Etc/GMT-8").getRawOffset())
				/ (60 * 60 * 1000);
		if (hourInChina >= 24)
			newYearDate.add(Calendar.DAY_OF_YEAR, 1);

		return newYearDate.getTime();
	}

	/**
	 * Returns the Solar Date for the Calendar passed as parameter
	 * 
	 * @param instance
	 *            : Calendar
	 * @return Chinese Solar Date
	 */
	public static ChineseSolarDate getSolarDate(Calendar instance) {
		int solarEclipticLongitude = new Double(ChineseCalendar
				.getSolarEclipticLongitude(instance)).intValue();
		int month = (solarEclipticLongitude - 315 + 360) % 360 / 15;

		int inferiorTier = solarEclipticLongitude / 15 * 15;
		int temp = solarEclipticLongitude;
		Calendar copy = Utils.getCalendarCopy(instance);
		copy.set(Calendar.HOUR_OF_DAY, 0);
		copy.set(Calendar.MINUTE, 0);
		copy.set(Calendar.SECOND, 0);
		copy.set(Calendar.MILLISECOND, 0);

		while (temp > inferiorTier) {
			copy.add(Calendar.DAY_OF_YEAR, -1);
			temp = new Double(ChineseCalendar.getSolarEclipticLongitude(copy))
					.intValue();
		}
		int day = new Long(
				(instance.getTimeInMillis() - copy.getTimeInMillis()) / 86400000)
				.intValue();
		int year = instance.get(Calendar.YEAR) + 2698;
		if (instance.get(Calendar.MONTH) < 2 && solarEclipticLongitude < 315)
			year--;
		ChineseSolarDate csd = new ChineseSolarDate();
		csd.setOrdinaryDate(-1, day, month, year % 60, year / 60);
		return csd;
	}

	/*
	 * Returns the Solar Ecliptic Longitude for calendar. Used for solar date.
	 */
	private static double getSolarEclipticLongitude(Calendar instance) {
		OrbitalElements oes = OrbitalElements.getOrbitalElements(
				OrbitalElements.SUN, instance);

		double E0 = oes.getM() + oes.getE() * 180 / Math.PI
				* Angles.sin(oes.getM())
				* (1.0 + oes.getE() * Angles.cos(oes.getM()));
		double E1 = E0
				- (E0 - oes.getE() * 180 / Math.PI * Angles.sin(E0) - oes
						.getM()) / (1 - oes.getE() * Angles.cos(E0));
		while (E1 - E0 > 0.005) {
			E0 = E1;
			E1 = E0
					- (E0 - oes.getE() * 180 / Math.PI * Angles.sin(E0) - oes
							.getM()) / (1 - oes.getE() * Angles.cos(E0));
		}

		double xv = oes.getA() * (Angles.cos(E0) - oes.getE());
		double yv = oes.getA() * Math.sqrt(1.0 - oes.getE() * oes.getE())
				* Angles.sin(E0);

		double v = Angles.atan2(yv, xv);
		double r = Math.sqrt(xv * xv + yv * yv);

		double xh = r
				* (Angles.cos(oes.getN()) * Angles.cos(v + oes.getW()) - Angles
						.sin(oes.getN())
						* Angles.sin(v + oes.getW()) * Angles.cos(oes.getI()));
		double yh = r
				* (Angles.sin(oes.getN()) * Angles.cos(v + oes.getW()) + Angles
						.cos(oes.getN())
						* Angles.sin(v + oes.getW()) * Angles.cos(oes.getI()));

		return Angles.atan2(yh, xh);

	}

	public static void main(String[] args) {
		Calendar c = Calendar.getInstance();
		System.out.println(ChineseCalendar.getLunarDate(c).toENString());
	}

}
