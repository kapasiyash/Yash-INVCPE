package com.elitecore.cpe.util.logger;


public class Logger {

    //private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Logger.class.toString());
    
    private static ILogger logger = new DefaultLogger();
    
    public static void logError(String strModule, String strMessage) {
          logger.error( "["+Thread.currentThread().getName() + "] [ "+ strModule +" ] : " + strMessage);
    }
    public static void logDebug(String strModule, String strMessage){
          logger.debug("["+Thread.currentThread().getName() + "] [ "+ strModule +" ] : " + strMessage);
    }
    public static void logInfo(String strModule, String strMessage){
          logger.info("["+Thread.currentThread().getName() + "] [ "+ strModule +" ] : " + strMessage);
    }
    public static void logWarn(String strModule, String strMessage){
     	  logger.warn("["+Thread.currentThread().getName() + "] [ "+ strModule +" ] : "+strMessage);
    }
    public static void logTrace(String strModule, String strMessage){
     	  logger.trace("["+Thread.currentThread().getName() + "] [ "+ strModule +" ] : "+strMessage);
    }

    public static void logTrace(String strModule, Throwable exception){
    	logger.trace("["+Thread.currentThread().getName() + "] [ "+ strModule +" ] : ", exception);
    }

    public static void setLogger(ILogger newlogger) {
    	logger = newlogger;
    }
    
    public static ILogger getLogger(){
    	return logger;
    }
    

}
