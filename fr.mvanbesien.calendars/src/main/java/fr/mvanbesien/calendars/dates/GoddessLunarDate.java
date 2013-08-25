package fr.mvanbesien.calendars.dates;

public class GoddessLunarDate extends AbstractDate {

	/**
	 * Month names, for the Goddess Lunar Calendar
	 */
	public static final String[] G_MONTHS = new String[] { "", "Astarte",
			"Bast", "Cybele", "Diana", "Eris", "Freya", "Gaia", "Hathor",
			"Isis", "Juno", "Kali", "Lakshmi", "Maat" };

	@Override
	public String toString() {
		if (isExtraDay)
			return "";
		return String.format("%s %s, %s (%s Cycle)",
				GoddessLunarDate.G_MONTHS[month], day, year, era);
	}

}
