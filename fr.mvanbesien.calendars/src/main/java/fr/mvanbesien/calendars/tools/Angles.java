/**
 * $Id: Angles.java,v 1.1 2008/11/05 08:53:38 maxence Exp $
 * 
 * HISTORY
 * -------
 * $Log: Angles.java,v $
 * Revision 1.1  2008/11/05 08:53:38  maxence
 * Saved
 *
 * Revision 1.1  2007/08/30 11:12:35  bezien
 * Initial revision
 *
 */
package fr.mvanbesien.calendars.tools;

/**
 * Computes trigonometric formulas in degrees
 * 
 * @author mvanbesien
 * 
 */
public class Angles {

	public static double acos(double value) {
		return 180 / Math.PI * java.lang.Math.acos(value);
	}

	public static double asin(double value) {
		return 180 / Math.PI * java.lang.Math.asin(value);
	}

	public static double atan(double value) {
		return 180 / Math.PI * java.lang.Math.atan(value);
	}

	public static double atan2(double y, double x) {
		double value = Angles.atan(y / x);
		if (x < 0)
			return 180 + value;
		else if (x == 0)
			return Math.signum(y) * 90;
		else {

		}
		while (value < 0)
			value += 360;
		return value;
	}

	public static double cos(double angle) {
		return java.lang.Math.cos(angle / 180 * Math.PI);
	}

	public static double sin(double angle) {
		return java.lang.Math.sin(angle / 180 * Math.PI);
	}

	public static double tan(double angle) {
		return java.lang.Math.tan(angle / 180 * Math.PI);
	}

}
