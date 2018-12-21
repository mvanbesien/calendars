package fr.mvanbesien.calendars.dates;

import fr.mvanbesien.calendars.tools.Utils;

public class BorealSeasonDate extends AbstractDate {

	private static final String[] MONTHS = { "the rising Winter", "high Winter", "the setting Winter",
			"the rising Spring", "high Spring", "the setting Spring", "the rising Summer", "high Summer",
			"the setting Summer", "the rising Autumn", "high Autumn", "the setting Autumn" };

	@Override
	public String toString() {
		return String.format("%s day of %s of %d", Utils.ordinal(this.day), MONTHS[this.month], this.year);
	}

}
