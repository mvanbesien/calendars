package fr.mvanbesien.calendars.dates;

import fr.mvanbesien.calendars.tools.Utils;

public class MuslimDate extends AbstractDate {

	/**
	 * Month names the Muslim Calendar
	 */
	public static final String[] MONTHS = new String[] { "", "Muharram",
			"Safar", "Rabi' al-awwal", "Rabi' al-thani", "Jumada al-awwal", "Jumada al-thani",
			"Radjab", "Sha'ban", "Ramadan", "Chawwal", "Dhu al-Qi'dah",
			"Dhu al-Hijjah" };

	/**
	 * Weekday names for the Muslim Calendar
	 */
	public static final String[] WEEKDAYS = new String[] { "Youm el Ahad", "Youm el Thani",
			"Youm el Thalbeth", "Youm el Arbaa", "Youm el Thamis", "Youm el Djouma", "Youm el Effabt"};

	
	@Override
	public String toString() {
		return String.format("%s, %s %s[%s] %s", WEEKDAYS[weekDay], day, MONTHS[month], Utils.convertToRomanChars(month), year);
	}

}
