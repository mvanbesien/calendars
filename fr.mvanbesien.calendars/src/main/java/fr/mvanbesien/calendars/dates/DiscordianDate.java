package fr.mvanbesien.calendars.dates;

public class DiscordianDate extends AbstractDate {

	/**
	 * Days in Discordian calendar
	 */
	public static final String[] DC_WEEKDAYS = new String[] { "Sweetmorn",
			"Boomtime", "Pungenday", "Prickle-Prickle", "Setting Orange" };

	/**
	 * Days in Discordian calendar
	 */
	public static final String[] DC_MONTHS = new String[] { "Chaos", "Discord",
			"Confusion", "Bureaucracy", "The Aftermath" };

	/**
	 * Extra Days in Discordian calendar
	 */
	public static final String[] DC_EXTRA = new String[] { "St Tib's Day" };

	@Override
	public String toString() {
		if (isExtraDay)
			return String.format("%s, %s YOLD",
					DiscordianDate.DC_EXTRA[extraDayIndex], year);
		return String.format("%s, %s %s, %s YOLD",
				DiscordianDate.DC_WEEKDAYS[weekDay],
				DiscordianDate.DC_MONTHS[month], day, year);
	}

}
