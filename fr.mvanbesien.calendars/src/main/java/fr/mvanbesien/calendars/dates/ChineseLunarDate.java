/**
 * $Id: ChineseLunarDate.java,v 1.2 2009/04/27 08:20:39 maxence Exp $
 * 
 * HISTORY 
 * -------
 * $Log: ChineseLunarDate.java,v $
 * Revision 1.2  2009/04/27 08:20:39  maxence
 * Added months
 *
 * Revision 1.1  2009/04/23 09:38:31  maxence
 * Split Solar and Lunar dates.
 *
 */
package fr.mvanbesien.calendars.dates;

public class ChineseLunarDate extends AbstractDate {
	/**
	 * Chinese Trunks
	 */
	private static final String[] CN_TRUNKS = new String[] { "Jia", "Yi",
			"Bing", "Ding", "Wu", "Ji", "Geng", "Xin", "Ren", "Gui" };

	/**
	 * Chinese Elements
	 */
	private static final String[] CN_ELEMENTS = new String[] { "Zi", "Chou",
			"Yin", "Mao", "Chen", "Si", "Wu", "Wei", "Shen", "You", "Xu", "Hai" };

	/**
	 * English Trunks
	 */
	private static final String[] EN_TRUNKS = new String[] { "Wood", "Fire",
			"Earth", "Metal", "Water" };

	/**
	 * English Elements
	 */
	private static final String[] EN_ELEMENTS = new String[] { "Rat", "Ox",
			"Tiger", "Hare", "Dragon", "Snake", "Horse", "Sheep", "Monkey",
			"Rooster", "Dog", "Pig" };

	private static final String[] CN_MONTHS = new String[] { "zhengyu�",
			"�ryu�", "sanyu�", "s�yu�", "wuyu�", "li�yu�", "qiyu�", "bayu�",
			"jiuyu�", "sh�yu�", "sh�yiyu�", "sh�'�ryu�" };

	private static final String[] EN_MONTHS = new String[] { "Primens",
			"Apricomens", "Peacimens", "Plumens", "Guavamens", "Lotumens",
			"Orchimens", "Osmanthumens", "Chrysanthemens", "Benimens",
			"Hiemens", "Lamens" };

	/**
	 * 
	 * @return Trunk/Element combination in Chinese for Date
	 */
	public String getCNYearElements() {
		return ChineseLunarDate.CN_TRUNKS[(year + 8) % 10]
				+ ChineseLunarDate.CN_ELEMENTS[(year + era + 10) % 12]
						.toLowerCase();
	}

	/**
	 * 
	 * @return Trunk/Element combination in English for Date
	 */
	public String getENYearElements() {
		return ChineseLunarDate.EN_TRUNKS[(year + 8) % 10 / 2] + " "
				+ ChineseLunarDate.EN_ELEMENTS[(year + 10) % 12];
	}

	public String toENString() {
		return day + " " + ChineseLunarDate.EN_MONTHS[month - 1]
				+ ", year of the " + getENYearElements();
	}

	@Override
	public String toString() {
		return day + " " + ChineseLunarDate.CN_MONTHS[month - 1] + ", Ganzhi "
				+ getCNYearElements();
	}

}
