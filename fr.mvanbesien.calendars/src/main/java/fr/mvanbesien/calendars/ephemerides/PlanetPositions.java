package fr.mvanbesien.calendars.ephemerides;

import java.util.Calendar;
import java.util.TimeZone;

import fr.mvanbesien.calendars.tools.Angles;
import fr.mvanbesien.calendars.tools.OrbitalElements;

public class PlanetPositions {

	public static final double ASTRONOMIC_UNIT = 149597870.691;
	public static final double EARTH_RADIUS = 6378.135;

	public static double[] getBodyPosition(OrbitalElements oe, boolean useEarthRadiusUnit) {

		double E0 = oe.getM() + 180 / Math.PI * oe.getE() * Angles.sin(oe.getM())
				* (1 + oe.getE() * Angles.cos(oe.getM()));

		double E1 = E0 - (E0 - 180 / Math.PI * oe.getE() * Angles.sin(E0) - oe.getM())
				/ (1 - oe.getE() * Angles.cos(E0));

		while (Math.abs(E1 - E0) > 0.005) {
			E0 = E1;
			E1 = E0 - (E0 - 180 / Math.PI * oe.getE() * Angles.sin(E0) - oe.getM()) / (1 - oe.getE() * Angles.cos(E0));
		}
		double x = oe.getA() * (Angles.cos(E1) - oe.getE());
		double y = oe.getA() * Math.sqrt(1 - oe.getE() * oe.getE()) * Angles.sin(E0);
		double r = Math.sqrt(x * x + y * y);
		double v = Angles.atan2(y, x);

		double xe = r
				* (Angles.cos(oe.getN()) * Angles.cos(v + oe.getW()) - Angles.sin(oe.getN())
						* Angles.sin(v + oe.getW()) * Angles.cos(oe.getI()));
		double ye = r
				* (Angles.sin(oe.getN()) * Angles.cos(v + oe.getW()) + Angles.cos(oe.getN())
						* Angles.sin(v + oe.getW()) * Angles.cos(oe.getI()));
		double ze = r * Angles.sin(v + oe.getW()) * Angles.sin(oe.getI());
		double c = useEarthRadiusUnit ? EARTH_RADIUS : ASTRONOMIC_UNIT;
		return new double[] { c * xe, c * ye, c * ze };
	}

	public static double[] getMoonPosition(Calendar calendar) {
		OrbitalElements oes = OrbitalElements.getOrbitalElements(OrbitalElements.SUN, calendar);
		OrbitalElements oem = OrbitalElements.getOrbitalElements(OrbitalElements.MOON, calendar);
		double[] bodyPosition = PlanetPositions.getBodyPosition(oem, true);

		double lonh = Angles.atan2(bodyPosition[1], bodyPosition[0]);
		double lath = Angles.atan2(bodyPosition[2],
				Math.sqrt(bodyPosition[0] * bodyPosition[0] + bodyPosition[1] * bodyPosition[1]));
		double dist = Math.sqrt(bodyPosition[0] * bodyPosition[0] + bodyPosition[1] * bodyPosition[1] + bodyPosition[2]
				* bodyPosition[2]);

		double ls = oes.getN() + oes.getM() + oes.getW();
		double lm = oem.getN() + oem.getM() + oem.getW();
		double ms = oes.getM();
		double mm = oem.getM();
		double d = lm - ls;
		double f = lm - oem.getN();

		double pertLon = -1.274 * Angles.sin(mm - 2 * d) + 0.658 * Angles.sin(2 * d) - 0.186 * Angles.sin(ms) - 0.059
				* Angles.sin(2 * mm - 2 * d) - 0.057 * Angles.sin(mm - 2 * d + ms) + 0.053 * Angles.sin(mm + 2 * d)
				+ 0.046 * Angles.sin(2 * d - ms) + 0.041 * Angles.sin(mm - ms) - 0.035 * Angles.sin(d) - 0.031
				* Angles.sin(mm + ms) - 0.015 * Angles.sin(2 * f - 2 * d) + 0.011 * Angles.sin(mm - 4 * d);

		double pertLat = -0.173 * Angles.sin(f - 2 * d) - 0.055 * Angles.sin(mm - f - 2 * d) - 0.046
				* Angles.sin(mm + f - 2 * d) + 0.033 * Angles.sin(f + 2 * d) + 0.017 * Angles.sin(2 * mm + f);

		double pertDist = -0.58 * Angles.cos(mm - 2 * d) - 0.46 * Angles.cos(2 * d);

		lonh = lonh + pertLon;
		lath = lath + pertLat;
		dist = dist + pertDist;

		bodyPosition[0] = dist * Angles.cos(lonh) * Angles.cos(lath);
		bodyPosition[1] = dist * Angles.sin(lonh) * Angles.cos(lath);
		bodyPosition[2] = dist * Angles.sin(lath);

		return bodyPosition;
	}

