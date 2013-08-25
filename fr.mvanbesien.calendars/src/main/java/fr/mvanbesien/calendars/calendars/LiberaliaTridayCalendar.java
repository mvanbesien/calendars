package fr.mvanbesien.calendars.calendars;

import java.util.Calendar;

import fr.mvanbesien.calendars.dates.LiberaliaTridayDate;

public class LiberaliaTridayCalendar {

	private static final int GLOBAL_CYCLE_LENGTH = 48212;

	public static LiberaliaTridayDate getDate() {
		return LiberaliaTridayCalendar.getDate(Calendar.getInstance());
	}

	public static LiberaliaTridayDate getDate(Calendar instance) {
		long julianDay = new Double(
				ClassicCalendar.getJulianDay(instance) + 0.5).intValue();
		int daysInCalendar = new Long(julianDay - 2416557).intValue();
		LiberaliaTridayDate date = new LiberaliaTridayDate();
		date.setDay(LiberaliaTridayCalendar.getTriday(daysInCalendar) + 1);
		LiberaliaTridayCalendar.specifySolarDate(daysInCalendar, date);
		LiberaliaTridayCalendar.specifyLunarDate(daysInCalendar, date);
		return date;
	}

	private static int getTriday(int daysInCalendar) {
		return daysInCalendar % 3;
	}

	private static void specifyLunarDate(int daysInCalendar,
			LiberaliaTridayDate date) {
		int globalTriday = daysInCalendar / 3;

		int cycle = globalTriday / 45359;
		int tridaysInCycle = globalTriday % 45359;

		int year = -1;
		int monthIndex = -1;
		int triday = -1;

		if (globalTriday < 3 * 118) {
			year = tridaysInCycle / 118;
			int tridayInYear = tridaysInCycle % 118;
			tridayInYear += tridayInYear > 59 ? 1 : 0;
			monthIndex = tridayInYear / 10;
			triday = tridayInYear % 10 + 1;
		} else {
			int newTridayInCycle = globalTriday - 3 * 118;
			int innerCycle = newTridayInCycle / 945;
			int tridayInInnerCycle = newTridayInCycle % 945;
			year = tridayInInnerCycle / 118 + 8 * innerCycle + 3;
			int tridayInYear = tridayInInnerCycle % 118;
			tridayInYear += tridayInYear > 59 ? 1 : 0;
			monthIndex = tridayInYear / 10;
			triday = tridayInYear % 10 + 1;
		}
		date.setLunarDate(triday, monthIndex + 1, year, cycle + 1);
	}

	private static void specifySolarDate(int daysInCalendar,
			LiberaliaTridayDate date) {
		int globalTriday = daysInCalendar / 3;

		int cycleIndex = globalTriday
				/ LiberaliaTridayCalendar.GLOBAL_CYCLE_LENGTH;
		int tridayInCycle = globalTriday
				% LiberaliaTridayCalendar.GLOBAL_CYCLE_LENGTH;

		int month = -1;
		int triday = -1;
		int year = cycleIndex * 396;
		int tridayIn4Years = -1;
		if (tridayInCycle < 23863) {
			tridayIn4Years = tridayInCycle % 487;
			year += tridayInCycle / 487 * 4;
		} else if (tridayInCycle < 23863 + 486) {
			tridayIn4Years = tridayInCycle - 23863;
			year += 196;
		} else {
			int temp = tridayInCycle - 23863 - 486;
			tridayIn4Years = temp % 487;
			year += temp / 487 * 4 + 197;
		}

		int tridayInYear;
		if (tridayIn4Years < 122) {
			tridayInYear = tridayIn4Years;
			year += 0;
		} else if (tridayIn4Years <= 122 + 121) {
			tridayInYear = tridayIn4Years - 122;
			year += 1;
		} else if (tridayIn4Years <= 122 + 121 + 122) {
			tridayInYear = tridayIn4Years - 122 - 122;
			year += 2;
		} else {
			tridayInYear = tridayIn4Years - 122 - 122 - 122;
			year += 3;
		}

		if (tridayInYear < 30) {
			triday = tridayInYear;
			month = 0;
		} else if (tridayInYear < 30 + 31) {
			triday = tridayInYear - 30;
			month = 1;
		} else if (tridayInYear < 30 + 31 + 30) {
			triday = tridayInYear - 30 - 31;
			month = 2;
		} else {
			triday = tridayInYear - 30 - 31 - 30;
			month = 3;
		}
		date.setSolarDate(triday + 1, month + 1, year);
	}

}
