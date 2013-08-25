package fr.mvanbesien.calendars.ephemerides;

import java.util.Calendar;
import java.util.Date;

import fr.mvanbesien.calendars.tools.Angles;
import fr.mvanbesien.calendars.tools.OrbitalElements;
import fr.mvanbesien.calendars.tools.Utils;

public class LunarPhases {

	private static final String[] MOON_PHASES = new String[] { "New Moon",
			"Waxing Crescent", "First Quarter", "Waxing Gibbous", "Full Moon",
			"Waning Gibbous", "Last Quarter", "Waning Crescent", "New Moon" };

	private static double[] getBodyPosition(OrbitalElements oe,
			boolean useEarthRadiusUnit) {

		double E0 = oe.getM() + 180 / Math.PI * oe.getE()
				* Angles.sin(oe.getM())
				* (1 + oe.getE() * Angles.cos(oe.getM()));

		double E1 = E0
				- (E0 - 180 / Math.PI * oe.getE() * Angles.sin(E0) - oe.getM())
				/ (1 - oe.getE() * Angles.cos(E0));

		while (Math.abs(E1 - E0) > 0.005) {
			E0 = E1;
			E1 = E0
					- (E0 - 180 / Math.PI * oe.getE() * Angles.sin(E0) - oe
							.getM()) / (1 - oe.getE() * Angles.cos(E0));
		}
		double x = oe.getA() * (Angles.cos(E1) - oe.getE());
		double y = oe.getA() * Math.sqrt(1 - oe.getE() * oe.getE())
				* Angles.sin(E0);
		double r = Math.sqrt(x * x + y * y);
		double v = Angles.atan2(y, x);

		double xe = r
				* (Angles.cos(oe.getN()) * Angles.cos(v + oe.getW()) - Angles
						.sin(oe.getN())
						* Angles.sin(v + oe.getW()) * Angles.cos(oe.getI()));
		double ye = r
				* (Angles.sin(oe.getN()) * Angles.cos(v + oe.getW()) + Angles
						.cos(oe.getN())
						* Angles.sin(v + oe.getW()) * Angles.cos(oe.getI()));
		double ze = r * Angles.sin(v + oe.getW()) * Angles.sin(oe.getI());
		double au = 149597870.691;
		double er = 6378.135;
		double c = useEarthRadiusUnit ? er : au;
		return new double[] { c * xe, c * ye, c * ze };

	}

	public static float getMoonPhase() {
		return LunarPhases.getMoonPhase(Calendar.getInstance());
	}

	public static float getMoonPhase(Calendar c) {
		double[] sunPosition = LunarPhases.getSunPosition(c);
		double[] moonPosition = LunarPhases.getMoonPosition(c);

		double slon = Angles.atan2(sunPosition[1], sunPosition[0]);

		double mlon = Angles.atan2(moonPosition[1], moonPosition[0]);
		double mlat = Angles.atan2(moonPosition[2], Math.sqrt(moonPosition[1]
				* moonPosition[1] + moonPosition[0] * moonPosition[0]));

		double elong = Angles.acos(Angles.cos(slon - mlon) * Angles.cos(mlat));
		double FV = 180 - elong;
		double phase = (1 + Angles.cos(FV)) / 2;
		return new Double(phase).floatValue() * 100;
	}

	public static int getMoonPhaseIndex() {
		return LunarPhases.getMoonPhaseIndex(Calendar.getInstance());
	}

	public static int getMoonPhaseIndex(Calendar c) {
		float phase = LunarPhases.getMoonPhase(c);

		Date nextNewMoon = LunarPhases.getNextFullMoon(c);
		Date nextFullMoon = LunarPhases.getNextNewMoon(c);

		float index = 0;
		if (nextFullMoon.after(nextNewMoon)) {
			index = (phase + 12.5f) * 4 / 100;
		} else {
			index = 9 - ((phase + 12.5f) * 4 / 100);
		}
		return new Float(index).intValue();
	}

	public static String getMoonPhaseName() {
		return LunarPhases.getMoonPhaseName(Calendar.getInstance());
	}

	public static String getMoonPhaseName(Calendar c) {
		return LunarPhases.MOON_PHASES[LunarPhases.getMoonPhaseIndex(c)];
	}

