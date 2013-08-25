package fr.mvanbesien.calendars.runners;

import java.util.Calendar;

import fr.mvanbesien.calendars.ephemerides.MoonComputer;
import fr.mvanbesien.calendars.ephemerides.SolarComputer;
import fr.mvanbesien.calendars.tools.Utils;

public class PlainYearSunMoonDisplayer {

	private static class Data {
		String sunrise;

		String sunset;

		String moonrise;

		String moonset;
	}

	public static void main(String[] args) {
		run(2012, 3f + 1 / 60f + 39f / 3600, 50f + 34f / 60 + 5f / 3600);
		// run(2012, -79f - 35 / 60f - 0f / 3600, 44f + 18f / 60 + 0f / 3600);
	}

	public static void run(int year, float longitude, float latitude) {
		StringBuffer separator = new StringBuffer();
		for (int i = 0; i < 14 * 12 + 8; i++)
			separator.append("*");

		StringBuffer header = new StringBuffer();
		header.append(String.format("%5s  *", ""));
		String[] months = new String[] { "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV",
				"DEC" };
		for (int i = 0; i < 12; i++)
			header.append(String.format("%5s%3s%5s*", "", months[i], ""));
		Data[][] values = new Data[12][31];

		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_YEAR, 1);
		c.set(Calendar.YEAR, year);
		SolarComputer sc = SolarComputer.getInstance(longitude, latitude);
		MoonComputer mc = MoonComputer.getInstance(longitude, latitude);
		while (c.get(Calendar.YEAR) == year) {
			Data data = new Data();
			data.sunrise = Utils.displayAsHours(sc.getSunrise(c));
			data.sunset = Utils.displayAsHours(sc.getSunset(c));
			float[] moonRises = mc.getMoonRises(c);
			data.moonrise = moonRises.length > 0 ? Utils.displayAsHours(moonRises[0]) : "--:--";
			float[] moonSets = mc.getMoonSets(c);
			data.moonset = moonSets.length > 0 ? Utils.displayAsHours(moonSets[0]) : "--:--";
			values[c.get(Calendar.MONTH)][c.get(Calendar.DAY_OF_MONTH) - 1] = data;
			c.add(Calendar.DAY_OF_YEAR, 1);
		}

		System.out.println(separator);
		System.out.println(header);
		System.out.println(separator);
		for (int i = 0; i < 31; i++) {
			StringBuffer buffer = new StringBuffer();
			buffer.append(String.format("* %2s R *", i + 1));
			for (int j = 0; j < 12; j++)
				buffer.append(String.format(" %5s %5s *", values[j][i] != null ? values[j][i].sunrise : "",
						values[j][i] != null ? values[j][i].moonrise : ""));
			buffer.append(String.format("\n* %2s S *", ""));
			for (int j = 0; j < 12; j++)
				buffer.append(String.format(" %5s %5s *", values[j][i] != null ? values[j][i].sunset : "",
						values[j][i] != null ? values[j][i].moonset : ""));
			System.out.println(buffer.toString());
			System.out.println(separator);
		}

	}
}
