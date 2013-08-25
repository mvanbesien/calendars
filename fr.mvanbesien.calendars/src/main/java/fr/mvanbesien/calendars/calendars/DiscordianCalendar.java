/**
 * $Id: DiscordianCalendar.java,v 1.4 2008/11/05 09:58:35 maxence Exp $
 * 
 * HISTORY 
 * -------
 * $Log: DiscordianCalendar.java,v $
 * Revision 1.4  2008/11/05 09:58:35  maxence
 * Removed useless method
 *
 * Revision 1.3  2008/11/05 09:18:51  maxence
 * Fixed
 *
 * Revision 1.2  2008/11/05 09:18:25  maxence
 * Fixed
 *
 * Revision 1.1  2008/11/05 08:53:42  maxence
 * Saved
 *
 * Revision 1.1  2008/02/04 19:20:46  bezien
 * Initial Revision of Pataphysique and Discordian calendars
 *
 */
package fr.mvanbesien.calendars.calendars;

import java.util.Calendar;

import fr.mvanbesien.calendars.dates.DiscordianDate;
import fr.mvanbesien.calendars.tools.Utils;

/**
 * Discordian Calendar TODO : Additional days
 * 
 * @author mvanbesien
 * @version $Revision: 1.4 $
 * @since $Date: 2008/11/05 09:58:35 $
 * 
 */
public class DiscordianCalendar {

	public static DiscordianDate getDate() {
		return DiscordianCalendar.getDate(Calendar.getInstance());
	}

	public static DiscordianDate getDate(Calendar instance) {

		DiscordianDate date = new DiscordianDate();
		boolean isLeap = Utils.isLeapYear(instance.get(Calendar.YEAR));
		int days = instance.get(Calendar.DAY_OF_YEAR) - 1;
		int year = instance.get(Calendar.YEAR) + 1166;

		if (isLeap)
			if (days == 60) {
				date.setExtraDate(0, year);
				return date;
			} else if (isLeap == days > 60)
				days--;
		date.setOrdinaryDate(days % 5, days % 73 + 1, days / 73, year);
		return date;
	}

}
