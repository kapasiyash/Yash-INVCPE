package com.elitecore.cpe.web.base.ui.module;

import static com.elitecore.cpe.web.base.ui.module.BaseConstants.ERROR_MESSAGE_PARAM_ID;
import static com.elitecore.cpe.web.base.ui.module.BaseConstants.ERROR_OBJECT_PARAM_ID;
import static com.elitecore.cpe.web.base.ui.module.BaseConstants.SYSTEMPARAMETER;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Window;

import com.elitecore.cpe.bl.constants.system.parameter.SystemParameterConstants;
import com.elitecore.cpe.bl.data.system.internal.SystemActionData;
import com.elitecore.cpe.core.IBDSessionContext;
import com.elitecore.cpe.util.logger.BLLogger;
public class BaseComposerOperationImpl {

	private static String dateFormat;
	private static String dateTimeFormat;
	
	public static void setBDSessionContext(IBDSessionContext ibdSessionContext) {
		HttpSession session = (HttpSession)Sessions.getCurrent(true).getNativeSession();
		session.setAttribute("_bd-sessi0n-ctx###", ibdSessionContext);
	}
	
	public static IBDSessionContext getBDSessionContext() {
		HttpSession session = (HttpSession)Sessions.getCurrent(true).getNativeSession();
		return (IBDSessionContext)session.getAttribute("_bd-sessi0n-ctx###");
	}
	
	public static ServletContext getBDServletContext(){
		HttpSession session = (HttpSession)Sessions.getCurrent(true).getNativeSession();
		return session.getServletContext();
	}

	
	
	private static int currentLogLevel;

	static {
		currentLogLevel = BLLogger.parseLogLevel(System.getProperty("cpe.loglevel"));
	/*	dateFormat= getSystemParamterValue(SystemParameterConstants.DEFAULT_DATE);
		dateTimeFormat = getSystemParamterValue(SystemParameterConstants.DEFAULT_DATE_TIME);*/
	}
	
	public static final void logError(String module, String strMessage) {
		BLLogger.error(module,  strMessage);
    }

    public static final void logDebug(String module, String strMessage) {
    	BLLogger.debug(module,  strMessage);
        
    }

    public static final void logInfo(String module, String strMessage) {
    	BLLogger.info(module,  strMessage);        
    }

    public static final void logWarn(String module, String strMessage) {
    	BLLogger.warn(module,  strMessage);
    }
    
    public static final void logFatal(String module, String strMessage) {
    	BLLogger.fatal(module,  strMessage);
    }

    public static final void logTrace(String module, String strMessage) {
    	BLLogger.trace(module,  strMessage);
    }

    public static final void logStackTrace(String module, Throwable t) {
    	BLLogger.stackTrace(module,  t);
    }
    
    public static final boolean isFatalLevel() {
    	return currentLogLevel >= BLLogger.LogLevel.FATAL.VALUE; 
    }

    public static final boolean isErrorLevel() {
    	return currentLogLevel >= BLLogger.LogLevel.ERROR.VALUE; 
    }
    
    public static final boolean isWarnLevel() {
    	return currentLogLevel >= BLLogger.LogLevel.WARN.VALUE; 
    }
    
    public static final boolean isInfoLevel() {
    	return currentLogLevel >= BLLogger.LogLevel.INFO.VALUE; 
    }
    
    public static final boolean isDebugLevel() {
    	return currentLogLevel >= BLLogger.LogLevel.DEBUG.VALUE; 
    }
    
    public static final boolean isTraceLevel() {
    	return currentLogLevel >= BLLogger.LogLevel.TRACE.VALUE; 
    }
    
    public static final boolean isAllLevel() {
    	return currentLogLevel >= BLLogger.LogLevel.ALL.VALUE; 
    }
	
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static String getSystemParamterValue(String aliasName){
    	HttpSession session = (HttpSession)Sessions.getCurrent(true).getNativeSession();
    	Map<String,String> sysMap = (Map)session.getServletContext().getAttribute(SYSTEMPARAMETER);
    	return sysMap.get(aliasName);
    }
    

    public static String getDateFormat(){
    	if(dateFormat==null){
    		dateFormat = BaseComposerOperationImpl.getSystemParamterValue(SystemParameterConstants.DEFAULT_DATE);
    	}
    	return dateFormat;
	}
    
    public static String getDateTimeFormat(){
    	if(dateTimeFormat==null){
    		dateTimeFormat = BaseComposerOperationImpl.getSystemParamterValue(SystemParameterConstants.DEFAULT_DATE_TIME);
    	}
		return dateTimeFormat;
	}
    
    
    public static void showErrorDialog(String errorMessage, Throwable error) {
    	showErrorDialog(errorMessage, error, null);
    }
    
    public static void showErrorDialog(String errorMessage, Throwable error, Window parent) {
    	Map<String, Object> errorComposerParams = new HashMap<String, Object>();
    	errorComposerParams.put(ERROR_MESSAGE_PARAM_ID, errorMessage);
    	errorComposerParams.put(ERROR_OBJECT_PARAM_ID, error);
    	
		Component errorDialog = Executions.createComponents("/WEB-INF/pages/core/error-dialog.zul", parent, errorComposerParams);
        ((Window)errorDialog).doModal();
    }

	public static boolean isPermitedAction(String strActionAlias) {
		// TODO Auto-generated method stub
		//mapAliasAction=getBDSessionContext().getSessionAccessControl().getAliasActionMap();
		//return getBDSessionContext().getSessionAccessControl().getAliasActionMap().containsKey(strActionAlias);
		return getBDSessionContext().getBLSession().isPermittedAction(strActionAlias);
	}
	
	public static SystemActionData getSystemActionData(String strActionAlias){
		return getBDSessionContext().getBLSession().getSystemAction(strActionAlias);
	}
    
}
