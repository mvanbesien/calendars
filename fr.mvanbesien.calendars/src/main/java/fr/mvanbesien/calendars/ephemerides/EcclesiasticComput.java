/**
 * $Id: EcclesiasticComput.java,v 1.3 2009/02/17 09:25:52 maxence Exp $
 * 
 * HISTORY
 * -------
 * $Log: EcclesiasticComput.java,v $
 * Revision 1.3  2009/02/17 09:25:52  maxence
 * MVA : Added Dominical Letter
 *
 * Revision 1.2  2009/02/09 13:10:18  maxence
 * Epaque changed to Epacte
 *
 * Revision 1.1  2008/11/05 08:53:47  maxence
 * Saved
 *
 * Revision 1.1  2007/08/30 11:12:37  bezien
 * Initial revision
 *
 */
package fr.mvanbesien.calendars.ephemerides;

import java.util.Calendar;

import fr.mvanbesien.calendars.tools.Utils;

/**
 * This Class converts a Gregorian Calendar Date Year into Ecclesiastic Comput
 * variables.
 * 
 * @author mvanbesien
 * 
 */
public class EcclesiasticComput {

	/**
	 * @return Ecclesiastic Comput values as String for the current day
	 */
	public static String getComputAsString() {
		return EcclesiasticComput.getComputAsString(Calendar.getInstance());
	}

	/**
	 * @param Calendar
	 *            Instance
	 * @return Ecclesiastic Comput values as String for the date passed as
	 *         parameter
	 */

	private static String getComputAsString(Calendar calendar) {
		return "(" + EcclesiasticComput.getSolarCycle(calendar) + ", "
				+ EcclesiasticComput.getGoldNumber(calendar) + ", "
				+ EcclesiasticComput.getIndiction(calendar) + ")";
	}

	/**
	 * Returns the dominical letter for the current date
	 * 
	 * @return dominical letter
	 */
	public static String getDominicalLetter() {
		return EcclesiasticComput.getDominicalLetter(Calendar.getInstance());
	}

	/**
	 * Returns the dominical letter for the date passed as parameter
	 * 
	 * @param calendar
	 *            : Calendar instance
	 * 
	 * @return dominical letter
	 */
	public static String getDominicalLetter(Calendar instance) {
		int m = instance.get(Calendar.YEAR);
		int c = m / 100;
		int u = m % 100;
		int sum = 2 * c - c / 4 - u - u / 4;
		while (sum < 0)
			sum = sum + 7;
		String letter = new Character((char) (sum % 7 + 'A')).toString();
		if (Utils.isLeapYear(instance.get(Calendar.YEAR)))
			letter = new Character((char) ((sum + 1) % 7 + 'A')).toString()
					.concat(letter);
		return letter;
	}

	/**
	 * Returns the Epacte for the current date
	 * 
	 * @return Epacte
	 */
	public static int getEpacte() {
		return EcclesiasticComput.getEpacte(Calendar.getInstance());
	}

	/**
	 * Returns the Epaque for the date passed as parameter
	 * 
	 * @param calendar
	 *            : Calendar Instance
	 * @return Epaque
	 */
	public static int getEpacte(Calendar calendar) {
		return ((EcclesiasticComput.getGoldNumber(calendar) + 1) * 11 + 7) % 30;
	}

	/**
	 * Returns the Gold Number for the current day
	 * 
	 * @return Gold Number
	 */
	public static int getGoldNumber() {
		return EcclesiasticComput.getGoldNumber(Calendar.getInstance());
	}

	/**
	 * Returns the Gold Number for the date passed as parameter
	 * 
	 * @param calendar
	 *            : Calendar instance
	 * @return Gold Number
	 */
	public static int getGoldNumber(Calendar calendar) {
		int year = calendar.get(Calendar.YEAR);
		return year % 19 + 1;
	}

	/**
	 * Returns the Indiction for the current day
	 * 
	 * @return Indiction
	 */
	public static int getIndiction() {
		return EcclesiasticComput.getIndiction(Calendar.getInstance());
	}

	/**
	 * Returns the Indiction for the date passed as parameter
	 * 
	 * @param calendar
	 *            : Calendar instance
	 * @return Indiction
	 */
	public static int getIndiction(Calendar calendar) {
		int year = calendar.get(Calendar.YEAR);
		return (year + 2) % 15 + 1;
	}

	/**
	 * Returns the Solar Cycle for the current day
	 * 
	 * @return the Solar Cycle
	 */
	public static int getSolarCycle() {
		return EcclesiasticComput.getSolarCycle(Calendar.getInstance());
	}

	/**
	 * Returns the Solar Cycle for the date passed as parameter
	 * 
	 * @param calendar
	 *            instance
	 * @return the Solar Cycle
	 */
	public static int getSolarCycle(Calendar calendar) {
		int year = calendar.get(Calendar.YEAR);
		return (year + 8) % 28 + 1;
	}

}
