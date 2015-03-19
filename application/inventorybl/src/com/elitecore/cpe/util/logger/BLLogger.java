package com.elitecore.cpe.util.logger;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public final class BLLogger {

	private static final Logger logger = Logger.getLogger("cpe-BL");
	
	public static void error(String module, String strMessage) {
		logger.error(module +" " + strMessage);
    }

    public static void debug(String module, String strMessage) {
    	logger.debug(module +" " + strMessage);
    }

    public static void info(String module, String strMessage) {
    	logger.info(module +" " + strMessage);        
    }

    public static void warn(String module, String strMessage) {
    	logger.warn(module +" " + strMessage);
    }
    
    public static void fatal(String module, String strMessage) {
    	logger.fatal(module +" " + strMessage);
    }

    public static void trace(String module, String strMessage) {
    	logger.trace(module +" " + strMessage);
    }
    
    public static void stackTrace(String module, Throwable t) {
		StringWriter stringWriter = new StringWriter();
		t.printStackTrace(new PrintWriter(stringWriter));
		logger.error(stringWriter.toString());
    }
    
    
    public static final int parseLogLevel(String level) {
    	
    	int logLevelValue = LogLevel.ALL.VALUE;
    	
    	if (level != null && level.trim().length() != 0 ) {
    		
    		if (level.equalsIgnoreCase(LogLevel.OFF.NAME)) {
    			logLevelValue = LogLevel.OFF.VALUE;	
    			logger.setLevel(Level.OFF);
    		} else if (level.equalsIgnoreCase(LogLevel.FATAL.NAME)) {
    			logLevelValue = LogLevel.FATAL.VALUE;
    			logger.setLevel(Level.FATAL);
    		} else if (level.equalsIgnoreCase(LogLevel.ERROR.NAME)) {
    			logLevelValue = LogLevel.ERROR.VALUE;
    			logger.setLevel(Level.ERROR);
    		} else if (level.equalsIgnoreCase(LogLevel.WARN.NAME)) {
    			logLevelValue = LogLevel.WARN.VALUE;
    			logger.setLevel(Level.WARN);
    		} else if (level.equalsIgnoreCase(LogLevel.INFO.NAME)) {
    			logLevelValue = LogLevel.INFO.VALUE;
    			logger.setLevel(Level.INFO);
    		} else if (level.equalsIgnoreCase(LogLevel.DEBUG.NAME)) {
    			logLevelValue = LogLevel.DEBUG.VALUE;
    			logger.setLevel(Level.DEBUG);
    		} else if (level.equalsIgnoreCase(LogLevel.TRACE.NAME)) {
    			logLevelValue = LogLevel.TRACE.VALUE;
    			logger.setLevel(Level.TRACE);
    		} 
    	}
    	return logLevelValue;
    }
    
    public static enum LogLevel {
    	OFF(0, "OFF"),
    	FATAL(1, "FATAL"),
    	ERROR(2, "ERROR"),
    	WARN(3, "WARN"),
    	INFO(4, "INFO"),
    	DEBUG(5, "DEBUG"),
    	TRACE(6, "TRACE"),
    	ALL(7, "ALL");
    	
    	public final int VALUE;
    	public final String NAME;
    	LogLevel (int value, String name){
    		this.VALUE = value;
    		this.NAME = name;
    	}
    }
}
