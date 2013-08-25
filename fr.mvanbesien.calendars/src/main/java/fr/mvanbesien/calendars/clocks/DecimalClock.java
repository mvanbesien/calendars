/**
 * $Id: DecimalClock.java,v 1.1 2008/11/05 08:53:47 maxence Exp $
 * 
 * HISTORY : 
 * -------
 * $Log: DecimalClock.java,v $
 * Revision 1.1  2008/11/05 08:53:47  maxence
 * Saved
 *
 * Revision 1.1  2007/11/07 19:47:16  bezien
 * Moved clocks to their respective packages
 *
 * Revision 1.1  2007/09/20 18:54:36  bezien
 * Initial Revision
 *
 */
package fr.mvanbesien.calendars.clocks;

import java.util.Calendar;

import fr.mvanbesien.calendars.tools.Utils;

/**
 * Converts the sexagesimal time into a decimal time
 * 
 * @author mvanbesien
 * 
 */
public class DecimalClock {

	/**
	 * Converts the current hour into decimal time
	 * 
	 * @return String
	 */
	public static String getHour() {
		return DecimalClock.getHour(Calendar.getInstance());
	}

	/**
	 * Converts the hours passed as parameter via Calendar instance, into
	 * decimal time
	 * 
	 * @param instance
	 *            : Calendar instance
	 * @return String
	 */
	public static String getHour(Calendar instance) {
		int hours = instance.get(Calendar.HOUR_OF_DAY);
		int minutes = instance.get(Calendar.MINUTE);
		int seconds = instance.get(Calendar.SECOND);
		int milliseconds = instance.get(Calendar.MILLISECOND);

		long time = hours;
		time = time * 60 + minutes;
		time = time * 60 + seconds;
		time = time * 1000 + milliseconds;
		time = time * 1000 / 864;
		time = time / 1000;

		int deciseconds = new Long(time % 100).intValue();
		time = time / 100;
		int deciminutes = new Long(time % 100).intValue();
		time = time / 100;
		int decihours = new Long(time % 10).intValue();

		return Utils.setPrefix(decihours, 2) + ":"
				+ Utils.setPrefix(deciminutes, 2) + ":"
				+ Utils.setPrefix(deciseconds, 2);
	}

	/**
	 * TODO to be removed
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(DecimalClock.getHour());
	}
}
