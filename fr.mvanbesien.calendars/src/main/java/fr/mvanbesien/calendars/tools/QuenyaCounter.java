/**
 * $Id: QuenyaCounter.java,v 1.1 2008/11/05 08:53:38 maxence Exp $
 * 
 * HISTORY 
 * -------
 * $Log: QuenyaCounter.java,v $
 * Revision 1.1  2008/11/05 08:53:38  maxence
 * Saved
 *
 * Revision 1.1  2008/02/12 19:14:57  bezien
 * Initial Revision
 *
 */
package fr.mvanbesien.calendars.tools;

/**
 * Quenya Counter
 * 
 * @author mvanbesien
 * @version $Revision: 1.1 $
 * @since $Date: 2008/11/05 08:53:38 $
 * 
 */
public class QuenyaCounter {

	private static final String[] DIGITS = { "üqua", "minë", "atta", "neldë",
			"canta", "lempë", "enquë", "otso", "tolto", "nertë", "cainen",
			"minquë" };

	private static final String[] MULTIPLES = { "yunquë", "tuxa", "mencë" };

	private static final String SPACE = " ";

	private static final String LINKER = QuenyaCounter.SPACE + "ar"
			+ QuenyaCounter.SPACE;

	private static final int BASE = 12;

	private static final int UPPER_BOUND = 20735;

	private static final int LOWER_BOUND = 0;

	public static String getDuodecimalValue(int value) {

		String values = "0123456789AB";
		String result = "";
		int temp = value;

		while (temp > 0) {
			result = values.charAt(temp % QuenyaCounter.BASE) + result;
			temp = temp / QuenyaCounter.BASE;
		}

		return result;
	}

	public static String getOrdinal(int value) {
		if (value < QuenyaCounter.LOWER_BOUND
				|| value > QuenyaCounter.UPPER_BOUND)
			return String
					.format("[ERROR] %d is Out of bounds...(%d, %d)", value,
							QuenyaCounter.LOWER_BOUND,
							QuenyaCounter.UPPER_BOUND);

		String number = QuenyaCounter.getValue(value);

		if (number.endsWith("minë"))
			number = number.substring(0, number.length() - 1) + "ya";
		else if (number.endsWith("atta"))
			number = number.substring(0, number.length() - 4) + "tatya";
		else if (number.endsWith("neldë"))
			number = number.substring(0, number.length() - 2) + "ya";
		else if (number.endsWith("cainen"))
			number = number.substring(0, number.length() - 6) + "quainëa";
		else if (number.length() > 1)
			number = number.subSequence(0, number.length() - 1) + "ëa";

		return number;
	}

	public static String getValue(int value) {

		if (value < QuenyaCounter.LOWER_BOUND
				|| value > QuenyaCounter.UPPER_BOUND)
			return String
					.format("[ERROR] %d is Out of bounds...(%d, %d)", value,
							QuenyaCounter.LOWER_BOUND,
							QuenyaCounter.UPPER_BOUND);

		if (value == 0)
			return QuenyaCounter.DIGITS[0];

		String result = value % QuenyaCounter.BASE == 0 ? ""
				: QuenyaCounter.DIGITS[value % QuenyaCounter.BASE];
		int temp = value / QuenyaCounter.BASE;
		if (temp > 0 && result.length() > 0)
			result += QuenyaCounter.LINKER;
		int mult = 0;
		while (temp > 0) {
			if (!(temp % QuenyaCounter.BASE == 0 && temp / QuenyaCounter.BASE > 0))
				result += (temp % QuenyaCounter.BASE > 1 ? QuenyaCounter.DIGITS[temp
						% QuenyaCounter.BASE].replace("ë", "e")
						: "")
						+ (mult < QuenyaCounter.MULTIPLES.length ? QuenyaCounter.MULTIPLES[mult]
								: "(" + QuenyaCounter.BASE + "^" + mult + ")")
						+ QuenyaCounter.SPACE;
			temp = temp / QuenyaCounter.BASE;
			mult++;
		}
		return result.trim();
	}
}
