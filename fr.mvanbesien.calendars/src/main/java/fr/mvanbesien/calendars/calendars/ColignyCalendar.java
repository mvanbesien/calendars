package fr.mvanbesien.calendars.calendars;

import java.util.Calendar;

import fr.mvanbesien.calendars.tools.Utils;

/*
 * 1 lustre = 5 ans = 1831 jours
 * 1 si�cle vaut 6 lustres = 5 * 1831 + 1801 jours
 * 1 ere = 40 si�cles
 * 
 * => 1 �re = 1200 ans, et 1 �re = 240 lustres
 * 
 * Jours � rattraper = 51 par �re, soit 51 pour 240 lustres
 * => 1 tous les 5 lustres, + 1 tous les 80 lustres 
 * 
 */
public class ColignyCalendar {

	int[] lustreSplit = new int[] { 30, 355, 353, 178, 30, 177, 353, 355 };

	private static final String[] monthNames = new String[] { "Samonios",
			"Dumanios", "Riuros", "Anagantios", "Ogroniv", "Cutios",
			"Giamonios", "Simivi Sonnios", "Equos", "Elembius", "Aedrinnis",
			"Cantlos" };

	public static String getDate() {
		return ColignyCalendar.getDate(Calendar.getInstance());
	}

	public static String getDate(Calendar calendar) {
		double rawJD = ClassicCalendar.getJulianDay(calendar);
		long julianDay = new Double(rawJD - 0.5).longValue();

		julianDay = julianDay - 901772;

		int lustreNormalLength = 1831;
		int lustreShortLength = 1801;
		int siecleLength = lustreNormalLength * 5 + lustreShortLength;

		int era = new Long(julianDay / (40 * siecleLength + 51)).intValue();
		long dayInEra = julianDay - (40 * siecleLength + 51) * era;

		int dayInLustre = new Long(dayInEra).intValue();
		int lustre = 0;

		int daysToRemove = lustre % 6 == 0 ? lustreShortLength
				: lustreNormalLength;
		if (lustre % 5 == 0)
			daysToRemove++;
		if (lustre % 80 == 0)
			daysToRemove++;
		while (dayInLustre > daysToRemove) {
			dayInLustre -= daysToRemove;
			daysToRemove = lustre % 6 == 0 ? lustreShortLength
					: lustreNormalLength;
			lustre++;
			if (lustre % 5 == 0)
				daysToRemove++;
			if (lustre % 80 == 0)
				daysToRemove++;
		}

		int annee = lustre * 6 + era * 1200;

		int[] yearSplit = new int[] { 30, 29, 30, 29, 30, 30, 29, 30, 30, 29,
				30, 29 };
		int[] lustreSplit = new int[] { 30, 355, 353, 178, 30, 177, 353, 355 };

		if (lustre % 5 == 0)
			lustreSplit[6]++;
		if (lustre % 80 == 0)
			lustreSplit[6]++;

		int periode = -1;
		int lustreLimitday = 0;
		int dayInPeriode = 0;
		while (dayInLustre > lustreLimitday) {
			periode++;
			lustreLimitday += lustreSplit[periode];
		}

		dayInPeriode = periode < 0 ? 0 : dayInLustre - lustreLimitday
				+ lustreSplit[periode];
		if (periode == 5) {
			dayInPeriode = dayInPeriode + lustreSplit[3];
			periode = 3;
		}

		if (lustre % 6 == 0)
			lustreSplit[0] = 0;

		if (periode == 2)
			yearSplit[8] = 28;
		if (periode == 6) {
			yearSplit[8] = 28;
			if (lustre % 5 == 0)
				yearSplit[8]++;
			if (lustre % 80 == 0)
				yearSplit[8]++;
		}

		if (periode == 0 || periode == 4) {
			String mois = periode == 0 ? "Quimon" : "Ciallos";
			int day = dayInPeriode + 1;
			String moisComp = "";
			if (day > 15) {
				day = day - 15;
				moisComp = " Atenoux";
			}
			if (periode == 4)
				annee += 2;
			return String.format("%s%s %s %s", day, moisComp, mois, annee);

		} else {
			int index = -1;
			int monthLimitDay = 0;
			while (dayInPeriode > monthLimitDay) {
				index++;
				monthLimitDay += yearSplit[index];
			}
			String moisComp = "";
			String mois = ColignyCalendar.monthNames[index];
			int day = dayInPeriode - monthLimitDay + yearSplit[index] + 1;
			if (day > 15) {
				day = day - 15;
				moisComp = " Atenoux";
			}
			annee = annee + (periode < 5 ? periode - 1 : periode - 3);
			return String.format("%s%s %s %s", Utils.convertToRomanChars(day),
					moisComp, mois, Utils.convertToRomanChars(annee));
		}
	}

	public static void main(String[] args) throws Exception {

		Calendar c = Calendar.getInstance();
		// c.set(1984, 4, 15);
		String date = ColignyCalendar.getDate(c);
		System.out.println(date);

	}
}
