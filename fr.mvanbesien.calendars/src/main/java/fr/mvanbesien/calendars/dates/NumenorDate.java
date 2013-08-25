package fr.mvanbesien.calendars.dates;

import fr.mvanbesien.calendars.tools.Utils;

public class NumenorDate extends AbstractDate {

	/**
	 * weekday names for NumenorCalendar
	 */
	public static final String[] N_DAYSNAMES = { "", "Elenya", "Anarya",
			"Isilya", "Aldëa", "Menelia", "Eärenya", "Valanya" };

	/**
	 * Month names for NumenorCalendar
	 */
	public static final String[] N_MONTHNAMES = { "Narvinyë", "Nenimë",
			"Sulimë", "Viressë", "Lotessë", "Narië", "Cermië", "Urimë",
			"Yavannië", "Narquelië", "Hisimë", "Ringarë" };

	/**
	 * Extra Day names for NumenorCalendar
	 */
	public static final String[] N_EXTRADAYS = { "Yestarë", "Tuilerë",
			"Loëndë", "Yavierë", "Mettarë", "Cormarë" };

	@Override
	public String toString() {
		if (isExtraDay)
			return String.format("%s, Loa %s of the 7th Age",
					NumenorDate.N_EXTRADAYS[extraDayIndex], year);
		return String.format("%s %s %s, Loa %s of the 7th Age",
				NumenorDate.N_DAYSNAMES[weekDay], Utils.ordinal(day, true),
				NumenorDate.N_MONTHNAMES[month], year);

	}

}
