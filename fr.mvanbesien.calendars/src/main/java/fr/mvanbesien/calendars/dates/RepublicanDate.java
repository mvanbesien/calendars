package fr.mvanbesien.calendars.dates;

import fr.mvanbesien.calendars.tools.Utils;

public class RepublicanDate extends AbstractDate {

	/**
	 * Month names for the French Republican Calendar
	 */
	public static final String[] R_MOIS = new String[] { "", "Vendémiaire",
			"Brumaire", "Frimaire", "Nivôse", "Pluviôse", "Ventôse",
			"Germinal", "Floréal", "Prairial", "Messidor", "Thermidor",
			"Fructidor" };

	/**
	 * Weekday names for the French Republican Calendar
	 */
	public static final String[] R_JOURS = new String[] { "Décadi", "Primidi",
			"Duodi", "Tridi", "Quartidi", "Quintidi", "Sextidi", "Septidi",
			"Octidi", "Nonidi" };

	/**
	 * Extra day names for the French Republican Calendar
	 */
	public static final String[] R_JOURS_COMPLEMENTAIRES = new String[] { "",
			"Fête de la Vertu", "Fête du Génie", "Fête du Travail",
			"Fête de l'Opinion", "Fête des Récompenses",
			"Fête de la Révolution" };

	@Override
	public String toString() {
		if (isExtraDay)
			return String.format("%s, de l'an %s",
					RepublicanDate.R_JOURS_COMPLEMENTAIRES[extraDayIndex],
					Utils.convertToRomanChars(year));
		return String.format("%s de la %s décade de %s de l'an %s",
				RepublicanDate.R_JOURS[weekDay], new String[] { "première",
						"deuxième", "troisième" }[(day - 1) / 10],
				RepublicanDate.R_MOIS[month], Utils.convertToRomanChars(year));

	}

}
