package com.elitecore.cpe.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author yash.kapasi
 *
 */
public class DateAndTimeFormateUtility 
{
	
	private static SimpleDateFormat dateFormater;
	private static SimpleDateFormat dateTimeFormater;

	public static final synchronized String toDateString(Date date, String dateFormat) {
		setDateFromatFromSystemParameter(dateFormat);
		return dateFormater.format(date);
	}
	
	public static final synchronized String toDateTimeString(Date date, String dateTimeFormat) {
		setDateTimeFromatFromSystemParameter(dateTimeFormat);
		return dateTimeFormater.format(date);
	}	
	
	private static final void setDateFromatFromSystemParameter(String dateFormat) {
		if(dateFormat!=null && !dateFormat.isEmpty()){
			dateFormater = new SimpleDateFormat(dateFormat); 
		}else{
			dateFormater = new SimpleDateFormat("dd-MMM-yyyy"); 
		}
	}
	
	private static final void setDateTimeFromatFromSystemParameter(String dateTimeFormat) {
		if(dateTimeFormat!=null && !dateTimeFormat.isEmpty()){
			dateTimeFormater = new SimpleDateFormat(dateTimeFormat);
		}else{
			dateTimeFormater = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		}
	}
	
	public String getDateTimeFormat(){
		return "dd-MMM-yyyy HH:mm:ss";
	}
	
}
