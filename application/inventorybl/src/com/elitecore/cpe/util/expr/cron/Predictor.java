package com.elitecore.cpe.util.expr.cron;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.elitecore.cpe.util.expr.cron.exception.InvalidPatternException;

/**
 * <p>
 * A predictor is able to predict when a scheduling pattern will be matched.
 * </p>
 * <p>
 * Suppose you want to know when the scheduler will execute a task scheduled
 * with the pattern <em>0 3 * jan-jun,sep-dec mon-fri</em>. You can predict the
 * next <em>n</em> execution of the task using a Predictor instance:
 * </p>
 * 
 * <pre>
 * String pattern = &quot;0 3 * jan-jun,sep-dec mon-fri&quot;;
 * Predictor p = new Predictor(pattern);
 * for (int i = 0; i &lt; n; i++) {
 * 	System.out.println(p.nextMatchingDate());
 * }
 * </pre>
 * 
 * @author yash.kapasi
 */
public class Predictor {

	/**
	 * The scheduling pattern on which the predictor works.
	 */
	private SchedulingPattern schedulingPattern;

	/**
	 * The start time for the next prediction.
	 */
	private long time;

	/**
	 * The time zone for the prediction.
	 */
//	private TimeZone timeZone = TimeZone.getDefault();

	/**
	 * It builds a predictor with the given scheduling pattern and start time.
	 * 
	 * @param schedulingPattern
	 *            The pattern on which the prediction will be based.
	 * @param start
	 *            The start time of the prediction.
	 * @throws InvalidPatternException
	 *             In the given scheduling pattern isn't valid.
	 */
	public Predictor(String schedulingPattern, long start)
			throws InvalidPatternException {
		this.schedulingPattern = new SchedulingPattern(schedulingPattern);
		this.time = (start / (1000 * 60)) * 1000 * 60;
	}

	/**
	 * It builds a predictor with the given scheduling pattern and start time.
	 * 
	 * @param schedulingPattern
	 *            The pattern on which the prediction will be based.
	 * @param start
	 *            The start time of the prediction.
	 * @throws InvalidPatternException
	 *             In the given scheduling pattern isn't valid.
	 */
	public Predictor(String schedulingPattern, Date start)
			throws InvalidPatternException {
		this(schedulingPattern, start.getTime());
	}

	/**
	 * It builds a predictor with the given scheduling pattern and the current
	 * system time as the prediction start time.
	 * 
	 * @param schedulingPattern
	 *            The pattern on which the prediction will be based.
	 * @throws InvalidPatternException
	 *             In the given scheduling pattern isn't valid.
	 */
	public Predictor(String schedulingPattern) throws InvalidPatternException {
		this(schedulingPattern, System.currentTimeMillis());
	}

	/**
	 * It builds a predictor with the given scheduling pattern and start time.
	 * 
	 * @param schedulingPattern
	 *            The pattern on which the prediction will be based.
	 * @param start
	 *            The start time of the prediction.
	 * @since 2.0
	 */
	public Predictor(SchedulingPattern schedulingPattern, long start) {
		this.schedulingPattern = schedulingPattern;
		this.time = (start / (1000 * 60)) * 1000 * 60;
	}

	/**
	 * It builds a predictor with the given scheduling pattern and start time.
	 * 
	 * @param schedulingPattern
	 *            The pattern on which the prediction will be based.
	 * @param start
	 *            The start time of the prediction.
	 * @since 2.0
	 */
	public Predictor(SchedulingPattern schedulingPattern, Date start) {
		this(schedulingPattern, start.getTime());
	}

	/**
	 * It builds a predictor with the given scheduling pattern and the current
	 * system time as the prediction start time.
	 * 
	 * @param schedulingPattern
	 *            The pattern on which the prediction will be based.
	 * @since 2.0
	 */
	public Predictor(SchedulingPattern schedulingPattern) {
		this(schedulingPattern, System.currentTimeMillis());
	}