	public static double[] getMoonPosition(Calendar calendar) {
		OrbitalElements oes = OrbitalElements.getOrbitalElements(
				OrbitalElements.SUN, calendar);
		OrbitalElements oem = OrbitalElements.getOrbitalElements(
				OrbitalElements.MOON, calendar);
		double[] bodyPosition = LunarPhases.getBodyPosition(oem, true);

		if (true) {
			double lonh = Angles.atan2(bodyPosition[1], bodyPosition[0]);
			double lath = Angles.atan2(bodyPosition[2], Math
					.sqrt(bodyPosition[0] * bodyPosition[0] + bodyPosition[1]
							* bodyPosition[1]));
			double dist = Math.sqrt(bodyPosition[0] * bodyPosition[0]
					+ bodyPosition[1] * bodyPosition[1] + bodyPosition[2]
					* bodyPosition[2]);

			double ls = oes.getN() + oes.getM() + oes.getW();
			double lm = oem.getN() + oem.getM() + oem.getW();
			double ms = oes.getM();
			double mm = oem.getM();
			double d = lm - ls;
			double f = lm - oem.getN();

			double pertLon = -1.274 * Angles.sin(mm - 2 * d) + 0.658
					* Angles.sin(2 * d) - 0.186 * Angles.sin(ms) - 0.059
					* Angles.sin(2 * mm - 2 * d) - 0.057
					* Angles.sin(mm - 2 * d + ms) + 0.053
					* Angles.sin(mm + 2 * d) + 0.046 * Angles.sin(2 * d - ms)
					+ 0.041 * Angles.sin(mm - ms) - 0.035 * Angles.sin(d)
					- 0.031 * Angles.sin(mm + ms) - 0.015
					* Angles.sin(2 * f - 2 * d) + 0.011
					* Angles.sin(mm - 4 * d);

			double pertLat = -0.173 * Angles.sin(f - 2 * d) - 0.055
					* Angles.sin(mm - f - 2 * d) - 0.046
					* Angles.sin(mm + f - 2 * d) + 0.033
					* Angles.sin(f + 2 * d) + 0.017 * Angles.sin(2 * mm + f);

			double pertDist = -0.58 * Angles.cos(mm - 2 * d) - 0.46
					* Angles.cos(2 * d);

			lonh = lonh + pertLon;
			lath = lath + pertLat;
			dist = dist + pertDist;

			bodyPosition[0] = dist * Angles.cos(lonh) * Angles.cos(lath);
			bodyPosition[1] = dist * Angles.sin(lonh) * Angles.cos(lath);
			bodyPosition[2] = dist * Angles.sin(lath);
		}
		return bodyPosition;
	}

	public static Date getNextFullMoon() {
		return LunarPhases.getNextFullMoon(Calendar.getInstance());
	}

	public static Date getNextFullMoon(Calendar c) {
		Calendar fm = Utils.getCalendarCopy(c);
		fm.set(Calendar.SECOND, 0);
		fm.set(Calendar.MILLISECOND, 0);

		float phase1 = LunarPhases.getMoonPhase(fm);
		fm.add(Calendar.MINUTE, 1);
		float phase2 = LunarPhases.getMoonPhase(fm);
		float nbDeriv = (phase2 - phase1) / 1;
		while (nbDeriv < 0) {
			fm.add(Calendar.DAY_OF_WEEK, 1);
			phase1 = phase2;
			phase2 = LunarPhases.getMoonPhase(fm);
			nbDeriv = (phase2 - phase1) / 1440;
		}
		while (nbDeriv > 0 || phase2 < 50) {
			fm.add(Calendar.DAY_OF_YEAR, 1);
			phase1 = phase2;
			phase2 = LunarPhases.getMoonPhase(fm);
			nbDeriv = (phase2 - phase1) / 1440;
		}

		fm.add(Calendar.DAY_OF_YEAR, -2);
		phase2 = phase1;
		phase1 = LunarPhases.getMoonPhase(fm);
		nbDeriv = (phase2 - phase1) / 1440;

		while (nbDeriv >= 0) {
			phase1 = LunarPhases.getMoonPhase(fm);
			fm.add(Calendar.MINUTE, 1);
			phase2 = LunarPhases.getMoonPhase(fm);
			nbDeriv = (phase2 - phase1) / 1;
		}
		return fm.getTime();
	}

	public static Date getNextNewMoon() {
		return LunarPhases.getNextNewMoon(Calendar.getInstance());
	}

	public static Date getNextNewMoon(Calendar c) {
		Calendar fm = Utils.getCalendarCopy(c);
		fm.set(Calendar.SECOND, 0);
		fm.set(Calendar.MILLISECOND, 0);

		float phase1 = LunarPhases.getMoonPhase(fm);
		fm.add(Calendar.MINUTE, 1);
		float phase2 = LunarPhases.getMoonPhase(fm);
		float nbDeriv = (phase2 - phase1) / 1;
		while (nbDeriv > 0) {
			fm.add(Calendar.DAY_OF_WEEK, 1);
			phase1 = phase2;
			phase2 = LunarPhases.getMoonPhase(fm);
			nbDeriv = (phase2 - phase1) / 1440;
		}
		while (nbDeriv < 0 || phase2 > 50) {
			fm.add(Calendar.DAY_OF_YEAR, 1);
			phase1 = phase2;
			phase2 = LunarPhases.getMoonPhase(fm);
			nbDeriv = (phase2 - phase1) / 1440;
		}

		fm.add(Calendar.DAY_OF_YEAR, -2);
		phase2 = phase1;
		phase1 = LunarPhases.getMoonPhase(fm);
		nbDeriv = (phase2 - phase1) / 1440;

		while (nbDeriv <= 0) {
			phase1 = LunarPhases.getMoonPhase(fm);
			fm.add(Calendar.MINUTE, 1);
			phase2 = LunarPhases.getMoonPhase(fm);
			nbDeriv = (phase2 - phase1) / 1;
		}
		return fm.getTime();
	}

	public static double[] getSunPosition(Calendar calendar) {
		return LunarPhases.getBodyPosition(OrbitalElements.getOrbitalElements(
				OrbitalElements.SUN, calendar), false);
	}

}
