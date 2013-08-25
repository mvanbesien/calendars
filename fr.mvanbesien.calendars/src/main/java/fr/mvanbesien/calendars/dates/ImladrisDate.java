package fr.mvanbesien.calendars.dates;

import fr.mvanbesien.calendars.tools.Utils;

public class ImladrisDate extends AbstractDate {
	/**
	 * Extra Day names for ImladrisCalendar
	 */
	public static final String[] I_EXTRADAYS = { "Yestarë", "Enna Enderë",
			"Loëndë", "Cormarë minë", "Cormarë atta", "Cormarë neldë",
			"Metima Enderë", "Mettarë" };

	/**
	 * Season names for the QuenyaCalendar
	 */
	public static final String[] I_MONTHS = { "Tuilië", "Lairë", "Yavië",
			"Quellë", "Hrivë", "Coirë" };

	/**
	 * weekday names for Imladris Calendar
	 */
	public static final String[] I_DAYSNAMES = { "", "Elenya", "Anarya",
			"Isilya", "Alduya", "Menelia", "Valanya" };

	@Override
	public String toString() {
		if (isExtraDay)
			return String.format("%s %s Loa, %s Yen of the 7th Age",
					ImladrisDate.I_EXTRADAYS[extraDayIndex], Utils.ordinal(
							year, true), Utils.ordinal(era, true));
		return String.format("%s %s %s, %s Loa, %s Yen of the 7th Age",
				ImladrisDate.I_DAYSNAMES[weekDay], Utils.ordinal(day, true),
				ImladrisDate.I_MONTHS[month], Utils.ordinal(year, true), Utils
						.ordinal(era, true));
	}

}
