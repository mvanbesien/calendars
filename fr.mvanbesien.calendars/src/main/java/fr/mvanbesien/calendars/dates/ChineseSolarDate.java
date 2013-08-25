/**
 * $Id: ChineseSolarDate.java,v 1.2 2009/04/23 09:38:31 maxence Exp $
 * 
 * HISTORY 
 * -------
 * $Log: ChineseSolarDate.java,v $
 * Revision 1.2  2009/04/23 09:38:31  maxence
 * Split Solar and Lunar dates.
 *
 * Revision 1.1  2009/02/27 16:52:03  maxence
 * Initial Revision of Chinese Calendar
 *
 */
package fr.mvanbesien.calendars.dates;

/**
 * Chinese Solar Date Implementation
 * 
 * @author mvanbesien
 * @version $Revision: 1.2 $
 * @since $Date: 2009/04/23 09:38:31 $
 * 
 */
public class ChineseSolarDate extends AbstractDate {

	/**
	 * Chinese Solar Months
	 */
	private static final String[] CN_MONTHS = new String[] { "Lichun", "Yushui", "Jingzhe",
			"Chunfen", "Qingming", "Guyu", "Lixia", "Xiaman", "Mangzhong", "Xiazhi", "Xiaoshu",
			"Dashu", "Liqiu", "Chushu", "Bailu", "Qiufen", "Hanlu", "Shungjiang", "Lidong",
			"Wiaoxue", "Daxue", "Dongzhi", "Xiaohan", "Dahan" };

	/**
	 * English Solar Months
	 */
	private static final String[] EN_MONTHS = new String[] { "the start of Spring", "rain water",
			"the awakening of insects", "the vernal equinox", "clear and bright",
			"the grain rains", "the start of Summer", "the grain full", "the grain in ear",
			"the summer solstice", "minor heat", "major heat", "the start of Autumn",
			"the limit of heat", "the white dew", "the autumnal equinox", "the cold dew",
			"the descent of frost", "the start of Winter", "minor snow", "major snow",
			"the winter solstice", "minor cold", "major cold" };

	/**
	 * Jieqi Letters
	 */
	private static final String[] JIEQILETTERS = new String[] { "S", "P" };

	/**
	 * @return Returns Jieqi Code for Date
	 */
	public String getJieqiCode() {
		return ChineseSolarDate.JIEQILETTERS[month % 2] + Integer.toString(month / 2 + 1);
	}

	/**
	 * 
	 * @return Chinese String value of Date
	 */
	public String toString() {
		return day + 1 + " " + ChineseSolarDate.CN_MONTHS[month] + " ("
				+ ChineseSolarDate.EN_MONTHS[month] + ")";
	}

}
