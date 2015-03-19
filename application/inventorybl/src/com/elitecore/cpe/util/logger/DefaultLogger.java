
/**
 * @author baiju
 * DefaultLogger 
 */
package com.elitecore.cpe.util.logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DefaultLogger implements ILogger {
    
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");

    public void error(String strMessage) {
        System.out.println("[ " + dateToString(new Date())+" ]" + " [ ERROR ] " + strMessage);
    }
    public void debug(String strMessage) {
        System.out.println("[ " + dateToString(new Date())+" ]" + " [ DEBUG ] " + strMessage);
    }
    public void info(String strMessage) {
        System.out.println("[ " + dateToString(new Date())+" ]" + " [ INFO ] " + strMessage);
    }
    public void warn(String strMessage) {
        System.out.println("[ " + dateToString(new Date())+" ]" + " [ WARN ] " + strMessage);
    }
    public void fatal(String strMessage) {
        System.out.println("[ " + dateToString(new Date())+" ]" + " [ FATAL ] " + strMessage);
    }
    public void trace(String strMessage) {
        System.out.println("[ " + dateToString(new Date())+" ]" + " [ TRACE ] " + strMessage);
    }

    public void trace(Throwable exception) {
    	trace("", exception);
	}

    public void trace(String module, Throwable exception) {
		StringWriter stringWriter = new StringWriter();
		exception.printStackTrace(new PrintWriter(stringWriter));
		System.out.println("[ " + dateToString(new Date())+" ]" + " [ TRACE ] " + module + stringWriter.toString());
    }
    
    protected String dateToString(Date date){
    	return sdf.format(date);
    }
    
}
