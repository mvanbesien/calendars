/**
 * $Id: MoonComputer.java,v 1.3 2009/04/23 08:11:09 maxence Exp $
 * 
 * HISTORY
 * -------
 * $Log: MoonComputer.java,v $
 * Revision 1.3  2009/04/23 08:11:09  maxence
 * MVA : Fixed value when iterating
 *
 * Revision 1.2  2009/02/23 15:08:28  maxence
 * Enhanced
 *
 * Revision 1.1  2009/02/18 10:28:36  maxence
 * Initial Revision as finished element
 *
 * 
 */
package fr.mvanbesien.calendars.ephemerides;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fr.mvanbesien.calendars.tools.Angles;
import fr.mvanbesien.calendars.tools.OrbitalElements;
import fr.mvanbesien.calendars.tools.Utils;

/**
 * Computes Moon position information, and rises/sets
 * 
 * @author mvanbesien
 * @version $Revision: 1.3 $
 * @since $Date: 2009/04/23 08:11:09 $
 * 
 */
public class MoonComputer {

	/**
	 * Return instance for longitude and latitude
	 * 
	 * @param longitude
	 * @param latitude
	 * @return MoonComputer Instance
	 */
	public static MoonComputer getInstance(double longitude, double latitude) {
		return new MoonComputer(longitude, latitude);
	}

	/**
	 * Longitude
	 */
	private double longitude;

	/**
	 * Latitude
	 */
	private double latitude;

	/**
	 * Returns now instance of MoonComputer
	 * 
	 * @param longitude
	 * @param latitude
	 */
	private MoonComputer(double longitude, double latitude) {
		this.longitude = longitude;
		this.latitude = latitude;
	}

	private double[] getInfo(Calendar c) {

		Calendar temp = Utils.getCalendarCopy(c);

		OrbitalElements oem = OrbitalElements.getOrbitalElements(
				OrbitalElements.MOON, temp);

		double E0 = oem.getM() + oem.getE() * 180 / Math.PI
				* Angles.sin(oem.getM())
				* (1.0 + oem.getE() * Angles.cos(oem.getM()));
		double E1 = E0
				- (E0 - oem.getE() * 180 / Math.PI * Angles.sin(E0) - oem
						.getM()) / (1 - oem.getE() * Angles.cos(E0));
		while (E1 - E0 > 0.005) {
			E0 = E1;
			E1 = E0
					- (E0 - oem.getE() * 180 / Math.PI * Angles.sin(E0) - oem
							.getM()) / (1 - oem.getE() * Angles.cos(E0));
		}

		double xv = oem.getA() * (Angles.cos(E1) - oem.getE());
		double yv = oem.getA() * Math.sqrt(1.0 - oem.getE() * oem.getE())
				* Angles.sin(E0);

		double v = Angles.atan2(yv, xv);
		double r = Math.sqrt(xv * xv + yv * yv);

		double xh = r
				* (Angles.cos(oem.getN()) * Angles.cos(v + oem.getW()) - Angles
						.sin(oem.getN())
						* Angles.sin(v + oem.getW()) * Angles.cos(oem.getI()));
		double yh = r
				* (Angles.sin(oem.getN()) * Angles.cos(v + oem.getW()) + Angles
						.cos(oem.getN())
						* Angles.sin(v + oem.getW()) * Angles.cos(oem.getI()));
		double zh = r * Angles.sin(v + oem.getW()) * Angles.sin(oem.getI());

		double lonh = Angles.atan2(yh, xh);
		double lath = Angles.atan2(zh, Math.sqrt(xh * xh + yh * yh));
		double dist = Math.sqrt(xh * xh + yh * yh + zh * zh);

		OrbitalElements oes = OrbitalElements.getOrbitalElements(
				OrbitalElements.SUN, temp);
		double ls = oes.getN() + oes.getM() + oes.getW();
		double lm = oem.getN() + oem.getM() + oem.getW();
		double ms = oes.getM();
		double mm = oem.getM();
		double d = lm - ls;
		double f = lm - oem.getN();

		double pertLon = -1.274 * Angles.sin(mm - 2 * d) + 0.658
				* Angles.sin(2 * d) - 0.186 * Angles.sin(ms) - 0.059
				* Angles.sin(2 * mm - 2 * d) - 0.057
				* Angles.sin(mm - 2 * d + ms) + 0.053 * Angles.sin(mm + 2 * d)
				+ 0.046 * Angles.sin(2 * d - ms) + 0.041 * Angles.sin(mm - ms)
				- 0.035 * Angles.sin(d) - 0.031 * Angles.sin(mm + ms) - 0.015
				* Angles.sin(2 * f - 2 * d) + 0.011 * Angles.sin(mm - 4 * d);

		double pertLat = -0.173 * Angles.sin(f - 2 * d) - 0.055
				* Angles.sin(mm - f - 2 * d) - 0.046
				* Angles.sin(mm + f - 2 * d) + 0.033 * Angles.sin(f + 2 * d)
				+ 0.017 * Angles.sin(2 * mm + f);

		double pertDist = -0.58 * Angles.cos(mm - 2 * d) - 0.46
				* Angles.cos(2 * d);

		lonh = lonh + pertLon;
		lath = lath + pertLat;
		dist = dist + pertDist;

		xh = dist * Angles.cos(lonh) * Angles.cos(lath);
		yh = dist * Angles.sin(lonh) * Angles.cos(lath);
		zh = dist * Angles.sin(lath);

		double JD = new Double(temp.getTimeInMillis()) / 86400000 + 2440587.5;

		double T = (JD - 2451545.0) / 36525.0;
		double ecl = 23.0 + 26.0 / 60.0 + 21.448 / 3600.0
				- (46.8150 * T + 0.00059 * T * T - 0.001813 * T * T * T) / 3600;

		double xe = xh;
		double ye = yh * Angles.cos(ecl) - zh * Angles.sin(ecl);
		double ze = yh * Angles.sin(ecl) + zh * Angles.cos(ecl);

		double RA = Angles.atan2(ye, xe);
		double delta = Angles.atan2(ze, Math.sqrt(xe * xe + ye * ye));

		double theta0 = 280.46061837 + 360.98564736629 * (JD - 2451545.0)
				+ 0.000387933 * T * T - T * T * T / 38710000.0;
		theta0 = theta0 % 360;
		if (theta0 < 0)
			theta0 += 360;

		double theta = theta0 + longitude;
		double tau = theta - RA;
		if (tau < 0)
			tau += 360;

		double h = Angles.asin(Angles.sin(latitude) * Angles.sin(delta)
				+ Angles.cos(latitude) * Angles.cos(delta) * Angles.cos(tau));

		double az = Angles.atan2(-Angles.sin(tau), (Angles.cos(latitude)
				* Angles.tan(delta) - Angles.sin(latitude) * Angles.cos(tau)));

		return new double[] { h, az };
	}

