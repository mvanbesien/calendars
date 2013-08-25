/**
 * $Id: ZodiacSign.java,v 1.3 2008/11/05 10:13:35 maxence Exp $
 * 
 * HISTORY
 * -------
 * $Log: ZodiacSign.java,v $
 * Revision 1.3  2008/11/05 10:13:35  maxence
 * Deledated image management to another plugin
 *
 * Revision 1.2  2008/11/05 09:55:12  maxence
 * Fixed
 *
 * Revision 1.1  2008/11/05 08:53:48  maxence
 * Saved
 *
 * Revision 1.1  2007/08/30 11:12:37  bezien
 * Initial revision
 *
 */
package fr.mvanbesien.calendars.ephemerides;

import java.util.Calendar;

import fr.mvanbesien.calendars.tools.Angles;
import fr.mvanbesien.calendars.tools.OrbitalElements;
import fr.mvanbesien.calendars.tools.Utils;

/**
 * This Class gets the Zodiac sign from the calendar passed as parameter.
 * 
 * @author mvanbesien
 * 
 */
public class ZodiacSign {

	/**
	 * Zodiac sign names, in english
	 */
	public static final String[] ZODIAC_SIGNS = new String[] { "Aries", "Taurus", "Gemini",
			"Cancer", "Leo", "Virgo", "Libra", "Scorpio", "Sagittarius", "Capricornus", "Aquarius",
			"Pisces" };

	public static String getZodiacSign() {
		return ZodiacSign.getZodiacSign(Calendar.getInstance());
	}

	public static String getZodiacSign(Calendar c) {
		double solarEclipticLongitude = getSolarEclipticLongitude(c);
		String sign = ZODIAC_SIGNS[(int) (solarEclipticLongitude / 30)];
		int decan = (int) (solarEclipticLongitude / 10 % 3 + 1);
		return sign + ", " + Utils.ordinal(decan, true) + " decan";
	}

	private static double getSolarEclipticLongitude(Calendar instance) {
		OrbitalElements oes = OrbitalElements.getOrbitalElements(OrbitalElements.SUN, instance);

		double E0 = oes.getM() + oes.getE() * 180 / Math.PI * Angles.sin(oes.getM())
				* (1.0 + oes.getE() * Angles.cos(oes.getM()));
		double E1 = E0 - (E0 - oes.getE() * 180 / Math.PI * Angles.sin(E0) - oes.getM())
				/ (1 - oes.getE() * Angles.cos(E0));
		while (E1 - E0 > 0.005) {
			E0 = E1;
			E1 = E0 - (E0 - oes.getE() * 180 / Math.PI * Angles.sin(E0) - oes.getM())
					/ (1 - oes.getE() * Angles.cos(E0));
		}

		double xv = oes.getA() * (Angles.cos(E0) - oes.getE());
		double yv = oes.getA() * Math.sqrt(1.0 - oes.getE() * oes.getE()) * Angles.sin(E0);

		double v = Angles.atan2(yv, xv);
		double r = Math.sqrt(xv * xv + yv * yv);

		double xh = r
				* (Angles.cos(oes.getN()) * Angles.cos(v + oes.getW()) - Angles.sin(oes.getN())
						* Angles.sin(v + oes.getW()) * Angles.cos(oes.getI()));
		double yh = r
				* (Angles.sin(oes.getN()) * Angles.cos(v + oes.getW()) + Angles.cos(oes.getN())
						* Angles.sin(v + oes.getW()) * Angles.cos(oes.getI()));

		return Angles.atan2(yh, xh);

	}
}
