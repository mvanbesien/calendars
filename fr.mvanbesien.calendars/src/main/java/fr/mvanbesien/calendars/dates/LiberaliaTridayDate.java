package fr.mvanbesien.calendars.dates;

public class LiberaliaTridayDate {

	private static final String[] DAYS = new String[] { "Sophiesday",
			"Zoesday", "Norasday" };

	private static final String[] SOLAR_MONTHS = new String[] { "Kamaliel",
			"Gabriel", "Samlo", "Abrasax" };

	private static final String[] LUNAR_MONTHS = new String[] { "Armedon",
			"Nousanios", "Harmozel", "Phaionios", "Ainios", "Oraiel",
			"Mellephaneus", "Loios", "Davithe", "Mousanios", "Amethes",
			"Eleleth" };

	private int day;

	private int solarTriday;

	private int solarMonth;

	private int solarYear;

	private int lunarTriday;

	private int lunarMonth;

	private int lunarYear;

	private int lunarCycle;

	public LiberaliaTridayDate() {
	}

	public String displayLong() {
		return String.format("%s, %d %s %d, %d %s %d",
				LiberaliaTridayDate.DAYS[day - 1], lunarTriday,
				LiberaliaTridayDate.LUNAR_MONTHS[lunarMonth - 1], lunarYear,
				solarTriday, LiberaliaTridayDate.SOLAR_MONTHS[solarMonth - 1],
				solarYear);
	}

	public String displayShort() {
		return String.format("%d-%d-%d-%02d-%d LLT, %d-%d-%02d-%d SLT",
				lunarCycle, lunarYear, lunarMonth, lunarTriday, day, solarYear,
				solarMonth, solarTriday, day);
	}

	public void setDay(int day) {
		this.day = day;
	}

	public void setLunarDate(int lunarTriday, int lunarMonth, int lunarYear,
			int lunarCycle) {
		this.lunarCycle = lunarCycle;
		this.lunarMonth = lunarMonth;
		this.lunarTriday = lunarTriday;
		this.lunarYear = lunarYear;
	}

	public void setSolarDate(int solarTriday, int solarMonth, int solarYear) {
		this.solarMonth = solarMonth;
		this.solarTriday = solarTriday;
		this.solarYear = solarYear;
	}

	@Override
	public String toString() {
		return displayLong();
	}
}
