/**
 * $Id: SolarComputer.java,v 1.2 2008/11/05 10:13:36 maxence Exp $
 * 
 * HISTORY
 * -------
 * $Log: SolarComputer.java,v $
 * Revision 1.2  2008/11/05 10:13:36  maxence
 * Deledated image management to another plugin
 *
 * Revision 1.1  2008/11/05 08:53:47  maxence
 * Saved
 *
 * Revision 1.1  2007/11/19 17:36:49  bezien
 * DaylightComputer renamed to SolarComputer
 *
 * Revision 1.4  2007/10/22 16:56:40  bezien
 * Fixed bug in altitude computation
 *
 * Revision 1.3  2007/09/12 05:41:30  bezien
 * Fixed OrbitalElements in Surrise/Sunset Calculation
 *
 * Revision 1.2  2007/09/05 20:21:22  bezien
 * Added daylight ratio percentage
 *
 * Revision 1.1  2007/08/30 11:12:37  bezien
 * Initial revision
 *
 */
package fr.mvanbesien.calendars.ephemerides;

import java.util.Calendar;

import fr.mvanbesien.calendars.tools.Angles;
import fr.mvanbesien.calendars.tools.OrbitalElements;
import fr.mvanbesien.calendars.tools.Utils;

public class SolarComputer {

	/**
	 * Saved longitude value
	 */
	private double longitude;

	/**
	 * Saved latitude value
	 */
	private double latitude;