	public static double[] getSunPosition(Calendar calendar) {
		return PlanetPositions.getBodyPosition(OrbitalElements.getOrbitalElements(OrbitalElements.SUN, calendar), false);
	}

	public static double getDistanceWithSun(Calendar c) {
		double[] sunPosition = getSunPosition(c);
		return Math.sqrt(sunPosition[0] * sunPosition[0] + sunPosition[1] * sunPosition[1] + sunPosition[2]
				* sunPosition[2]);
	}

	public static double getDistanceWithMoon(Calendar c) {
		double[] moonPosition = PlanetPositions.getMoonPosition(c);
		return Math.sqrt(moonPosition[0] * moonPosition[0] + moonPosition[1] * moonPosition[1] + moonPosition[2]
				* moonPosition[2]);
	}

	public static double getDistanceWithMars(Calendar c) {

		OrbitalElements oes = OrbitalElements.getOrbitalElements(OrbitalElements.SUN, c);
		OrbitalElements oem = OrbitalElements.getOrbitalElements(OrbitalElements.MARS, c);
		double[] sunPosition = PlanetPositions.getBodyPosition(oes, false);
		double[] marsPosition = PlanetPositions.getBodyPosition(oem, false);

		double x = marsPosition[0] + sunPosition[0];
		double y = marsPosition[1] + sunPosition[1];
		double z = marsPosition[2] + sunPosition[2];

		return Math.sqrt(x * x + y * y + z * z);
	}

	public static double getDistanceWithJupiter(Calendar c) {

		OrbitalElements oes = OrbitalElements.getOrbitalElements(OrbitalElements.SUN, c);
		OrbitalElements oem = OrbitalElements.getOrbitalElements(OrbitalElements.JUPITER, c);
		double[] sunPosition = PlanetPositions.getBodyPosition(oes, false);
		double[] marsPosition = PlanetPositions.getBodyPosition(oem, false);

		double x = marsPosition[0] + sunPosition[0];
		double y = marsPosition[1] + sunPosition[1];
		double z = marsPosition[2] + sunPosition[2];

		return Math.sqrt(x * x + y * y + z * z);
	}

	public static double getDistanceWithSaturn(Calendar c) {

		OrbitalElements oes = OrbitalElements.getOrbitalElements(OrbitalElements.SUN, c);
		OrbitalElements oem = OrbitalElements.getOrbitalElements(OrbitalElements.SATURN, c);
		double[] sunPosition = PlanetPositions.getBodyPosition(oes, false);
		double[] marsPosition = PlanetPositions.getBodyPosition(oem, false);

		double x = marsPosition[0] + sunPosition[0];
		double y = marsPosition[1] + sunPosition[1];
		double z = marsPosition[2] + sunPosition[2];

		return Math.sqrt(x * x + y * y + z * z);
	}

	public static double getDistanceWithVenus(Calendar c) {

		OrbitalElements oes = OrbitalElements.getOrbitalElements(OrbitalElements.SUN, c);
		OrbitalElements oev = OrbitalElements.getOrbitalElements(OrbitalElements.VENUS, c);
		double[] sunPosition = PlanetPositions.getBodyPosition(oes, false);
		double[] venusPosition = PlanetPositions.getBodyPosition(oev, false);

		double x = venusPosition[0] + sunPosition[0];
		double y = venusPosition[1] + sunPosition[1];
		double z = venusPosition[2] + sunPosition[2];

		return Math.sqrt(x * x + y * y + z * z);
	}

	public static double getDistanceWithMercury(Calendar c) {

		OrbitalElements oes = OrbitalElements.getOrbitalElements(OrbitalElements.SUN, c);
		OrbitalElements oev = OrbitalElements.getOrbitalElements(OrbitalElements.MERCURY, c);
		double[] sunPosition = PlanetPositions.getBodyPosition(oes, false);
		double[] venusPosition = PlanetPositions.getBodyPosition(oev, false);

		double x = venusPosition[0] + sunPosition[0];
		double y = venusPosition[1] + sunPosition[1];
		double z = venusPosition[2] + sunPosition[2];

		return Math.sqrt(x * x + y * y + z * z);
	}