	/**
	 * Sets the time zone for predictions.
	 * 
	 * @param timeZone
	 *            The time zone for predictions.
	 * 
	 */
	/*public void setTimeZone(TimeZone timeZone) {
		this.timeZone = timeZone;
	}
*/
	/**
	 * It returns the next matching moment as a millis value.
	 * 
	 * @return The next matching moment as a millis value.
	 */
	public synchronized long nextMatchingTime() {
		// Go a minute ahead.
		time += 60000;
		// Is it matching?
		if (schedulingPattern.match(time)) {
			return time;
		}
		// Go through the matcher groups.
		int size = schedulingPattern.matcherSize;
		long[] times = new long[size];
		for (int k = 0; k < size; k++) {
			// Ok, split the time!
			GregorianCalendar c = new GregorianCalendar();
			c.setTimeInMillis(time);
//			c.setTimeZone(timeZone);
			int minute = c.get(Calendar.MINUTE);
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
			int month = c.get(Calendar.MONTH);
			int year = c.get(Calendar.YEAR);
			// Gets the matchers.
			ValueMatcher minuteMatcher = (ValueMatcher) schedulingPattern.minuteMatchers.get(k);
			ValueMatcher hourMatcher = (ValueMatcher) schedulingPattern.hourMatchers.get(k);
			ValueMatcher dayOfMonthMatcher = (ValueMatcher) schedulingPattern.dayOfMonthMatchers.get(k);
			ValueMatcher dayOfWeekMatcher = (ValueMatcher) schedulingPattern.dayOfWeekMatchers.get(k);
			ValueMatcher monthMatcher = (ValueMatcher) schedulingPattern.monthMatchers.get(k);
			for (;;) { // day of week
				for (;;) { // month
					for (;;) { // day of month
						for (;;) { // hour
							for (;;) { // minutes
								if (minuteMatcher.match(minute)) {
									break;
								} else {
									minute++;
									if (minute > 59) {
										minute = 0;
										hour++;
									}
								}
							}
							if (hour > 23) {
								hour = 0;
								dayOfMonth++;
							}
							if (hourMatcher.match(hour)) {
								break;
							} else {
								hour++;
								minute = 0;
							}
						}
						if (dayOfMonth > 31) {
							dayOfMonth = 1;
							month++;
						}
						if (month > Calendar.DECEMBER) {
							month = Calendar.JANUARY;
							year++;
						}
						if (dayOfMonthMatcher instanceof DayOfMonthValueMatcher) {
							DayOfMonthValueMatcher aux = (DayOfMonthValueMatcher) dayOfMonthMatcher;
							if (aux.match(dayOfMonth, month + 1, c.isLeapYear(year))) {
								break;
							} else {
								dayOfMonth++;
								hour = 0;
								minute = 0;
							}
						} else if (dayOfMonthMatcher.match(dayOfMonth)) {
							break;
						} else {
							dayOfMonth++;
							hour = 0;
							minute = 0;
						}
					}
					if (monthMatcher.match(month + 1)) {
						break;
					} else {
						month++;
						dayOfMonth = 1;
						hour = 0;
						minute = 0;
					}
				}
				// Is this ok?
				c = new GregorianCalendar();
//				c.setTimeZone(timeZone);
				c.set(Calendar.MINUTE, minute);
				c.set(Calendar.HOUR_OF_DAY, hour);
				c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				c.set(Calendar.MONTH, month);
				c.set(Calendar.YEAR, year);
				// Day-of-month/month/year compatibility check.
				int oldDayOfMonth = dayOfMonth;
				int oldMonth = month;
				int oldYear = year;
				dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
				month = c.get(Calendar.MONTH);
				year = c.get(Calendar.YEAR);
				if (month != oldMonth || dayOfMonth != oldDayOfMonth
						|| year != oldYear) {
					// Take another spin!
					continue;
				}
				// Day of week.
				int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
				if (dayOfWeekMatcher.match(dayOfWeek - 1)) {
					break;
				} else {
					dayOfMonth++;
					hour = 0;
					minute = 0;
					if (dayOfMonth > 31) {
						dayOfMonth = 1;
						month++;
						if (month > Calendar.DECEMBER) {
							month = Calendar.JANUARY;
							year++;
						}
					}
				}
			}
			// Seems it matches!
			times[k] = (c.getTimeInMillis() / (1000 * 60)) * 1000 * 60;
		}
		// Which one?
		long min = Long.MAX_VALUE;
		for (int k = 0; k < size; k++) {
			if (times[k] < min) {
				min = times[k];
			}
		}
		// Updates the object current time value.
		time = min;
		// Here it is.
		return time;
	}

	/**
	 * It returns the next matching moment as a {@link Date} object.
	 * 
	 * @return The next matching moment as a {@link Date} object.
	 */
	public synchronized Date nextMatchingDate() {
		return new Date(nextMatchingTime());
	}
}