	private SolarComputer(double longitude, double latitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/**
	 * Returns instance of DayLightComputer, for the longitude and latitude
	 * passed as parameter
	 * 
	 * @param lg
	 *            : longitude
	 * @param lt
	 *            : latitude
	 * @return instance
	 */
	public static SolarComputer getInstance(double lg, double lt) {
		return new SolarComputer(lg, lt);
	}

	/**
	 * Private Constructor
	 * 
	 */
	private SolarComputer() {
	}

	/**
	 * Returns the daylight left time for the current day
	 * 
	 * @return daylight left time
	 */
	public double getDayLeft() {
		return this.getDayLeft(Calendar.getInstance());
	}

	/**
	 * Returns the daylight left time for the date passed as parameter
	 * 
	 * @param calendar
	 *            : Calendar instance
	 * @return daylight left time
	 */
	public double getDayLeft(Calendar calendar) {
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int min = calendar.get(Calendar.MINUTE);
		double time = new Double(60 * hour + min) / 60;
		Double d = new Double(this.getSunset(calendar) - time);
		if (d < 0 || d > this.getDayTime(calendar))
			return -1;
		else
			return d;

	}

	/**
	 * Returns the ratio of daylight for the day of the current date
	 * 
	 * @return percentage
	 */
	public float getDayLengthPercentage() {
		return this.getDayLengthPercentage(Calendar.getInstance());
	}

	/**
	 * Returns the ratio of daylight for the day of the date passed as parameter
	 * 
	 * @param calendar
	 *            : Calendar instance
	 * @return percentage
	 */
	public float getDayLengthPercentage(Calendar calendar) {
		double dayTime = this.getDayTime(calendar);
		return new Float(new Double(dayTime * 1000 / 24).intValue()) / 10;
	}

	/**
	 * Gets percentage of elapsed daylight, for the current day
	 * 
	 * @return percentage of elapsed daylight
	 */
	public int getDayPct() {
		return this.getDayPct(Calendar.getInstance());
	}

	/**
	 * Gets percentage of elapsed daylight, for the date passed as parameter
	 * 
	 * @param calendar
	 *            : Calendar Instance
	 * @return percentage of elapsed daylight
	 */
	public int getDayPct(Calendar calendar) {
		Double d = new Double(100 - this.getDayLeft(calendar) / this.getDayTime(calendar) * 100);
		if (d < 0 || d > 100)
			return 0;
		else
			return d.intValue();
	}

	/**
	 * Returns the daylight time for the current day
	 * 
	 * @return daylight time
	 */
	public double getDayTime() {
		return this.getDayTime(Calendar.getInstance());
	}

	/**
	 * Returns the daylight time for the date passed as parameter
	 * 
	 * @param calendar
	 *            : Calendar instance
	 * @return daylight time
	 */
	public double getDayTime(Calendar calendar) {
		double sunrise = this.getSunrise(calendar);
		double sunset = this.getSunset(calendar);
		return sunset - sunrise;
	}

	/**
	 * Returns the daylight length difference between two consecutive days,
	 * starting by the current day
	 * 
	 * @return time difference
	 */
	public double getDifferenceWithDayBefore() {
		return getDifferenceWithDayBefore(Calendar.getInstance());
	}

	/**
	 * Returns the daylight length difference between two consecutive days,
	 * starting by the date passed as parameter
	 * 
	 * @return time difference
	 */
	public double getDifferenceWithDayBefore(Calendar c) {
		Calendar cY = Utils.getCalendarCopy(c);
		cY.add(Calendar.DAY_OF_YEAR, -1);
		return getSunset(c) - getSunrise(c) - (getSunset(cY) - getSunrise(cY));
	}

	public double getEveningTwilight() {
		return getEveningTwilight(Calendar.getInstance());
	}

	public double getEveningTwilight(Calendar calendar) {
		return getSunInfo(calendar, false, true);
	}

	/**
	 * Returns the Sun Max altitude for the current date
	 * 
	 * @return Sun altitude in degrees (from -90� to 90�)
	 */
	public double getMaxSunAltitude() {
		return getMaxSunAltitude(Calendar.getInstance());
	}

	/**
	 * Returns the Sun Max altitude for the date passed as parameter
	 * 
	 * @return Sun altitude in degrees (from -90� to 90�)
	 */

	public double getMaxSunAltitude(Calendar calendar) {
		double zenith = getTransit(calendar);
		int h = new Double(zenith).intValue();
		zenith = zenith - h;
		int m = new Double(zenith * 60).intValue();
		zenith = zenith - m / 60.0;
		int s = new Double(zenith * 60 * 60).intValue();
		zenith = zenith - s / (60.0 * 60.0);
		int ms = new Double(zenith * 60 * 60 * 1000).intValue();
		zenith = zenith - ms / (60.0 * 60.0 * 1000.0);
		Calendar c = Utils.getCalendarCopy(calendar);
		c.set(Calendar.HOUR_OF_DAY, h);
		c.set(Calendar.MINUTE, m);
		c.set(Calendar.SECOND, s);
		c.set(Calendar.MILLISECOND, ms);

		return getSunAltitude(c);
	}

	public double getMinSunAltitude() {
		return getMinSunAltitude(Calendar.getInstance());
	}

	public double getMinSunAltitude(Calendar calendar) {
		double zenith = getNadir(calendar);
		int h = new Double(zenith).intValue();
		zenith = zenith - h;
		int m = new Double(zenith * 60).intValue();
		zenith = zenith - m / 60.0;
		int s = new Double(zenith * 60 * 60).intValue();
		zenith = zenith - s / (60.0 * 60.0);
		int ms = new Double(zenith * 60 * 60 * 1000).intValue();
		zenith = zenith - ms / (60.0 * 60.0 * 1000.0);
		Calendar c = Utils.getCalendarCopy(calendar);
		c.set(Calendar.HOUR_OF_DAY, h);
		c.set(Calendar.MINUTE, m);
		c.set(Calendar.SECOND, s);
		c.set(Calendar.MILLISECOND, ms);

		return getSunAltitude(c);

	}

	public double getMorningTwilight() {
		return getMorningTwilight(Calendar.getInstance());
	}

	public double getMorningTwilight(Calendar calendar) {
		return getSunInfo(calendar, true, true);
	}

	public double getNadir() {
		return getNadir(Calendar.getInstance());
	}

	public double getNadir(Calendar calendar) {
		Calendar tempCalendar = Utils.getCalendarCopy(calendar);
		tempCalendar.add(Calendar.DAY_OF_YEAR, -1);
		double sunrise = getSunInfo(calendar, true, false);
		double sunset = getSunInfo(tempCalendar, false, false);
		double value = (sunrise - 24 + sunset) / 2.0;
		if (value >= 0)
			return value;
		tempCalendar.add(Calendar.DAY_OF_YEAR, 2);
		sunrise = getSunInfo(tempCalendar, true, false);
		sunset = getSunInfo(calendar, false, false);
		value = (sunrise + sunset + 24) / 2.0;
		if (value <= 24)
			return value;
		return -1;
	}

	/**
	 * Returns the percentage of the night elapsed, for the next night
	 * 
	 * @return night elapsed percentage
	 */
	public int getNextNightPct() {
		return this.getNextNightPct(Calendar.getInstance());
	}

	/**
	 * Returns the percentage of the night elapsed, for the date passed as
	 * parameter
	 * 
	 * @param calendar
	 *            : Calendar Instance
	 * @return night elapsed percentage
	 */
	public int getNextNightPct(Calendar calendar) {
		Double d = new Double(100 - this.getNightLeft(calendar) / this.getNightTime(calendar) * 100);
		if (d < 0 || d > 100)
			return 0;
		else
			return d.intValue();
	}

	/**
	 * Returns the night left time for the current or next night
	 * 
	 * @return night left time
	 */
	public double getNightLeft() {
		return this.getNightLeft(Calendar.getInstance());
	}

	/**
	 * Returns the night left time for the night of the date passed as parameter
	 * 
	 * @param calendar
	 *            : Calendar instance
	 * @return night left time
	 */
	public double getNightLeft(Calendar calendar) {
		Calendar dayMore = Utils.getCalendarCopy(calendar);
		dayMore.add(Calendar.DATE, 1);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int min = calendar.get(Calendar.MINUTE);
		double time = new Double(60 * hour + min) / 60;
		Double d;
		if (time < this.getSunrise(calendar))
			d = new Double(this.getSunrise(calendar) - time);
		else
			d = new Double(this.getSunrise(dayMore) + 24 - time);
		if (d < 0 || d > this.getNightTime())
			return -1;
		else
			return d;

	}

	/**
	 * Returns night length time for the current day
	 * 
	 * @return night time
	 */
	public double getNightTime() {
		return this.getNightTime(Calendar.getInstance());
	}

	/**
	 * Returns night length time for the date passed as parameter
	 * 
	 * @param calendar
	 *            : Calendar Instance
	 * @return night time
	 */
	public double getNightTime(Calendar calendar) {
		Calendar dayMore = Utils.getCalendarCopy(calendar);
		dayMore.add(Calendar.DATE, 1);
		Calendar dayLess = Utils.getCalendarCopy(calendar);
		dayLess.add(Calendar.DATE, -1);
		double sunrise = calendar.get(Calendar.HOUR_OF_DAY) > this.getSunrise(calendar) ? this.getSunrise(dayMore)
				: this.getSunrise(calendar);
		double sunset = calendar.get(Calendar.HOUR_OF_DAY) > this.getSunrise(calendar) ? this.getSunset(calendar)
				: this.getSunset(dayLess);
		return 24 - sunset + sunrise;
	}

	/**
	 * Returns the Sun altitude for the current date
	 * 
	 * @return Sun altitude in degrees (from -90� to 90�)
	 */
	public double getSunAltitude() {
		return getSunAltitude(Calendar.getInstance());
	}

	/**
	 * Returns the Sun altitude for the date passed as parameter
	 * 
	 * @return Sun altitude in degrees (from -90� to 90�)
	 */
	public double getSunAltitude(Calendar instance) {

		Calendar tempCalendar = Utils.getCalendarCopy(instance);
		tempCalendar.set(Calendar.HOUR_OF_DAY, 12);
		tempCalendar.set(Calendar.MINUTE, 0);
		tempCalendar.set(Calendar.SECOND, 0);
		tempCalendar.set(Calendar.MILLISECOND, 0);
		OrbitalElements oe = OrbitalElements.getOrbitalElements(OrbitalElements.SUN, tempCalendar);

		double gmst0 = oe.getM() + oe.getW() + 180;
		double lst = gmst0 + this.longitude
				+ (instance.get(Calendar.HOUR_OF_DAY) + instance.get(Calendar.MINUTE) / 60f
						+ instance.get(Calendar.SECOND) / 3600f
						- (instance.get(Calendar.DST_OFFSET) + instance.get(Calendar.ZONE_OFFSET)) / 3600000) * 15; // REALLY

		double E = oe.getM()
				+ oe.getE() * 180 / Math.PI * Angles.sin(oe.getM()) * (1.0 - oe.getE() * Angles.cos(oe.getM()));
		double xv = Angles.cos(E) - oe.getE();
		double yv = Math.sqrt(1.0 - oe.getE() * oe.getE()) * Angles.sin(E);

		double v = Angles.atan2(yv, xv);
		double r = Math.sqrt(xv * xv + yv * yv);

		double lonsun = v + oe.getW();

		double xs = r * Angles.cos(lonsun);
		double ys = r * Angles.sin(lonsun);

		double xe = xs;
		double ye = ys * Angles.cos(oe.getEcl());
		double ze = ys * Angles.sin(oe.getEcl());

		double ra = Angles.atan2(ye, xe);
		double dec = Angles.atan2(ze, Math.sqrt(xe * xe + ye * ye));

		double lha = lst - ra;

		double sinh = Angles.sin(this.latitude) * Angles.sin(dec)
				+ Angles.cos(this.latitude) * Angles.cos(dec) * Angles.cos(lha);

		return Angles.asin(sinh);

	}

	/**
	 * Returns the Sun azimuth for the current date
	 * 
	 * @return Sun azimuth in degrees (from 0� to 360�)
	 */
	public double getSunAzimuth() {
		return getSunAzimuth(Calendar.getInstance());
	}

	/**
	 * Returns the Sun azimuth for the date passed as parameter
	 * 
	 * @return Sun azimuth in degrees (from 0� to 360�)
	 */

	public double getSunAzimuth(Calendar instance) {
		OrbitalElements oe = OrbitalElements.getOrbitalElements(OrbitalElements.SUN, instance);

		double gmst0 = oe.getM() + oe.getW() + 180;
		double lst = gmst0 + this.longitude
				+ (instance.get(Calendar.HOUR_OF_DAY) + instance.get(Calendar.MINUTE) / 60f
						+ instance.get(Calendar.SECOND) / 3600f
						- (instance.get(Calendar.DST_OFFSET) + instance.get(Calendar.ZONE_OFFSET)) / 3600000) * 15;

		double E = oe.getM()
				+ oe.getE() * 180 / Math.PI * Angles.sin(oe.getM()) * (1.0 - oe.getE() * Angles.cos(oe.getM()));
		double xv = Angles.cos(E) - oe.getE();
		double yv = Math.sqrt(1.0 - oe.getE() * oe.getE()) * Angles.sin(E);

		double v = Angles.atan2(yv, xv);
		double r = Math.sqrt(xv * xv + yv * yv);

		double lonsun = v + oe.getW();

		double xs = r * Angles.cos(lonsun);
		double ys = r * Angles.sin(lonsun);

		double xe = xs;
		double ye = ys * Angles.cos(oe.getEcl());
		double ze = ys * Angles.sin(oe.getEcl());

		double ra = Angles.atan2(ye, xe);
		double dec = Angles.atan2(ze, Math.sqrt(xe * xe + ye * ye));

		double lha = lst - ra;

		double az = Angles.atan2(-Angles.sin(lha),
				(Angles.cos(this.latitude) * Angles.tan(dec) - Angles.sin(this.latitude) * Angles.cos(lha)));
		return az < 0 ? az + 360 : az;
	}

	private double getSunInfo(Calendar instance, boolean sunrise, boolean twilight) {

		Calendar tempCalendar = Utils.getCalendarCopy(instance);
		tempCalendar.set(Calendar.HOUR_OF_DAY, 12);
		tempCalendar.set(Calendar.MINUTE, 0);
		tempCalendar.set(Calendar.SECOND, 0);
		tempCalendar.set(Calendar.MILLISECOND, 0);
		OrbitalElements oe = OrbitalElements.getOrbitalElements(OrbitalElements.SUN, tempCalendar);
		double gmst0 = oe.getM() + oe.getW() + 180;
		double E = oe.getM()
				+ oe.getE() * 180 / Math.PI * Angles.sin(oe.getM()) * (1.0 - oe.getE() * Angles.cos(oe.getM()));
		double xv = Angles.cos(E) - oe.getE();
		double yv = Math.sqrt(1.0 - oe.getE() * oe.getE()) * Angles.sin(E);

		double v = Angles.atan2(yv, xv);
		double r = Math.sqrt(xv * xv + yv * yv);

		double lonsun = v + oe.getW();

		double xs = r * Angles.cos(lonsun);
		double ys = r * Angles.sin(lonsun);

		double xe = xs;
		double ye = ys * Angles.cos(oe.getEcl());
		double ze = ys * Angles.sin(oe.getEcl());

		double ra = Angles.atan2(ye, xe);
		double dec = Angles.atan2(ze, Math.sqrt(xe * xe + ye * ye));

		double lst = ra;

		double ut = (lst - gmst0 - this.longitude) / 15.0;
		while (ut < 0 || ut > 24)
			if (ut < 0)
				ut += 24;
			else
				ut -= 24;
		double h = -0.833;
		if (twilight)
			h = -6;

		double aaa = (Angles.sin(h) - Angles.sin(this.latitude) * Angles.sin(dec))
				/ (Angles.cos(this.latitude) * Angles.cos(dec));
		double lha = Angles.acos(aaa) / 15.0;

		double gmtt = ut;
		if (sunrise)
			gmtt -= lha;
		else
			gmtt += lha;
		double localt = gmtt
				+ (tempCalendar.get(Calendar.ZONE_OFFSET) + tempCalendar.get(Calendar.DST_OFFSET)) / 3600000.0;
		return localt;
	}

	public double getSunrise() {
		return getSunrise(Calendar.getInstance());
	}

	public double getSunrise(Calendar calendar) {
		return getSunInfo(calendar, true, false);
	}

	public double getSunset() {
		return getSunset(Calendar.getInstance());
	}

	public double getSunset(Calendar calendar) {
		return getSunInfo(calendar, false, false);
	}

	public float getSunTime() {
		return getSunTime(Calendar.getInstance());
	}

	public float getSunTime(Calendar calendar) {
		double az = getSunAzimuth(calendar);
		return new Double(az / 15.0).floatValue();
	}

	public double getTransit() {
		return getTransit(Calendar.getInstance());
	}

	public double getTransit(Calendar calendar) {
		double sunrise = getSunInfo(calendar, true, false);
		double sunset = getSunInfo(calendar, false, false);
		return (sunrise + sunset) / 2.0;
	}

	/**
	 * Returns if is day or night, for the current day
	 * 
	 * @return true if day, false if night
	 */
	public boolean isDay() {
		return this.isDay(Calendar.getInstance());
	}

	/**
	 * Returns if is day or night, for the date passed as parameter
	 * 
	 * @param calendar
	 *            : Calendar Instance
	 * @return true if day, false if night
	 */
	public boolean isDay(Calendar calendar) {
		return this.getDayLeft(calendar) > 0;
	}

}
