package fr.mvanbesien.calendars.runners;

import java.util.Calendar;

import fr.mvanbesien.calendars.calendars.SeasonCalendar;

public class SeasonsDisplayer {
	public static void main(String[] args) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.YEAR, -1);
		for (int i = 0; i < 51; i++) {
			System.out.println("YEAR   : "+c.get(Calendar.YEAR));
			System.out.println("SPRING : "+SeasonCalendar.getVernalEquinox(c).getTime());
			System.out.println("SUMMER : "+SeasonCalendar.getSummerSolstice(c).getTime());
			System.out.println("AUTUMN : "+SeasonCalendar.getAutumnEquinox(c).getTime());
			System.out.println("WINTER : "+SeasonCalendar.getWinterSolstice(c).getTime());
			System.out.println("");
			c.add(Calendar.YEAR, 1);
		}
	}
}
