package fr.mvanbesien.calendars.dates;

public class DarianDate extends AbstractDate {

	private static final String[] MONTHS = { "Sagittarius", "Dhanus", "Capricornus", "Makara", "Aquarius", "Kumbha",
			"Pisces", "Mina", "Aries", "Mesha", "Taurus", "Rishabha", "Gemini", "Mithuna", "Cancer", "Karka", "Leo",
			"Simha", "Virgo", "Kanya", "Libra", "Tula", "Scorpius", "Vrishika" };

	@Override
	public String toString() {
		return String.format("%d %s %d", this.day, MONTHS[this.month], this.year);
	}

}
