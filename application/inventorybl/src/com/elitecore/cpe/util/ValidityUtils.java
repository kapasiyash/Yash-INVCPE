package com.elitecore.cpe.util;

import java.util.Calendar;
import java.util.Date;

public class ValidityUtils {

	/**
	 * <p>This method checks for validity between start date and end date for given compare date. This method also provides an advantage for 
	 * finding validity either advance day or past day.</p>
	 * @param startdate Start date for comparison
	 * @param enddate End date for comparison
	 * @param comparedate Date to check for validity.
	 * @param actpasday Advance / Past days for example  1 for day minus one data. 
	 * */
	public static boolean isValid(Date startDate, Date endDate, Date compareDate , int actpasday){
		
		
		boolean retval = false;
		Calendar compareCal = Calendar.getInstance();
		Calendar startCal = Calendar.getInstance();
		Calendar endCal = Calendar.getInstance();
		compareCal.setTime(compareDate);
		startCal.setTime(startDate);
		endCal.setTime(endDate);
		if(actpasday!=0){
			endCal.add(Calendar.DATE, actpasday);
		}
		
		if(compareCal.after(startCal) && compareCal.before(endCal)){
			retval = true; // compareCal between start and end date
		}else if(compareCal.after(endCal)){
			retval = false; //compareCal is after end date
		}else if(compareCal.before(startCal)){
			retval = false; //compareCal is before start date
		}else if(compareCal.equals(startCal) || compareCal.equals(endCal)){
			retval= true; //compareCal is equals start and end date
		} 
//		System.out.println( "\nStart Date   " + startCal.getTime()  + " \nEnd Date     " + endCal.getTime() + " \nCompare Date " + compareCal.getTime()+"\nvalid->"+retval);
		return retval; 
	}
	public static boolean isValid(Date startDate, Date endDate, Date compareDate ){
		return isValid(startDate, endDate, compareDate,0);
	}
	public static void main(String[] args) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		Calendar cal3 = Calendar.getInstance();
		cal2.add(Calendar.DATE, 2);
		cal3.add(Calendar.DATE, 0);
		System.out.println(isValid(cal1.getTime(), cal2.getTime(), cal3.getTime(),1));
		
	}
}
