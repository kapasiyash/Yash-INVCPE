package com.elitecore.cpe.util.expr.cron;

import java.util.ArrayList;

public class DayOfMonthValueMatcher extends IntArrayValueMatcher{

	private static final int[] lastDays = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	/**
	 * Builds the ValueMatcher.
	 * 
	 * @param integers
	 *            An ArrayList of Integer elements, one for every value accepted
	 *            by the matcher. The match() method will return true only if
	 *            its parameter will be one of this list or the
	 *            last-day-of-month setting applies.
	 */
	
	public DayOfMonthValueMatcher(ArrayList<Integer> integers) {
		super(integers);
	}

	/**
	 * Returns true if the given value is included in the matcher list or the
	 * last-day-of-month setting applies.
	 */
	public boolean match(int value, int month, boolean isLeapYear) {
		return (super.match(value) || (value > 27 && match(32) && isLastDayOfMonth(value, month, isLeapYear)));
	}

	public boolean isLastDayOfMonth(int value, int month, boolean isLeapYear) {
		if (isLeapYear && month == 2) {
			return value == 29;
		} else {
			return value == lastDays[month - 1];
		}
	}

}
