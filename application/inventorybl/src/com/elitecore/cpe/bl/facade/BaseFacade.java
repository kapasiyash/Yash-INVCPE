 package com.elitecore.cpe.bl.facade;


import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.elitecore.cpe.bl.data.system.internal.SystemActionData;
import com.elitecore.cpe.core.InternalBLSession;
import com.elitecore.cpe.core.base.BaseBL;
import com.elitecore.cpe.util.logger.Logger;


/**
 * 
 * @author yash.kapasi
 */

public abstract class BaseFacade extends BaseBL {
	
	private static final String MODULE = "BASE_FACADE";
	
	
	@Resource 
	private SessionContext sessionContext;
	
	protected final SessionContext getSessionContext() {
		return sessionContext;
	}
	
	protected final String isNULLString(String value, String replaceValue) {
		if (value == null)
			return replaceValue;
		return value;
	}
	
	protected static final InitialContext getInitialContext() throws NamingException {
        return new InitialContext();
    }

	
	
	
	protected final InternalBLSession createAdminBLSession(String userId, String name,String username, String password, String userType, String ipAddress,Map<String, SystemActionData> permitedActions,Set<Long> userWarehouseMappings) {
		if(isTraceLevel())
			Logger.logTrace(MODULE, "inside createAdminBLSession()");		
		String sessionId = UUID.randomUUID().toString();
		InternalBLSession internalBLSession = new InternalBLSession(userId,name, username, password, sessionId, ipAddress, userType,permitedActions,userWarehouseMappings);
		return internalBLSession;
	}
	
	protected final Timestamp getCurrentTimestamp() {
		return new Timestamp(new Date().getTime());
	}
	
	public String getDateFormat(){
		return "dd-MMM-yyyy";
	}
	
	public String getDateTimeFormat(){
		return "dd-MMM-yyyy hh:mm:ss";
	}
	
	public boolean isBLValidationRequried(){
		return true;
	}
	
}