	public static double getDistanceWithUranus(Calendar c) {

		OrbitalElements oes = OrbitalElements.getOrbitalElements(OrbitalElements.SUN, c);
		OrbitalElements oev = OrbitalElements.getOrbitalElements(OrbitalElements.URANUS, c);
		double[] sunPosition = PlanetPositions.getBodyPosition(oes, false);
		double[] venusPosition = PlanetPositions.getBodyPosition(oev, false);

		double x = venusPosition[0] + sunPosition[0];
		double y = venusPosition[1] + sunPosition[1];
		double z = venusPosition[2] + sunPosition[2];

		return Math.sqrt(x * x + y * y + z * z);
	}

	public static double getDistanceWithNeptune(Calendar c) {

		OrbitalElements oes = OrbitalElements.getOrbitalElements(OrbitalElements.SUN, c);
		OrbitalElements oev = OrbitalElements.getOrbitalElements(OrbitalElements.NEPTUNE, c);
		double[] sunPosition = PlanetPositions.getBodyPosition(oes, false);
		double[] venusPosition = PlanetPositions.getBodyPosition(oev, false);

		double x = venusPosition[0] + sunPosition[0];
		double y = venusPosition[1] + sunPosition[1];
		double z = venusPosition[2] + sunPosition[2];

		return Math.sqrt(x * x + y * y + z * z);
	}

	public static double[] getLocation(int index, Calendar c, double longitude, double latitude) {

		OrbitalElements oes = OrbitalElements.getOrbitalElements(OrbitalElements.SUN, c);
		OrbitalElements oem = OrbitalElements.getOrbitalElements(index, c);
		double[] sunPosition = PlanetPositions.getBodyPosition(oes, false);
		double[] eltPosition = PlanetPositions.getBodyPosition(oem, false);

		double xg = eltPosition[0] + sunPosition[0];
		double yg = eltPosition[1] + sunPosition[1];
		double zg = eltPosition[2] + sunPosition[2];

		double xe = xg;
		double ye = yg * Angles.cos(oem.getEcl()) - zg * Angles.sin(oem.getEcl());
		double ze = yg * Angles.sin(oem.getEcl()) + zg * Angles.cos(oem.getEcl());

		double ra = Angles.atan2(ye, xe);
		double dec = Angles.atan2(ze, Math.sqrt(xe * xe + ye * ye));

		double gmst0 = oes.getM() + oes.getW() + 180;
		double lst = gmst0
				+ longitude
				+ (c.get(Calendar.HOUR_OF_DAY) + c.get(Calendar.MINUTE) / 60f + c.get(Calendar.SECOND) / 3600f - (c
						.get(Calendar.DST_OFFSET) + c.get(Calendar.ZONE_OFFSET)) / 3600000) * 15; // REALLY
		double ha = lst - ra;

		double xhor = Angles.cos(ha) * Angles.cos(dec) * Angles.sin(latitude) - Angles.sin(dec) * Angles.cos(latitude);
		double yhor = Angles.sin(ha) * Angles.cos(dec);
		double zhor = Angles.cos(ha) * Angles.cos(dec) * Angles.cos(latitude) + Angles.sin(dec) * Angles.sin(latitude);

		double azimuth = (Angles.atan2(yhor, xhor) + 180) % 360;
		double altitude = Angles.asin(zhor);

		// double slon = Angles.atan2(sunPosition[1], sunPosition[0]);
		//
		// double mlon = Angles.atan2(eltPosition[1], eltPosition[0]);
		// double mlat = Angles.atan2(eltPosition[2],
		// Math.sqrt(eltPosition[1] * eltPosition[1] + eltPosition[0] *
		// eltPosition[0]));

		double sunEarthDistance = Math.sqrt(sunPosition[0] * sunPosition[0] + sunPosition[1] * sunPosition[1]
				+ sunPosition[2] * sunPosition[2]);
		double geocentricDistance = Math.sqrt(xg * xg + yg * yg + zg * zg);
		double heliocentricDistance = Math.sqrt(eltPosition[0] * eltPosition[0] + eltPosition[1] * eltPosition[1]
				+ eltPosition[2] * eltPosition[2]);
		;

		double FV = Angles
				.acos((+geocentricDistance * geocentricDistance + heliocentricDistance * heliocentricDistance - sunEarthDistance
						* sunEarthDistance)
						/ (2 * heliocentricDistance * geocentricDistance));
		double phase = (1 + Angles.cos(FV)) / 2;

		double angularAppearance = 2 * (Angles.atan2(2 * getRadius(index), 2 * geocentricDistance)) * 3600;

		return new double[] {
				altitude,
				azimuth,
				phase,
				getBrightness(index, heliocentricDistance / PlanetPositions.ASTRONOMIC_UNIT, geocentricDistance
						/ PlanetPositions.ASTRONOMIC_UNIT, FV, c), angularAppearance, phase, geocentricDistance };
	}

