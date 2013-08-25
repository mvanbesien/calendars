/**
 * $Id: SwatchClock.java,v 1.1 2008/11/05 08:53:47 maxence Exp $
 * 
 * HISTORY : 
 * -------
 * $Log: SwatchClock.java,v $
 * Revision 1.1  2008/11/05 08:53:47  maxence
 * Saved
 *
 * Revision 1.1  2007/11/07 19:47:16  bezien
 * Moved clocks to their respective packages
 *
 * Revision 1.1  2007/09/18 16:53:27  bezien
 * Initial revision
 *
 */
package fr.mvanbesien.calendars.clocks;

import java.util.Calendar;

/**
 * Swatch Clock Class
 * 
 * @author mvanbesien
 * 
 */
public class SwatchClock {

	/**
	 * Offset to apply, to get the BMT Time
	 */
	private static long BMT_OFFSET = 3600000;

	/**
	 * Returns the Swatch time for the current date
	 * 
	 * @return Swatch time (format : XXX.XX)
	 */
	public float getSwatchTime() {
		return getSwatchTime(Calendar.getInstance());
	}

	/**
	 * Returns the Swatch time for the calendar passed as parameter
	 * 
	 * @param calendar
	 *            : Calendar instance
	 * @return Swatch time (format : XXX.XX)
	 */
	public float getSwatchTime(Calendar calendar) {
		long temp = calendar.getTimeInMillis();
		temp = (temp + SwatchClock.BMT_OFFSET) % 86400000;
		double swatchTime = new Double(temp) * 100000 / 86400000;
		return new Float(new Double(swatchTime).intValue()) / 100;
	}
}
