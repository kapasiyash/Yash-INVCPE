package com.elitecore.cpe.bl.facade.system.user;

import java.util.Map;

import com.elitecore.cpe.bl.data.system.internal.SystemActionData;
import com.elitecore.cpe.bl.exception.AccessDeniedException;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.core.IBDSessionContext;
import com.elitecore.cpe.core.IBLSession;





/**
 * @author yash.kapasi
 *
 */
public interface IUserFacade {

	
	public void doLogout(IBLSession iblSession);
	
	public IBDSessionContext doLogin(String username, String password, String strIpAddress) throws AccessDeniedException, SearchBLException;
	
	public Map<String,SystemActionData> getActions() throws Exception;
	
}
