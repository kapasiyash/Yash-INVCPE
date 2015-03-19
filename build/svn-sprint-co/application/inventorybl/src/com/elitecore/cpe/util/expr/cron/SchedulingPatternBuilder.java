package com.elitecore.cpe.util.expr.cron;

import com.elitecore.cpe.util.expr.cron.exception.InvalidPatternException;

/**
 * <p>
 * 
 * 	SchedulingPatternBuilder wil
 * 
 * 
 * <p>
 * 
 * 
 * @see SchedulingPattern
 * @author yash.kapasi
 * 
 * */
public class SchedulingPatternBuilder {
	
	private SchedulingPattern schedulingPattern;
		
	
	  protected SchedulingPatternBuilder(SchedulingPattern schedulingPattern) {
	        if (schedulingPattern == null) {
	            throw new NullPointerException("schedulingPattern cannot be null");
	        }
	        this.schedulingPattern = schedulingPattern;
	    }
	
	 /**
     * Create a SchedulingPatternBuilder with the given cron-expression string -
     * which is presumed to b e valid cron expression (and hence only a
     * RuntimeException will be thrown if it is not).
     * 
     * @param SchedulingPattern
     *            the cron expression string to base the schedule on.
     * @return the new SchedulingPattern
     * @throws RuntimeException
     *             wrapping a InvalidPatternException if the expression is invalid
     * @see SchedulingPatternBuilder
     */
	   public static SchedulingPatternBuilder cronSchedule(String SchedulingPattern) {
	        try {
	            return cronSchedule(new SchedulingPattern(SchedulingPattern));
	        } catch (InvalidPatternException e) {
	            // all methods of construction ensure the expression is valid by
	            // this point...
	            throw new RuntimeException("SchedulingPattern '" + SchedulingPattern
	                    + "' is invalid.", e);
	        }
	    }

	    /**
	     * Create a SchedulingPatternBuilder with the given cron-expression string -
	     * which may not be a valid cron expression (and hence a InvalidPatternException will
	     * be thrown if it is not).
	     * 
	     * @param SchedulingPattern
	     *            the cron expression string to base the schedule on.
	     * @return the new SchedulingPatternBuilder
	     * @throws InvalidPatternException
	     *             if the expression is invalid
	     * @see SchedulingPattern
	     */
	    public static SchedulingPatternBuilder cronScheduleNonvalidatedExpression(
	            String SchedulingPattern) throws InvalidPatternException {
	        return cronSchedule(new SchedulingPattern(SchedulingPattern));
	    }

	    private static SchedulingPatternBuilder cronScheduleNoInvalidPatternException(
	            String presumedValidSchedulingPattern) {
	        try {
	            return cronSchedule(new SchedulingPattern(presumedValidSchedulingPattern));
	        } catch (InvalidPatternException e) {
	            // all methods of construction ensure the expression is valid by
	            // this point...
	            throw new RuntimeException(
	                    "SchedulingPattern '"
	                            + presumedValidSchedulingPattern
	                            + "' is invalid, which should not be possible.",
	                    e);
	        }
	    }

	    /**
	     * Create a SchedulingPatternBuilder with the given cron-expression.
	     * 
	     * @param SchedulingPattern
	     *            the cron expression to base the schedule on.
	     * @return the new SchedulingPatternBuilder
	     * @see SchedulingPattern
	     */
	    public static SchedulingPatternBuilder cronSchedule(SchedulingPattern SchedulingPattern) {
	        return new SchedulingPatternBuilder(SchedulingPattern);
	    }

	    /**
	     * Create a SchedulingPatternBuilder with a cron-expression that sets the
	     * schedule to fire every day at the given time (hour and minute).
	     * 
	     * @param hour
	     *            the hour of day to fire
	     * @param minute
	     *            the minute of the given hour to fire
	     * @return the new SchedulingPatternBuilder
	     * @see SchedulingPattern
	     */
	    public static SchedulingPatternBuilder dailyAtHourAndMinute(int hour, int minute) {
	        DateBuilder.validateHour(hour);
	        DateBuilder.validateMinute(minute);

	        String SchedulingPattern = String.format("%d %d * * *", minute, hour);

	        return cronScheduleNoInvalidPatternException(SchedulingPattern);
	    }

