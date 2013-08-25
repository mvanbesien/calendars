package fr.mvanbesien.calendars.runners;

import java.util.Calendar;

import fr.mvanbesien.calendars.ephemerides.LunarPhases;
import fr.mvanbesien.calendars.ephemerides.MoonComputer;
import fr.mvanbesien.calendars.ephemerides.SolarComputer;

public class SunMoonTimes {

	private static final String[] directions = new String[] { " N ", "NNE",
			"NE ", "ENE", " E ", "ESE", " SE", "SSE", " S ", "SSW", "SW ",
			"WSW", " W ", "WNW", "NW ", "NNW" };

	private static String getDirection(double angle) {
		double temp = angle + 360d / 32;
		if (temp < 0)
			temp += 360;
		return SunMoonTimes.directions[new Double(temp * 16 / 360).intValue() % 16];
	}

	public static void main(String[] args) {
		double lt = 50.61985;
		double lg = 3.05409;
		Calendar c = Calendar.getInstance();

		SolarComputer solarComputer = SolarComputer.getInstance(lg, lt);
		double sunAltitude = solarComputer.getSunAltitude(c);
		double sunAzimuth = solarComputer.getSunAzimuth(c);
		System.out
				.println(String.format("SUN  : Al=%5.1f�, Az=%5.1f� (%3s)",
						sunAltitude, sunAzimuth, SunMoonTimes
								.getDirection(sunAzimuth)));

		MoonComputer moonComputer = MoonComputer.getInstance(lg, lt);
		double moonAltitude = moonComputer.getMoonAltitude(c);
		double moonAzimuth = moonComputer.getMoonAzimuth(c);
		double moonFullPct = LunarPhases.getMoonPhase(c);
		System.out.println(String.format(
				"MOON : Al=%5.1f�, Az=%5.1f� (%3s) - %5.1f%% full",
				moonAltitude, moonAzimuth, SunMoonTimes
						.getDirection(moonAzimuth), moonFullPct));

	}

}
