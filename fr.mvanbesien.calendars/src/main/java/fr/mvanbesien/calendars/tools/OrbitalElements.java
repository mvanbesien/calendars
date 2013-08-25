/**
 * $Id: OrbitalElements.java,v 1.2 2009/02/24 10:45:22 maxence Exp $
 * 
 * HISTORY
 * -------
 * $Log: OrbitalElements.java,v $
 * Revision 1.2  2009/02/24 10:45:22  maxence
 * MVA : Fixed OrbitalElement reference
 *
 * Revision 1.1  2008/11/05 08:53:38  maxence
 * Saved
 *
 * Revision 1.1  2007/08/30 11:12:35  bezien
 * Initial revision
 *
 */
package fr.mvanbesien.calendars.tools;

import java.util.Calendar;
import java.util.TimeZone;

public class OrbitalElements {

	public static final int SUN = 0;

	public static final int MOON = 1;

	public static final int MERCURY = 2;

	public static final int VENUS = 3;

	public static final int MARS = 4;

	public static final int JUPITER = 5;

	public static final int SATURN = 6;

	public static final int URANUS = 7;

	public static final int NEPTUNE = 8;

	public static OrbitalElements getOrbitalElements(int elementIndex,
			Calendar calendar) {
		Calendar reference = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		reference.set(2000, 0, 1, 0, 0, 0);
		reference.set(Calendar.MILLISECOND, 0);

		double d = (calendar.getTimeInMillis() - reference.getTimeInMillis())
				/ (86400f * 1000f) + 1;
		OrbitalElements o = null;
		switch (elementIndex) {
		case SUN:
			o = new OrbitalElements(0.0, 0.0, 282.9404 + 4.70935E-5 * d,
					1.000000, 0.016709 - 1.151E-9 * d,
					356.0470 + 0.9856002585 * d, 23.4393 - 3.563E-7 * d);
			break;
		case MOON:
			o = new OrbitalElements(125.1228 - 0.0529538083 * d, 5.1454,
					318.0634 + 0.1643573223 * d, 60.2666, 0.054900,
					115.3654 + 13.0649929509 * d, 23.4393 - 3.563E-7 * d);
			break;
		case MERCURY:
			o = new OrbitalElements(48.3313 + 3.24587E-5 * d,
					7.0047 + 5.00E-8 * d, 29.1241 + 1.01444E-5 * d, 0.387098,
					0.205635 + 5.59E-10 * d, 168.6562 + 4.0923344368 * d,
					23.4393 - 3.563E-7 * d);
			break;
		case VENUS:
			o = new OrbitalElements(76.6799 + 2.46590E-5 * d,
					3.3946 + 2.75E-8 * d, 54.8910 + 1.38374E-5 * d, 0.723330,
					0.006773 - 1.302E-9 * d, 48.0052 + 1.6021302244 * d,
					23.4393 - 3.563E-7 * d);
			break;
		case MARS:
			o = new OrbitalElements(49.5574 + 2.11081E-5 * d,
					1.8497 - 1.78E-8 * d, 286.5016 + 2.92961E-5 * d, 1.523688,
					0.093405 + 2.516E-9 * d, 18.6021 + 0.5240207766 * d,
					23.4393 - 3.563E-7 * d);
			break;
		case JUPITER:
			o = new OrbitalElements(100.4542 + 2.76854E-5 * d,
					1.3030 - 1.557E-7 * d, 273.8777 + 1.64505E-5 * d, 5.20256,
					0.048498 + 4.469E-9 * d, 19.8950 + 0.0830853001 * d,
					23.4393 - 3.563E-7 * d);
			break;
		case SATURN:
			o = new OrbitalElements(113.6634 + 2.38980E-5 * d,
					2.4886 - 1.081E-7 * d, 339.3939 + 2.97661E-5 * d, 9.55475,
					0.055546 - 9.499E-9 * d, 316.9670 + 0.0334442282 * d,
					23.4393 - 3.563E-7 * d);
			break;
		case URANUS:
			o = new OrbitalElements(74.0005 + 1.3978E-5 * d,
					0.7733 + 1.9E-8 * d, 96.6612 + 3.0565E-5 * d,
					19.18171 - 1.55E-8 * d, 0.047318 + 7.45E-9 * d,
					142.5905 + 0.011725806 * d, 23.4393 - 3.563E-7 * d);
			break;
		case NEPTUNE:
			o = new OrbitalElements(131.7806 + 3.0173E-5 * d,
					1.7700 - 2.55E-7 * d, 272.8461 - 6.027E-6 * d,
					30.05826 + 3.313E-8 * d, 0.008606 + 2.15E-9 * d,
					260.2471 + 0.005995147 * d, 23.4393 - 3.563E-7 * d);
			break;

		default:
			return null;
		}
		return o;
	}

	private double N;

	private double i;

	private double w;

	private double a;

	private double e;

	private double M;

	private double ecl;

	private OrbitalElements(double N, double i, double w, double a, double e,
			double M, double ecl) {
		this.N = N;
		this.i = i;
		this.w = w % 360;
		while (this.w < 0)
			this.w += 360;
		this.a = a;
		this.e = e;
		this.M = M % 360;
		while (this.M < 0)
			this.M += 360;
		this.ecl = ecl;

	}

	public double getA() {
		return a;
	}

	public double getE() {
		return e;
	}

	public double getEcl() {
		return ecl;
	}

	public double getI() {
		return i;
	}

	public double getM() {
		return M;
	}

	public double getN() {
		return N;
	}

	public double getW() {
		return w;
	}

}
