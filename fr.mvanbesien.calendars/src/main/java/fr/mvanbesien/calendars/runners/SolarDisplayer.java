package fr.mvanbesien.calendars.runners;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import fr.mvanbesien.calendars.ephemerides.SolarComputer;
import fr.mvanbesien.calendars.tools.Utils;

public class SolarDisplayer {

	// -longitude -79.5 -latitude 44.3 -zone America/Toronto
	
	private static final double defaultLongitude = 3.05409;

	private static final double defaultLatitude = 50.61985;
	
	private static final String defaultTimeZone = "Europe/Paris";

	private static final String[] directions = new String[] { "N", "NNE", "NE",
			"ENE", "E", "ESE", "SE", "SSE", "S", "SSW", "SW", "WSW", "W",
			"WNW", "NW", "NNW" };

	private static final SimpleDateFormat format = new SimpleDateFormat(
			"yyyy-MM-dd@HH:mm:ss");

	public static void main(String[] args) {
		double longitude = SolarDisplayer.defaultLongitude;
		double latitude = SolarDisplayer.defaultLatitude;
		Calendar c = Calendar.getInstance();
		TimeZone timeZone = TimeZone.getTimeZone(defaultTimeZone);
		int index = 0;
		while (args != null && index < args.length) {
			if (args[index].toLowerCase().equals("-longitude")
					&& index < args.length - 1) {
				String s = args[index + 1];
				try {
					longitude = Double.parseDouble(s);
				} catch (NumberFormatException nfe) {
					System.out.println("Longitude malformed");
				}
				index++;
			} else if (args[index].toLowerCase().equals("-latitude")
					&& index < args.length - 1) {
				String s = args[index + 1];
				try {
					latitude = Double.parseDouble(s);
				} catch (NumberFormatException nfe) {
					System.out.println("Latitude malformed");
				}
				index++;
			} else if (args[index].toLowerCase().equals("-date")
					&& index < args.length - 1) {
				String dateAsString = args[index + 1];
				try {
					Date date = SolarDisplayer.format.parse(dateAsString);
					c.setTimeInMillis(date.getTime());
				} catch (ParseException pe) {
					System.out.println("Date malformed");
				}
			} else if (args[index].toLowerCase().equals("-zone")
					&& index < args.length - 1) {
				String tzid = args[index + 1];
				timeZone = TimeZone.getTimeZone(tzid);
			}
			index++;
		}

		c.setTimeZone(timeZone);
		
		SolarComputer solarComputer = SolarComputer.getInstance(longitude,
				latitude);
		double sunAltitude = solarComputer.getSunAltitude(c);
		double sunAzimuth = solarComputer.getSunAzimuth(c);
		double temp = sunAzimuth + 360d / 32;
		if (temp < 0)
			temp += 360;
		String altitudeToDisplay = Double.toString(sunAltitude);
		if (altitudeToDisplay.indexOf(".") > -1
				&& altitudeToDisplay.indexOf(".") < altitudeToDisplay.length() - 2)
			altitudeToDisplay = altitudeToDisplay.substring(0,
					altitudeToDisplay.indexOf(".") + 2);
		int directionIndex = new Double(temp * 16 / 360).intValue();
		System.out.println("-----------------------------------------------");
		System.out.println(String.format("Sun position is %s at %s°",
				SolarDisplayer.directions[directionIndex], altitudeToDisplay));
		double tempAzimuth = sunAzimuth / 15;
		int hours = new Double(tempAzimuth).intValue() % 24;
		tempAzimuth = tempAzimuth - hours;
		int minutes = new Double(tempAzimuth * 60).intValue() % 60;
		tempAzimuth = tempAzimuth - minutes / 60;
		int seconds = new Double(tempAzimuth * 3600).intValue() % 60;

		System.out.println(String.format("Sun time is %d:%s%d:%s%d", hours,
				minutes < 10 ? "0" : "", minutes, seconds < 10 ? "0" : "",
				seconds));
		System.out.println("");
		String zenith = Double.toString(solarComputer.getMaxSunAltitude(c));
		if (zenith.indexOf(".") > -1
				&& zenith.indexOf(".") < zenith.length() - 2)
			zenith = zenith.substring(0, zenith.indexOf(".") + 2);

		String nadir = Double.toString(solarComputer.getMinSunAltitude(c));
		if (nadir.indexOf(".") > -1 && nadir.indexOf(".") < nadir.length() - 2)
			nadir = nadir.substring(0, nadir.indexOf(".") + 2);

		System.out.println("Today's max Sun altitude is " + zenith + "° at "
				+ Utils.displayAsHours(solarComputer.getTransit(c)));
		System.out.println("Today's min Sun altitude is " + nadir + "° at "
				+ Utils.displayAsHours(solarComputer.getNadir(c)));
		String mean = Double
				.toString((solarComputer.getMaxSunAltitude(c) + solarComputer
						.getMinSunAltitude(c)) / 2);
		if (mean.indexOf(".") > -1 && mean.indexOf(".") < mean.length() - 2)
			mean = mean.substring(0, mean.indexOf(".") + 2);
		System.out.println("Mean Sun altitude for today is " + mean + "°");
		System.out.println("");
		System.out.println("Today's sunrise : "
				+ Utils.displayAsHours(solarComputer.getSunrise(c)));
		System.out.println("Today's sunset  : "
				+ Utils.displayAsHours(solarComputer.getSunset(c)));
		System.out.println("-----------------------------------------------");
		System.out.println(String.format(
				"(Geographical Position: %s° %s, %s° %s)", Math.abs(latitude),
				latitude >= 0 ? "N" : "S", Math.abs(longitude),
				longitude >= 0 ? "E" : "W"));
		System.out.println("(Time: " + c.getTime().toString() + ")");

		System.out.println("-----------------------------------------------");
		// double distanceWithBody = SolarSystemDistances.getDistanceWithBody(c,
		// OrbitalElements.SUN, false);
		// System.out.println("Sun actual distance is
		// "+Double.toString(distanceWithBody));
	}

}
