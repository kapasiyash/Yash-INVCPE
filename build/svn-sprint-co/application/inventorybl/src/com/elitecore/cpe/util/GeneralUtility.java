package com.elitecore.cpe.util;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yash.kapasi
 *
 */
public class GeneralUtility {

	public static final int stringToInt(String value, int defaultValue) {
		try { 
			defaultValue = Integer.parseInt(value);
		}catch(Exception e) {
		}
		return defaultValue;
	}

	public static final long stringToLong(String value, long defaultValue) {
		try { 
			defaultValue = Long.parseLong(value);
		}catch(Exception e) {
		}
		return defaultValue;
	}
	
	public static final double stringToDouble(String value, double defaultValue) {
		try { 
			defaultValue = Double.parseDouble(value);
		}catch(Exception e) {
		}
		return defaultValue;
	}
	
	public static final Date displayDateFromObject(Object object) {
		if(object!=null) {
			return (Date)object;
		} else {
			return null;
		}
	} 
	
	
	public static final String displayValueIfNull(String value) {
		return value == null ? "-" : value;
	}
	public static final String blankDisplayValueIfNull(String value) {
		return value == null ? "" : value;
	}
	
	public static final Double round(double unrounded, int precision, int roundingMode)
	{
	    BigDecimal bd = new BigDecimal(unrounded);
	    BigDecimal rounded = bd.setScale(precision, roundingMode);
	    return rounded.doubleValue();
	}

	public static final boolean isValueNullOrEmpty(String value) 
	{
		return (value == null || value.equals("")) ? true : false;
	}
}
