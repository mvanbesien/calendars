/**
 * $Id: FuzzyTime.java,v 1.1 2008/11/05 08:53:47 maxence Exp $
 * 
 * HISTORY
 * -------
 * $Log: FuzzyTime.java,v $
 * Revision 1.1  2008/11/05 08:53:47  maxence
 * Saved
 *
 * Revision 1.4  2007/11/13 18:01:06  bezien
 * Added caller for current time
 *
 * Revision 1.3  2007/11/09 11:37:46  bezien
 * Added noon and midnight management
 *
 * Revision 1.2  2007/11/07 19:58:31  bezien
 * Bug fixed
 *
 * Revision 1.1  2007/11/07 19:47:16  bezien
 * Moved clocks to their respective packages
 *
 * Revision 1.1  2007/11/07 19:44:15  bezien
 * Initial Revision
 *
 */
package fr.mvanbesien.calendars.clocks;

import java.util.Calendar;

/**
 * Displays the fuzzy time for the current time of the system.
 * 
 * @author mvanbesien
 * 
 */
public class FuzzyTime {

	/**
	 * Hours to display.
	 */
	private static final String[] HOURS = new String[] { "", "one", "two", "three", "four", "five",
			"six", "seven", "eight", "nine", "ten", "eleven" };

	/**
	 * extrema to display.
	 */
	private static final String[] EXTREMA = new String[] { "midnight", "noon" };

	/**
	 * Minutes to display.
	 */
	private static final String[] MINUTES = new String[] { "o'clock", "five", "ten", "quarter",
			"twenty", "twenty-five", "half" };

	/**
	 * Linkers to display
	 */
	private static final String[] LINKERS = new String[] { "past", "to" };

	/**
	 * Space char
	 */
	private static final String SPACE = " ";

	public static void main(String[] args) {
		System.out.println(getFuzzyTime());
	}

	/**
	 * Returns Fuzzy Time for the current Time System.
	 * 
	 * @return String with fuzzy time
	 */
	public static String getFuzzyTime() {
		return FuzzyTime.getFuzzyTime(Calendar.getInstance());
	}

	/**
	 * Returns Fuzzy Time for the calendar passed as parameter
	 * 
	 * @return String with fuzzy time
	 */
	public static String getFuzzyTime(Calendar c) {
		StringBuffer result = new StringBuffer();
		int time = c.get(Calendar.HOUR_OF_DAY) * 60 * 60 + c.get(Calendar.MINUTE) * 60
				+ c.get(Calendar.SECOND);
		time = (time + 150) % 86400;

		boolean isAM = time / 3600 % 12 == time / 3600 % 24;

		int minutesIndex = time % 3600 / 300;
		int hourIndex = time / 3600 % 12;
		int linkerIndex = 0;

		if (minutesIndex > 6) {
			hourIndex = (hourIndex + 1) % 12;
			linkerIndex = 1;
			minutesIndex = 12 - minutesIndex;
		}

		if (minutesIndex == 0) {
			String hour = "";
			if (hourIndex > 0)
				hour = FuzzyTime.HOURS[hourIndex];
			else
				hour = isAM && linkerIndex == 0 || !isAM && linkerIndex == 1 ? FuzzyTime.EXTREMA[0]
						: FuzzyTime.EXTREMA[1];
			hour = hour.substring(0, 1).toUpperCase() + hour.substring(1);
			result.append(hour);
			if (hourIndex > 0) {
				result.append(FuzzyTime.SPACE);
				result.append(FuzzyTime.MINUTES[minutesIndex]);
			}
			result.append(".");
		} else {
			String minutes = FuzzyTime.MINUTES[minutesIndex];
			minutes = minutes.substring(0, 1).toUpperCase() + minutes.substring(1);
			result.append(minutes);
			result.append(FuzzyTime.SPACE);
			result.append(FuzzyTime.LINKERS[linkerIndex]);
			result.append(FuzzyTime.SPACE);
			String hour = "";
			if (hourIndex > 0)
				hour = FuzzyTime.HOURS[hourIndex];
			else
				hour = isAM && linkerIndex == 0 || !isAM && linkerIndex == 1 ? FuzzyTime.EXTREMA[0]
						: FuzzyTime.EXTREMA[1];
			result.append(hour);
			result.append(".");
		}

		return result.toString();
	}

	/**
	 * Gets the time in milliseconds, when the current time will change.
	 * 
	 * @return Time in milliseconds
	 */
	public long getNextChangeInMillis() {
		Calendar c = Calendar.getInstance();
		long time = (c.get(Calendar.HOUR_OF_DAY) * 60 * 60 + c.get(Calendar.MINUTE) * 60 + c
				.get(Calendar.SECOND)) * 1000 + c.get(Calendar.MILLISECOND);
		time = (time + 150000) % 86400000;
		return 300000 - time % 300000;
	}
}