	public double getMoonAltitude(Calendar c) {
		return getInfo(c)[0];
	}

	public double getMoonAzimuth(Calendar c) {
		return getInfo(c)[1];
	}

	public float[] getMoonRises(Calendar c) {

		Calendar test = Utils.getCalendarCopy(c);
		test.set(Calendar.HOUR_OF_DAY, 0);
		test.set(Calendar.MINUTE, 0);
		test.set(Calendar.SECOND, 0);
		test.set(Calendar.MILLISECOND, 0);

		List<Float> risesTemp = new ArrayList<Float>();

		double alt1 = getInfo(test)[0];
		test.add(Calendar.MINUTE, 1);
		double alt2 = getInfo(test)[0];
		int day = test.get(Calendar.DAY_OF_YEAR);
		while (day == test.get(Calendar.DAY_OF_YEAR)) {
			if (alt1 < 0 && alt2 > 0)
				risesTemp.add(test.get(Calendar.HOUR_OF_DAY)
						+ test.get(Calendar.MINUTE) / 60f);
			test.add(Calendar.MINUTE, 1);
			alt1 = alt2;
			alt2 = getInfo(test)[0];
		}

		float[] rises = new float[risesTemp.size()];

		for (int i = 0; i < risesTemp.size(); i++)
			rises[i] = risesTemp.get(i);

		return rises;
	}

	public float[] getMoonSets(Calendar c) {

		Calendar test = Utils.getCalendarCopy(c);
		test.set(Calendar.HOUR_OF_DAY, 0);
		test.set(Calendar.MINUTE, 0);
		test.set(Calendar.SECOND, 0);
		test.set(Calendar.MILLISECOND, 0);

		List<Float> setsTemp = new ArrayList<Float>();

		double alt1 = getInfo(test)[0];
		test.add(Calendar.MINUTE, 1);
		double alt2 = getInfo(test)[0];

		int day = test.get(Calendar.DAY_OF_YEAR);
		while (day == test.get(Calendar.DAY_OF_YEAR)) {
			if (alt1 > 0 && alt2 < 0)
				setsTemp.add(test.get(Calendar.HOUR_OF_DAY)
						+ test.get(Calendar.MINUTE) / 60f);
			test.add(Calendar.MINUTE, 1);
			alt1 = alt2;
			alt2 = getInfo(test)[0];
		}

		float[] sets = new float[setsTemp.size()];

		for (int i = 0; i < setsTemp.size(); i++)
			sets[i] = setsTemp.get(i);

		return sets;
	}

}
