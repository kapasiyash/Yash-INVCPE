package com.elitecore.cpe.util.expr.cron;

/**
 * @author yash.kapasi
 *
 */
public class AlwaysTrueValueMatcher implements ValueMatcher {

	/**
	 * Always return true
	 * */
	@Override
	public boolean match(int value) {

		return true;
	}

}