	public static String display(double[] elements) {
		return String.format("Alt: %5.1f�, Az: %5.1f�, Ph: %5.1f%%, App: %04.1f as", elements[0], elements[1],
				elements[2] * 100, elements[3]);
	}

	public static void main(String[] args) throws Exception {
		Calendar c = Calendar.getInstance();
		System.out.println(display(getLocation(OrbitalElements.MERCURY, c, 3, 50)));
		System.out.println(display(getLocation(OrbitalElements.VENUS, c, 3, 50)));
		System.out.println(display(getLocation(OrbitalElements.MARS, c, 3, 50)));
		System.out.println(display(getLocation(OrbitalElements.JUPITER, c, 3, 50)));
		System.out.println(display(getLocation(OrbitalElements.SATURN, c, 3, 50)));
		System.out.println(display(getLocation(OrbitalElements.URANUS, c, 3, 50)));
		System.out.println(display(getLocation(OrbitalElements.NEPTUNE, c, 3, 50)));
	}

	public static double getBrightness(int index, double r, double R, double FV, Calendar c) {
		switch (index) {
		case OrbitalElements.MERCURY:
			return -0.36 + 5 * Math.log10(r * R) + 0.027 * FV + 2.2E-13 * Math.pow(FV, 6);
		case OrbitalElements.VENUS:
			return -4.34 + 5 * Math.log10(r * R) + 0.013 * FV + 4.2E-7 * Math.pow(FV, 3);
		case OrbitalElements.MOON:
			return +0.23 + 5 * Math.log10(r * R) + 0.026 * FV + 4.0E-9 * Math.pow(FV, 4);
		case OrbitalElements.MARS:
			return -1.51 + 5 * Math.log10(r * R) + 0.016 * FV;
		case OrbitalElements.JUPITER:
			return -9.25 + 5 * Math.log10(r * R) + 0.014 * FV;
		case OrbitalElements.SATURN:
			return -9.0 + 5 * Math.log10(r * R) + 0.044 * FV + getRingMagnitude(c);
		case OrbitalElements.URANUS:
			return -7.15 + 5 * Math.log10(r * R) + 0.001 * FV;
		case OrbitalElements.NEPTUNE:
			return -6.90 + 5 * Math.log10(r * R) + 0.001 * FV;
		}
		return 0;
	}

	private static double getRingMagnitude(Calendar c) {
		OrbitalElements oes = OrbitalElements.getOrbitalElements(OrbitalElements.SATURN, c);
		double[] bodyPosition = PlanetPositions.getBodyPosition(oes, true);
		double los = Angles.atan2(bodyPosition[1], bodyPosition[0]);
		double las = Angles.atan2(bodyPosition[2],
				Math.sqrt(bodyPosition[0] * bodyPosition[0] + bodyPosition[1] * bodyPosition[1]));

		Calendar reference = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		reference.set(2000, 0, 1, 0, 0, 0);
		reference.set(Calendar.MILLISECOND, 0);

		double d = new Double(c.getTimeInMillis() - reference.getTimeInMillis()) / (86400f * 1000f) + 1;
		double ir = 28.06;
		double Nr = 169.51 + 3.82E-5 * d;

		double B = Angles.asin(Angles.sin(las) * Angles.cos(ir) - Angles.cos(las) * Angles.sin(ir)
				* Angles.sin(los - Nr));
		return -2.6 * Angles.sin(Math.abs(B)) + 1.2 * Math.pow(Angles.sin(B), 2);
	}

	public static double getRadius(int index) {
		switch (index) {
		case OrbitalElements.SUN:
			return 696342;
		case OrbitalElements.MERCURY:
			return 2439.7;
		case OrbitalElements.VENUS:
			return 6051.8;
		case OrbitalElements.MOON:
			return 1737.1;
		case OrbitalElements.MARS:
			return 3396.2;
		case OrbitalElements.JUPITER:
			return 69911;
		case OrbitalElements.SATURN:
			return 60268;
		case OrbitalElements.URANUS:
			return 25559;
		case OrbitalElements.NEPTUNE:
			return 24764;

		default:
			return -1;
		}
	}
}
