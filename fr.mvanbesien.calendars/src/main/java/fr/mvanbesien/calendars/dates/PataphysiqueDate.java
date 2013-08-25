package fr.mvanbesien.calendars.dates;

public class PataphysiqueDate extends AbstractDate {

	/**
	 * Days in Pataphysique french calendar
	 */
	public static final String[] PT_JOURS = new String[] { "Lundi", "Mardi",
			"Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche", "Hunyadi" };

	/**
	 * Months in Pataphysique french calendar
	 */
	public static final String[] PT_MOIS = new String[] { "Absolu", "Haha",
			"As", "Sable", "Décervelage", "Gueules", "Pédale", "Clinamen",
			"Palotin", "Merdre", "Gidouille", "Tatane", "Phalle" };

	@Override
	public String toString() {
		if (isExtraDay)
			return "";
		return String.format("%s %s %s %s E.P.",
				PataphysiqueDate.PT_JOURS[weekDay], day,
				PataphysiqueDate.PT_MOIS[month], year);
	}

}
