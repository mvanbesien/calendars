package fr.mvanbesien.calendars.dates;

public class SexagesimalDate extends AbstractDate {

	/**
	 * Weekdays, in french, with Mon=1, to Sun=7
	 */
	public static final String[] WEEKDAYS_1_FR = new String[] { "", "Lundi",
			"Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche" };

	/**
	 * Soix names (months in Sexagesimal calendar)
	 */
	public static final String[] S_SOIX = new String[] { "", "Frigée",
			"Eclose", "Florée", "Granée", "Récole", "Caduce" };

	/**
	 * Adventice names (Extra days in Sexagesimal Calendar)
	 */
	public static final String[] S_ADVENTICE = new String[] { "", "Bacchanal",
			"Cérès", "Musica", "Liber", "Memento Mori", "Sext" };

	@Override
	public String toString() {
		if (isExtraDay)
			return String.format("%s %s",
					SexagesimalDate.S_ADVENTICE[extraDayIndex], String.format(
							"%03d", year > 0 ? year : year - 1));
		return String.format("%s %s%s %s %s",
				SexagesimalDate.WEEKDAYS_1_FR[weekDay], day, day == 1 ? "er"
						: "", SexagesimalDate.S_SOIX[month], String.format(
						"%03d", year > 0 ? year : year - 1));

	}

}
