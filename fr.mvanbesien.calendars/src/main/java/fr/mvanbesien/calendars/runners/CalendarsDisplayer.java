/**
 * $Id: CalendarsDisplayer.java,v 1.5 2009/02/27 16:52:02 maxence Exp $
 * 
 * HISTORY 
 * -------
 * $Log: CalendarsDisplayer.java,v $
 * Revision 1.5  2009/02/27 16:52:02  maxence
 * Initial Revision of Chinese Calendar
 *
 * Revision 1.4  2009/02/17 09:25:53  maxence
 * MVA : Added Dominical Letter
 *
 * Revision 1.3  2009/02/09 13:10:18  maxence
 * Epaque changed to Epacte
 *
 * Revision 1.2  2008/11/05 10:04:35  maxence
 * Moved ***Day to days
 *
 * Revision 1.1  2008/11/05 08:53:48  maxence
 * Saved
 *
 * Revision 1.1  2008/02/13 19:28:46  bezien
 * Initial Revision
 *
 */
package fr.mvanbesien.calendars.runners;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import fr.mvanbesien.calendars.calendars.AztecCalendar;
import fr.mvanbesien.calendars.calendars.ChineseCalendar;
import fr.mvanbesien.calendars.calendars.ClassicCalendar;
import fr.mvanbesien.calendars.calendars.ColignyCalendar;
import fr.mvanbesien.calendars.calendars.DiscordianCalendar;
import fr.mvanbesien.calendars.calendars.GoddessLunarCalendar;
import fr.mvanbesien.calendars.calendars.ImladrisCalendar;
import fr.mvanbesien.calendars.calendars.LiberaliaTridayCalendar;
import fr.mvanbesien.calendars.calendars.MayanCalendar;
import fr.mvanbesien.calendars.calendars.MuslimCalendar;
import fr.mvanbesien.calendars.calendars.NumenorCalendar;
import fr.mvanbesien.calendars.calendars.PataphysiqueCalendar;
import fr.mvanbesien.calendars.calendars.RepublicanCalendar;
import fr.mvanbesien.calendars.calendars.RomanCalendar;
import fr.mvanbesien.calendars.calendars.SeasonCalendar;
import fr.mvanbesien.calendars.calendars.SexagesimalCalendar;
import fr.mvanbesien.calendars.calendars.SolCalendar;
import fr.mvanbesien.calendars.calendars.Symmetry454Calendar;
import fr.mvanbesien.calendars.calendars.UniversalCalendar;
import fr.mvanbesien.calendars.dates.ChineseSolarDate;
import fr.mvanbesien.calendars.dates.LiberaliaTridayDate;
import fr.mvanbesien.calendars.days.PataphysiqueCalendarDay;
import fr.mvanbesien.calendars.days.RepublicanCalendarDay;
import fr.mvanbesien.calendars.ephemerides.ChineseSigns;
import fr.mvanbesien.calendars.ephemerides.EcclesiasticComput;
import fr.mvanbesien.calendars.ephemerides.ZodiacSign;

public class CalendarsDisplayer {

	public static void displayForDate(Calendar c) throws UnsupportedEncodingException {
		System.out.println("Current Date             : " + ClassicCalendar.getDate(c) + " "
				+ ClassicCalendar.getDayPositionInYear(c));
		System.out.println("Quarter Date             : " + ClassicCalendar.getQDate(c));
		System.out.println("ISO8601 Date             : " + ClassicCalendar.getWeekDate(c));
		System.out.println("Season Date              : " + SeasonCalendar.getSeasonDate(c) + ", "
				+ SeasonCalendar.getNextSeasonDate(c));
		System.out.println("Julian Day               : "
				+ new Double(ClassicCalendar.getJulianDay(c)).intValue());
		System.out.println("-----------------------------------------------");
		System.out.println("European Zodiac Sign     : " + ZodiacSign.getZodiacSign(c));
		System.out.println("Chinese Zodiac Sign      : " + ChineseSigns.getSign(c));
		System.out.println("Ecclesiastic Comput      : Epacte = " + EcclesiasticComput.getEpacte(c)
				+ ", Indiction = " + EcclesiasticComput.getIndiction(c) + ", Golden Number = "
				+ EcclesiasticComput.getGoldNumber(c) + ", Dominical Letter = "
				+ EcclesiasticComput.getDominicalLetter(c));
		System.out.println("-----------------------------------------------");
		System.out.println("Coligny Calendar         : " + ColignyCalendar.getDate(c));
		System.out.println("Roman Calendar           : " + RomanCalendar.getDateAsString(c));
		System.out.println("Republican Calendar      : " + RepublicanCalendar.getDate(c) + " ("
				+ RepublicanCalendarDay.getValue(c) + ")");
		System.out.println("");
		ChineseSolarDate chineseSolarDate = ChineseCalendar.getSolarDate(c);
		System.out.println("Muslim Calendar          : " + MuslimCalendar.getDate(c));
		System.out.println("Chinese Calendar         : " + chineseSolarDate.toString());
		System.out.println("Aztec Calendar           : " + AztecCalendar.getDate(c));
		System.out.println("Mayan Calendar           : " + MayanCalendar.getDate(c));
		System.out.println("");
		System.out.println("Universal Calendar       : " + UniversalCalendar.getDate(c));
		System.out.println("Sol Calendar             : " + SolCalendar.getDate(c));
		System.out.println("Symmetry454 Calendar     : " + Symmetry454Calendar.getDate(c));
		System.out.println("");
		System.out.println("Imladris Calendar        : " + ImladrisCalendar.getDate(c));
		System.out.println("Numenor Calendar         : " + NumenorCalendar.getDate(c));
		System.out.println("");
		System.out.println("Goddess Lunar Calendar   : " + GoddessLunarCalendar.getDate(c));
		LiberaliaTridayDate liberaliaTridayDate = LiberaliaTridayCalendar.getDate(c);
		System.out.println("Liberalia Triday Calendar: " + liberaliaTridayDate.displayLong() + " ("
				+ liberaliaTridayDate.displayShort() + ")");
		System.out.println("Sexagesimal Calendar     : " + SexagesimalCalendar.getDate(c));
		System.out.println("");
		System.out.println("Pataphysique Calendar    : " + PataphysiqueCalendar.getDate(c) + " ("
				+ PataphysiqueCalendarDay.getValue(c) + ")");
		System.out.println("Discordian Calendar      : " + DiscordianCalendar.getDate(c));

		System.out.println("-----------------------------------------------");
	}

	public static void main(String[] args) {

		Calendar c = Calendar.getInstance();
		if (args.length > 0) {
			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date d = null;
			try {
				d = f.parse(args[0]);
			} catch (ParseException nfe) {
			}
			if (d != null)
				c.setTimeInMillis(d.getTime());
		}
		try {
			CalendarsDisplayer.displayForDate(c);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}
