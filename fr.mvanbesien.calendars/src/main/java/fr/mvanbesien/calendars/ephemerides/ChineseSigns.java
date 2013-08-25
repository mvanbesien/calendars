package fr.mvanbesien.calendars.ephemerides;

import java.util.Calendar;

import fr.mvanbesien.calendars.calendars.ChineseCalendar;

public class ChineseSigns {

	private static final String[] ANIMALS = { "Rat", "Ox", "Tiger", "Rabbit",
			"Dragon", "Snake", "Horse", "Goat", "Monkey", "Rooster", "Dog",
			"Pig" };

	private static final String[] ELEMENTS = { "Metal", "Water", "Wood",
			"Fire", "Earth" };

	public static String getCelestElement(int year) {
		int combination = ChineseSigns.getCombination(year) - 2;
		while (combination < 0)
			combination += 60;
		if (combination % 3 == 0)
			return ChineseSigns.ELEMENTS[4];
		else
			return ChineseSigns.ELEMENTS[(combination + 5) / 3 % 4];
	}

	private static String getChineseSign(int year) {
		return ChineseSigns.ELEMENTS[year / 2 % 5] + " "
				+ ChineseSigns.ANIMALS[(year - 4) % 12];
	}

	private static int getCombination(int year) {
		return (year - 1984) % 60 + 1;
	}

	public static String getSign(Calendar c) {
		return ChineseSigns.getChineseSign(c.get(Calendar.YEAR)
				- (c.getTimeInMillis() < ChineseCalendar.getLunarNewYear(
						c.get(Calendar.YEAR)).getTime() ? 1 : 0));

	}
}
