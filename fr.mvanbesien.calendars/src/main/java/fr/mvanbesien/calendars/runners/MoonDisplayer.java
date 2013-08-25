package fr.mvanbesien.calendars.runners;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.mvanbesien.calendars.ephemerides.LunarPhases;
import fr.mvanbesien.calendars.ephemerides.MoonComputer;
import fr.mvanbesien.calendars.tools.Utils;

public class MoonDisplayer {

	private static enum MoonKind {
		RISE, SET
	}

	private static final double defaultLongitude = 3.05409;

	private static final double defaultLatitude = 50.61985;

	private static final SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd");

	private static final String[] directions = new String[] { "N", "NNE", "NE",
			"ENE", "E", "ESE", "SE", "SSE", "S", "SSW", "SW", "WSW", "W",
			"WNW", "NW", "NNW" };

	public static void main(String[] args) {
		double longitude = MoonDisplayer.defaultLongitude;
		double latitude = MoonDisplayer.defaultLatitude;
		Calendar c = Calendar.getInstance();
		// c.add(Calendar.DAY_OF_YEAR, -12);
		MoonComputer moonComputer = MoonComputer.getInstance(longitude,
				latitude);
		System.out.println("-----------------------------------------------");
		double moonAzimuth = moonComputer.getMoonAzimuth(c);
		double moonAltitude = moonComputer.getMoonAltitude(c);
		double temp = moonAzimuth + 360d / 32;
		if (temp < 0)
			temp += 360;
		int directionIndex = new Double(temp * 16 / 360).intValue();
		String altitudeToDisplay = Double.toString(moonAltitude);
		if (altitudeToDisplay.indexOf(".") > -1
				&& altitudeToDisplay.indexOf(".") < altitudeToDisplay.length() - 2)
			altitudeToDisplay = altitudeToDisplay.substring(0,
					altitudeToDisplay.indexOf(".") + 2);
		System.out.println("Current position is "
				+ MoonDisplayer.directions[directionIndex] + " at "
				+ altitudeToDisplay + "°.");
		System.out.println();
		float[] moonRises = moonComputer.getMoonRises(c);
		float[] moonSets = moonComputer.getMoonSets(c);
		Map<Float, MoonKind> riseAndSets = new HashMap<Float, MoonKind>();
		for (float moonRise : moonRises)
			riseAndSets.put(moonRise, MoonKind.RISE);
		for (float moonSet : moonSets)
			riseAndSets.put(moonSet, MoonKind.SET);
		List<Float> times = new ArrayList<Float>();
		times.addAll(riseAndSets.keySet());
		Collections.sort(times);
		System.out.print("The Moon ");
		for (int i = 0; i < times.size(); i++) {
			if (i > 0 && i < times.size() - 1)
				System.out.print(", ");
			else if (i == times.size() - 1)
				System.out.print(" and ");
			System.out
					.print(riseAndSets.get(times.get(i)) == MoonKind.RISE ? "rises at "
							: "sets at ");
			System.out.print(Utils.displayAsHours(times.get(i)));
		}
		System.out.println(".\n");

		times.add(0, 0f);
		times.add(24f);
		for (int i = 0; i < times.size() - 1; i++) {

			float begin = times.get(i);
			float end = times.get(i + 1);

			if (riseAndSets.get(begin) != null) {
				boolean isMax = riseAndSets.get(begin) == MoonKind.RISE;
				if (isMax) {
					float time = (begin + end) / 2 - 1;
					Calendar c1 = Calendar.getInstance();
					int hours = new Float(time).intValue();
					c1.set(Calendar.HOUR_OF_DAY, hours);
					c1.set(Calendar.MINUTE, new Float((time - hours) * 60)
							.intValue());
					double alt = moonComputer.getMoonAltitude(c1);
					c1.add(Calendar.MINUTE, 1);
					double d = moonComputer.getMoonAltitude(c1);
					while (d > alt) {
						// System.out.println("ALT = "+alt+" <> "+d+" ---
						// "+c1.getTime());
						alt = d;
						c1.add(Calendar.MINUTE, 1);
						d = moonComputer.getMoonAltitude(c1);
					}
					time = new Float(c1.get(Calendar.HOUR_OF_DAY))
							+ new Float(c1.get(Calendar.MINUTE)) / 60 - 1 / 60;
					System.out.println("Maximum peak at "
							+ Utils.displayAsHours(time) + " with "
							+ String.format("%1.1f", alt) + "°");
				} else {
					float time = (begin + end) / 2 - 1;
					Calendar c1 = Calendar.getInstance();
					int hours = new Float(time).intValue();
					c1.set(Calendar.HOUR_OF_DAY, hours);
					c1.set(Calendar.MINUTE, new Float((time - hours) * 60)
							.intValue());
					double alt = moonComputer.getMoonAltitude(c1);
					c1.add(Calendar.MINUTE, 1);
					double d = moonComputer.getMoonAltitude(c1);
					while (d < alt) {
						// System.out.println("ALT = "+alt+" <> "+d+" ---
						// "+c1.getTime());
						alt = d;
						c1.add(Calendar.MINUTE, 1);
						d = moonComputer.getMoonAltitude(c1);
					}
					time = new Float(c1.get(Calendar.HOUR_OF_DAY))
							+ new Float(c1.get(Calendar.MINUTE)) / 60 - 1 / 60;
					System.out.println("Minimum peak at "
							+ Utils.displayAsHours(time) + " with "
							+ String.format("%1.1f", alt) + "°");
				}
			}

		}

		// End of addition

		String moonFullPct = Double.toString(LunarPhases.getMoonPhase(c));
		if (moonFullPct.indexOf(".") > -1
				&& moonFullPct.indexOf(".") < moonFullPct.length() - 2)
			moonFullPct = moonFullPct
					.substring(0, moonFullPct.indexOf(".") + 2);
		System.out.println();
		System.out.println("The Moon is currently " + moonFullPct
				+ "% full (Phase is "
				+ LunarPhases.getMoonPhaseName(c).toLowerCase() + ").");
		Date nextFullMoon = LunarPhases.getNextFullMoon(c);
		Date nextNewMoon = LunarPhases.getNextNewMoon(c);
		if (nextFullMoon.before(nextNewMoon)) {
			System.out.println("Next Full Moon : "
					+ MoonDisplayer.sdf.format(nextFullMoon));
			System.out.println("Next New Moon  : "
					+ MoonDisplayer.sdf.format(nextNewMoon));
		} else {
			System.out.println("Next New Moon  : "
					+ MoonDisplayer.sdf.format(nextNewMoon));
			System.out.println("Next Full Moon : "
					+ MoonDisplayer.sdf.format(nextFullMoon));
		}
		System.out.println("-----------------------------------------------");
		System.out.println(String.format(
				"(Geographical Position: %s° %s, %s° %s)", Math.abs(latitude),
				latitude >= 0 ? "N" : "S", Math.abs(longitude),
				longitude >= 0 ? "E" : "W"));
		System.out.println("(Time: " + c.getTime().toString() + ")");

		System.out.println("-----------------------------------------------");
	}

}
