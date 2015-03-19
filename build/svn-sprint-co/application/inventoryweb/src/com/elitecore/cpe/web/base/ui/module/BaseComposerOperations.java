package com.elitecore.cpe.web.base.ui.module;

import javax.servlet.ServletContext;

import org.zkoss.zul.Window;

import com.elitecore.cpe.core.IBDSessionContext;

public interface BaseComposerOperations {
	
	public void setBDSessionContext(IBDSessionContext ibdSessionContext) ;
	
	public IBDSessionContext getBDSessionContext() ;
	
	public ServletContext getBDServletContext();
	
	
	
	public void logError(String module, String strMessage);

    public void logDebug(String module, String strMessage);

    public void logInfo(String module, String strMessage);

    public void logWarn(String module, String strMessage);
    
    public void logFatal(String module, String strMessage);

    public void logTrace(String module, String strMessage);

    public void logStackTrace(String module, Throwable t);
    
    public boolean isFatalLevel();

    public boolean isErrorLevel();
    
    public boolean isWarnLevel();
    
    public boolean isInfoLevel();
    
    public boolean isDebugLevel() ;
    
    public boolean isTraceLevel() ;
    
    public boolean isAllLevel() ;
    
    public String getSystemParamterValue(String aliasName);
    
    public String getDateFormat();
    public String getDateTimeFormat();
        
    public void showErrorDialog(String errorMessage, Throwable error) ;
    
    public void showErrorDialog(String errorMessage, Throwable error, Window parent) ;
}