	    /**
	     * Create a SchedulingPatternBuilder with a cron-expression that sets the
	     * schedule to fire at the given day at the given time (hour and minute) on
	     * the given days of the week.
	     * 
	     * @param daysOfWeek
	     *            the dasy of the week to fire
	     * @param hour
	     *            the hour of day to fire
	     * @param minute
	     *            the minute of the given hour to fire
	     * @return the new SchedulingPatternBuilder
	     * @see SchedulingPattern
	     * @see DateBuilder#MONDAY
	     * @see DateBuilder#TUESDAY
	     * @see DateBuilder#WEDNESDAY
	     * @see DateBuilder#THURSDAY
	     * @see DateBuilder#FRIDAY
	     * @see DateBuilder#SATURDAY
	     * @see DateBuilder#SUNDAY
	     */

	    public static SchedulingPatternBuilder atHourAndMinuteOnGivenDaysOfWeek(
	            int hour, int minute, Integer... daysOfWeek) {
	        if (daysOfWeek == null || daysOfWeek.length == 0)
	            throw new IllegalArgumentException(
	                    "You must specify at least one day of week.");
	        for (int dayOfWeek : daysOfWeek)
	            DateBuilder.validateDayOfWeek(dayOfWeek);
	        DateBuilder.validateHour(hour);
	        DateBuilder.validateMinute(minute);

	        String schedulingPattern = String.format("%d %d * * %d", minute, hour,
	                daysOfWeek[0]);
	        StringBuilder builder = new StringBuilder();
	        builder.append(schedulingPattern);
	        for (int i = 1; i < daysOfWeek.length; i++){
	        	builder.append(",");
	        	builder.append(daysOfWeek[i]);
	        }

	        return cronScheduleNoInvalidPatternException(builder.toString());
	    }

	    /**
	     * Create a SchedulingPatternBuilder with a cron-expression that sets the
	     * schedule to fire one per week on the given day at the given time (hour
	     * and minute).
	     * 
	     * @param dayOfWeek
	     *            the day of the week to fire
	     * @param hour
	     *            the hour of day to fire
	     * @param minute
	     *            the minute of the given hour to fire
	     * @return the new SchedulingPatternBuilder
	     * @see SchedulingPattern
	     * @see DateBuilder#MONDAY
	     * @see DateBuilder#TUESDAY
	     * @see DateBuilder#WEDNESDAY
	     * @see DateBuilder#THURSDAY
	     * @see DateBuilder#FRIDAY
	     * @see DateBuilder#SATURDAY
	     * @see DateBuilder#SUNDAY
	     */
	    public static SchedulingPatternBuilder weeklyOnDayAndHourAndMinute(
	            int dayOfWeek, int hour, int minute) {
	        DateBuilder.validateDayOfWeek(dayOfWeek);
	        DateBuilder.validateHour(hour);
	        DateBuilder.validateMinute(minute);

	        String SchedulingPattern = String.format(" %d %d * * %d ", minute, hour,
	                dayOfWeek);

	        return cronScheduleNoInvalidPatternException(SchedulingPattern);
	    }

	    /**
	     * Create a SchedulingPatternBuilder with a cron-expression that sets the
	     * schedule to fire one per month on the given day of month at the given
	     * time (hour and minute).
	     * 
	     * @param dayOfMonth
	     *            the day of the month to fire
	     * @param hour
	     *            the hour of day to fire
	     * @param minute
	     *            the minute of the given hour to fire
	     * @return the new SchedulingPatternBuilder
	     * @see SchedulingPattern
	     */
	    public static SchedulingPatternBuilder monthlyOnDayAndHourAndMinute(
	            int dayOfMonth, int hour, int minute) {
	        DateBuilder.validateDayOfMonth(dayOfMonth);
	        DateBuilder.validateHour(hour);
	        DateBuilder.validateMinute(minute);

	        String SchedulingPattern = String.format("%d %d %d * *", minute, hour,
	                dayOfMonth);

	        return cronScheduleNoInvalidPatternException(SchedulingPattern);
	    }
	    
