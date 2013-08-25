/**
 * $Id: AbstractDate.java,v 1.2 2008/11/05 09:19:38 maxence Exp $
 * 
 * HISTORY
 * -------
 * $Log: AbstractDate.java,v $
 * Revision 1.2  2008/11/05 09:19:38  maxence
 * Fixed
 *
 * Revision 1.1  2008/11/05 09:14:55  maxence
 * Fixed
 *
 * Revision 1.1  2008/11/05 08:53:39  maxence
 * Saved
 *
 * Revision 1.3  2008/02/04 19:20:46  bezien
 * Initial Revision of Pataphysique and Discordian calendars
 *
 * Revision 1.2  2007/09/17 16:45:04  bezien
 * Added Imladris and Numenor calendars, Removed Quenya calendar
 *
 * Revision 1.1  2007/08/30 11:12:35  bezien
 * Initial revision
 *
 */
package fr.mvanbesien.calendars.dates;

/**
 * This Class is an class containing information about a date converted from
 * Gregorian calendar
 * 
 * @author mvanbesien
 * 
 */
public abstract class AbstractDate {

	/**
	 * weekDay index
	 */
	protected Integer weekDay;

	/**
	 * day index
	 */
	protected Integer day;

	/**
	 * month index
	 */
	protected Integer month;

	/**
	 * year index
	 */
	protected Integer year;

	/**
	 * era index
	 */
	protected Integer era;

	/**
	 * extra day index
	 */
	protected Integer extraDayIndex;

	/**
	 * boolean, if this is describing an extra day
	 */
	protected boolean isExtraDay;

	/**
	 * Default constructor
	 * 
	 */
	public AbstractDate() {
	}

	/**
	 * @return day
	 */
	public Integer getDay() {
		return day;
	}

	/**
	 * 
	 * @return era
	 */
	public Integer getEra() {
		return era;
	}

	/**
	 * 
	 * @return extra day index
	 */
	public Integer getExtraDayIndex() {
		return extraDayIndex;
	}

	/**
	 * 
	 * @return month
	 */
	public Integer getMonth() {
		return month;
	}

	/**
	 * 
	 * @return weekday
	 */
	public Integer getWeekDay() {
		return weekDay;
	}

	/**
	 * 
	 * @return year
	 */
	public Integer getYear() {
		return year;
	}

	/**
	 * @return true if this is an extraday, false otherwise
	 */
	public boolean isExtraDay() {
		return isExtraDay;
	}

	/**
	 * Sets an extra day
	 * 
	 * @param calendarType
	 * @param extraDayIndex
	 * @param year
	 */
	public void setExtraDate(int extraDayIndex, int year) {

		this.extraDayIndex = new Integer(extraDayIndex);
		this.year = new Integer(year);
		isExtraDay = true;
	};

	/**
	 * Sets an extra day
	 * 
	 * @param calendarType
	 * @param extraDayIndex
	 * @param year
	 * @param era
	 */
	public void setExtraDate(int extraDayIndex, int year, int era) {

		this.extraDayIndex = new Integer(extraDayIndex);
		this.year = new Integer(year);
		this.era = new Integer(era);
		isExtraDay = true;
	}

	/**
	 * Sets an Ordinary Date (not an extraday)
	 * 
	 * @param calendarType
	 * @param weekDay
	 * @param day
	 * @param month
	 * @param year
	 */
	public void setOrdinaryDate(int weekDay, int day, int month, int year) {
		this.weekDay = new Integer(weekDay);
		this.day = new Integer(day);
		this.month = new Integer(month);
		this.year = new Integer(year);
		isExtraDay = false;

	}

	/**
	 * Sets an Ordinary Date (not an extraday)
	 * 
	 * @param calendarType
	 * @param weekDay
	 * @param day
	 * @param month
	 * @param year
	 * @param era
	 */
	public void setOrdinaryDate(int weekDay, int day, int month, int year,
			int era) {

		this.weekDay = new Integer(weekDay);
		this.day = new Integer(day);
		this.month = new Integer(month);
		this.year = new Integer(year);
		this.era = new Integer(era);
		isExtraDay = false;

	}

	@Override
	public abstract String toString();

}
