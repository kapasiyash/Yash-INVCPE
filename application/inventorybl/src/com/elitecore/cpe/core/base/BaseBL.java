package com.elitecore.cpe.core.base;



import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.elitecore.cpe.bl.data.notification.NotificationData;
import com.elitecore.cpe.bl.data.system.audit.AuditEntryData;
import com.elitecore.cpe.bl.data.system.audit.AuditQueueData;
import com.elitecore.cpe.bl.data.system.audit.AuditSummary;
import com.elitecore.cpe.bl.data.system.audit.WebServiceAuditData;
import com.elitecore.cpe.bl.facade.notification.SystemNotificationQueueHandler;
import com.elitecore.cpe.bl.facade.system.audit.SystemAuditQueueHandler;
import com.elitecore.cpe.bl.facade.system.audit.WebServiceAuditQueueHandler;
import com.elitecore.cpe.core.IBLSession;
import com.elitecore.cpe.util.logger.BLLogger;
import com.elitecore.cpe.util.logger.Logger;

/**
 * @author yash.kapasi
 *
 */
public class BaseBL {

	private static int currentLogLevel;
	private static final String MODULE ="BASE";
	
	static {
		currentLogLevel = BLLogger.parseLogLevel(System.getProperty("cpe.loglevel"));		
	}	
	
	public static String md5(String input)  {                
		String md5 = null;         
        if(null == input) return null;         
        try {
                 				        
        //Create MessageDigest object for MD5
        MessageDigest digest = MessageDigest.getInstance("MD5");         

        //Converts message digest value in base 16 (hex) 
        md5 = getHexString(digest.digest(input.getBytes()));
 
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return md5;
    }
		
	protected static String getHexString(byte[] data) throws Exception {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			int halfbyte = (data[i] >>> 4) & 0x0F;
			int two_halfs = 0;
			do {
				if ((0 <= halfbyte) && (halfbyte <= 9))
					buf.append((char) ('0' + halfbyte));
				else
					buf.append((char) ('a' + (halfbyte - 10)));
				halfbyte = data[i] & 0x0F;
			} while(two_halfs++ < 1);
		}
		return buf.toString();
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

	public void addToAuditDynamicMessage(String strActionAlias, String strReason,String auditTypeId,Map<String,Object> mapData,IBLSession iSessionInfo) 
	{
		addToAuditDynamicMessage(strActionAlias,strReason,null,auditTypeId,mapData,null,iSessionInfo);
	}
	public void addToAuditDynamicMessage(String strActionAlias, String strReason,String auditTypeId,Map<String,Object> mapData,List<AuditEntryData> auditEntryDataList,IBLSession iSessionInfo) 
	{
		addToAuditDynamicMessage(strActionAlias,strReason,null,auditTypeId,mapData,auditEntryDataList,iSessionInfo);
	}
	public void addToAuditDynamicMessage(String strActionAlias, String strReason,String strRemark,String auditTypeId,Map<String,Object> mapData,List<AuditEntryData> auditEntryDataList,IBLSession iSessionInfo) 
	{
		
		String strSystemUserId="STF0001";
		String strIpAddress="127.0.0.1";
		
		AuditSummary data = new AuditSummary();
		data.setActionAlias(strActionAlias);
		data.setAuditTypeId(auditTypeId);
		data.setAuditDate(new Timestamp(new Date().getTime()));
		data.setReason(strReason);
		data.setRemarks(strRemark);
		data.setTagMap(mapData);
		
		if(iSessionInfo != null){
			
			strSystemUserId = iSessionInfo.getSessionUserId();
		//Added if block on 13 october	
		if(!iSessionInfo.getIpAddress().equals("::1")){
			strIpAddress = iSessionInfo.getIpAddress();
			}
		}
		
		data.setUserId(strSystemUserId);
		data.setIpAddress(strIpAddress);
		if(auditEntryDataList != null && !auditEntryDataList.isEmpty()){
			data.setAuditEntryDatas(auditEntryDataList);
		}
		
		auditLog(data);
	}

	
	public void addToAuditDynamicMessage(String strActionAlias, String strReason,String strRemark,String auditTypeId,Map<String,Object> mapData,List<AuditEntryData> auditEntryDataList,IBLSession iSessionInfo,String strIpAddress) 
	{
		
		String strSystemUserId="STF0001";
		
		AuditSummary data = new AuditSummary();
		data.setActionAlias(strActionAlias);
		data.setAuditTypeId(auditTypeId);
		data.setAuditDate(new Timestamp(new Date().getTime()));
		data.setReason(strReason);
		data.setRemarks(strRemark);
		data.setTagMap(mapData);
		
		if(iSessionInfo != null){
			
			strSystemUserId = iSessionInfo.getSessionUserId();
		//Added if block on 13 october	
		if(!iSessionInfo.getIpAddress().equals("::1")){
			strIpAddress = iSessionInfo.getIpAddress();
			}
		}
		
		data.setUserId(strSystemUserId);
		data.setIpAddress(strIpAddress);
		if(auditEntryDataList != null && !auditEntryDataList.isEmpty()){
			data.setAuditEntryDatas(auditEntryDataList);
		}
		
		auditLog(data);
	}
	
	public void sendNotification(NotificationData notificationData) {
		try {
            
            SystemNotificationQueueHandler systemNotificationQueueHandler = new SystemNotificationQueueHandler();
			systemNotificationQueueHandler.send(notificationData);
			systemNotificationQueueHandler.close();
			
		} catch(javax.jms.JMSException e){
			Logger.logTrace(MODULE,e.getMessage());
			e.printStackTrace();
		} catch(Exception e) {
			Logger.logTrace(MODULE,e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	private void auditLog(AuditSummary auditData) {
		try {
            
            SystemAuditQueueHandler systemAuditQueueHandler = new SystemAuditQueueHandler();
			systemAuditQueueHandler.send(auditData);
			systemAuditQueueHandler.close();
			
		} catch(javax.jms.JMSException e){
			Logger.logTrace(MODULE,e.getMessage());
			e.printStackTrace();
		} catch(Exception e) {
			Logger.logTrace(MODULE,e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void auditLogSpecific(AuditQueueData auditData) {
		try {
            
            SystemAuditQueueHandler systemAuditQueueHandler = new SystemAuditQueueHandler();
			systemAuditQueueHandler.send(auditData);
			systemAuditQueueHandler.close();
			
		} catch(javax.jms.JMSException e){
			Logger.logTrace(MODULE,e.getMessage());
			e.printStackTrace();
		} catch(Exception e) {
			Logger.logTrace(MODULE,e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	public void webServiceAuditLog(WebServiceAuditData auditData) {
		try {
            
            WebServiceAuditQueueHandler webServiceAuditQueueHandler = new WebServiceAuditQueueHandler();
            webServiceAuditQueueHandler.send(auditData);
            webServiceAuditQueueHandler.close();
			
		} catch(javax.jms.JMSException e){
			Logger.logTrace(MODULE,e.getMessage());
			e.printStackTrace();
		} catch(Exception e) {
			Logger.logTrace(MODULE,e.getMessage());
			e.printStackTrace();
		}
	}
	
	protected javax.sql.DataSource getDataSource(String datasourceName) {
		DataSource ds = null;
		if (ds == null) {
			try {
				InitialContext ctx = new javax.naming.InitialContext();
				ds = (javax.sql.DataSource) ctx.lookup(datasourceName);
				Logger.logDebug(MODULE,"Got DS");
			} catch (NamingException e) {
				e.printStackTrace();
				Logger.logDebug(MODULE,"Data Source not found.");

			}
		}
		return ds;

	}
}