	    public SchedulingPattern getSchedulingPattern(){
	    	return this.schedulingPattern;
	    }
	    
	    
	    /**
	     * Create a SchedulingPatternBuilder with a cron-expression that sets the
	     * schedule to fire on every minute.
	     * @return the new SchedulingPatternBuilder
	     * @see SchedulingPattern
	     * */
	    public static  SchedulingPatternBuilder minute(){
	    	String schedulingPattern = " * * * * * ";
	    	return cronScheduleNoInvalidPatternException(schedulingPattern);
	    }
	    
	    /**
	     * Create a SchedulingPatternBuilder with a cron-expression that sets the
	     * schedule to fire on last date of month on at given time (hour and minute)
	     * 
	     * 
	     * 
	     * @param hour
	     *            the hour of day to fire
	     * @param minute
	     *            the minute of the given hour to fire
	     *  @return the new SchedulingPatternBuilder
	     * @see SchedulingPattern
	     * */
	    public static SchedulingPatternBuilder lastDateOfMonthDayAndHour(int hour,int minute){
	    	String schedulingPattern = String.format("%d %d L * *", minute,hour);
	    	return cronScheduleNoInvalidPatternException(schedulingPattern);
	    }
	    
	    
	    
	    
	    
	    

		
		/*public static void main(String[] args) throws InvalidPatternException {
			Date executionStartDate = new Date();
			SchedulingPatternBuilder builder = SchedulingPatternBuilder.lastDateOfMonthDayAndHour(23, 59);
			Date lastExecutionDate = nextSchedule(builder.getSchedulingPattern(),executionStartDate);
			System.out.println("Last Execution Date :  " + lastExecutionDate);
			lastExecutionDate = nextSchedule(builder.getSchedulingPattern(),lastExecutionDate);
			System.out.println("Last Execution Date :  " + lastExecutionDate);
			
			SchedulingPattern pattern = new SchedulingPattern("23 1,17,20 L feb Mon,Sat");
			printNextSchedule(pattern, 10);
			
		}
		
		public static Date prepareDate(int hour, int minute,int date, int month,int year){
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.DATE, date);
			cal.set(Calendar.MONTH, month);
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.HOUR, hour);
			cal.set(Calendar.MINUTE, minute);
			return cal.getTime();
		}
		
		public static Date nextSchedule(SchedulingPattern pattern,Date startDate){
			Predictor predictor = new Predictor(pattern,startDate);
			return predictor.nextMatchingDate();
		}
		
		
		public static void main(String[] args) {
//			String pattern = " 59 23 L jan-dec * ";
			String pattern = " * * * * * ";
			SchedulingPattern schedulePattern =null;
			try {
				schedulePattern = new SchedulingPattern(pattern);
			} catch (InvalidPatternException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Calendar cal = Calendar.getInstance();
			cal.set(2011,	0, 2);
			Predictor predictor = new Predictor(schedulePattern, cal.getTime());
			for(int i=0;i<40;i++){
				System.out.println(predictor.nextMatchingDate());
			}
			System.out.println(new Date(predictor.nextMatchingTime()));
			
			
			SchedulingPatternBuilder builder = SchedulingPatternBuilder.monthlyOnDayAndHourAndMinute(20, 11, 20);
			printNextSchedule(builder);
			
			builder = SchedulingPatternBuilder.weeklyOnDayAndHourAndMinute(DateBuilder.THURSDAY, 10, 2);
			printNextSchedule(builder);
			
			
			builder = SchedulingPatternBuilder.atHourAndMinuteOnGivenDaysOfWeek(10, 2 ,0,1,2,3,4);
			printNextSchedule(builder,10);
			
			builder = SchedulingPatternBuilder.dailyAtHourAndMinute(3, 23);
			printNextSchedule(builder,10);
		}


		private static void printNextSchedule(SchedulingPatternBuilder builder) {
			printNextSchedule(builder, 1);
		}

		private static void printNextSchedule(SchedulingPatternBuilder builder,int iterations) {
			Predictor predictor = new Predictor(builder.getSchedulingPattern());
			System.out.println(builder.getSchedulingPattern().toString());
			for(int i=0;i<iterations;i++){
				System.out.println(predictor.nextMatchingDate());
			}
		}
		private static void printNextSchedule(SchedulingPattern builder,int iterations) {
			Predictor predictor = new Predictor(builder);
			System.out.println(builder.toString());
			for(int i=0;i<iterations;i++){
				System.out.println(predictor.nextMatchingDate());
			}
		}*/
}
