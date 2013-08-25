package fr.mvanbesien.calendars.clocks;

import java.util.Calendar;

public class GermanFuzzyTime {

	public static String getFuzzy() {
		return getFuzzy(Calendar.getInstance());
	}

	public static String getFuzzy(Calendar c) {
		String[] hoursNames = new String[] { "eins", "zwei", "drei", "vier", "fünf", "sechs",
				"sieben", "acht", "neun", "zehn", "elf" };
		String[] minutesNames = new String[] { "", "fünf ", "zehn ", "viertel ", "zwanzig ",
				"fünf ", "", "fünf ", "zwanzig ", "viertel ", "zehn ", "fünf ", "" };

		int hours = c.get(Calendar.HOUR_OF_DAY);
		int minutes = c.get(Calendar.MINUTE);

		int quartile = (minutes * 2 + 5) / 10;
		int reducedHours = (hours + (quartile > 4 ? 1 : 0)) % 12;
		String halb = quartile >= 5 && quartile <= 7 ? "halb " : "";
		String keyword = quartile < 5 || quartile == 7 ? "nach " : "vor ";
		if (quartile == 0 || quartile == 6)
			keyword = "";

		String hoursValue = null;
		if (reducedHours == 0 && (hours < 5 || hours > 15)) {
			hoursValue = "mittenacht";
		} else if (reducedHours == 0 ) {
			hoursValue = "mittag";
		} else
			hoursValue = hoursNames[reducedHours - 1];
		return minutesNames[quartile] + keyword + halb + hoursValue;
	}

	public static void main(String[] args) {
		System.out.println(getFuzzy());
	}

}
