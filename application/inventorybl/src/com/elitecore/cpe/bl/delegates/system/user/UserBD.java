package com.elitecore.cpe.bl.delegates.system.user;

import javax.naming.NamingException;

import com.elitecore.cpe.bl.delegates.BaseBusinessDelegate;
import com.elitecore.cpe.bl.exception.AccessDeniedException;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.TechnicalException;
import com.elitecore.cpe.bl.facade.system.user.IUserFacade;
import com.elitecore.cpe.bl.facade.system.user.UserFacadeLocal;
import com.elitecore.cpe.bl.facade.system.user.UserFacadeRemote;
import com.elitecore.cpe.core.IBDSessionContext;



public class UserBD  extends BaseBusinessDelegate{
	
	private static final String MODULE ="USER-BD";
	private static IUserFacade facade;
	
	public UserBD() {
		super(null);
	}

	public UserBD(IBDSessionContext context) {
		super(context);
	}
	
	private IUserFacade getFacade() throws NamingException {
 		if (facade == null) {
 			if (isLocalMode()) {
 				facade = (IUserFacade)lookupLocal(UserFacadeLocal.class);
 			}else {
 				facade = (IUserFacade)lookup(UserFacadeRemote.class);
 			}
 		}
 		return facade;
 	}
	
	
	public void doLogout() {
		try {
			getFacade().doLogout(getBLSession());
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Method can be use for user login according system policy  
	 * @param username,password.IpAddress
	 * @return IBLSessionContext 
	 * @throws  SearchBLException,AccessDeniedException,TechnicalException
	 * @author yash.kapasi
	 */
	public IBDSessionContext doLogin(String username, String passwd, String strIpAddress) throws AccessDeniedException, TechnicalException, SearchBLException {
		if(isTraceLevel())
			logTrace(MODULE, "Inside doLogin");
		try{
			return getFacade().doLogin(username, passwd, strIpAddress);
		}catch (NamingException e) {
			if(isErrorLevel())
					logError(MODULE, "Error In doLogin Operation Reason :"+e.getMessage());
			e.printStackTrace();
			throw new TechnicalException();
		}
	}
	
}
